package com.jing.framework.web.handler;

import com.jing.framework.beans.BeanFactory;
import com.jing.framework.web.mvc.RequestMethod;
import com.jing.framework.web.mvc.ResponseBody;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.ietf.jgss.GSSContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author GUO
 * @date 2019/9/25
 */
public class MappingHandler {
    private   String uri ;
    private  Method method;
    private Class<?> controller;
    private String [] args;
    private RequestMethod requestMethod;

    public MappingHandler(String uri, Method method,RequestMethod requestMethod, Class<?> controller, String[] args) throws IllegalAccessException, InstantiationException {
        this.uri = uri;
        this.method = method;
        this.requestMethod = requestMethod;
        this.controller = controller;
        this.args = args;
    }

    public boolean handler(ServletRequest servletRequest, ServletResponse servletResponse) throws InvocationTargetException, IllegalAccessException, IOException {
        String requestUri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (!uri.equals(requestUri)) {
            return false;
        }
        String requestMethod = ((HttpServletRequest) servletRequest).getMethod();
        if(this.requestMethod != null){
            if(!this.requestMethod.name().equals(requestMethod)){
                return false;
            }
        }

        Object [] parameters = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            parameters[i] = servletRequest.getParameter(args[i]);
        }

        Object response = method.invoke(BeanFactory.getBean(controller),parameters);
        // todo 这里可能还需要处理跳转页面
        if(method.isAnnotationPresent(ResponseBody.class)){
            servletResponse.setContentType("text/json;charset=UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.println(GsonUtil.gson.toJson(response));
            out.flush();
            out.close();
        }else{
            servletResponse.setContentType("text/html;charset=UTF-8");
            //告诉servlet用UTF-8转码，而不是用默认的ISO8859
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.write(response.toString());
            out.flush();
            out.close();
        }
        return  true;
    }
}
