package com.jing.framework.web.handler;

import com.jing.framework.web.mvc.Controller;
import com.jing.framework.web.mvc.RequestMapping;
import com.jing.framework.web.mvc.RequestMethod;
import com.jing.framework.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author GUO
 * @date 2019/9/25
 */
public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();
    private static Map<String,String> uriMap = new HashMap<>();
    public static void resolveMappingHandler(List<Class<?>> classList) {
        classList.forEach(cls -> {
            if (cls.isAnnotationPresent(Controller.class)) {
                String uriPrefix = "";
                if (cls.isAnnotationPresent(RequestMapping.class)) {
                    uriPrefix = cls.getDeclaredAnnotation(RequestMapping.class).value();
                }
                paraseHandlerFromController(cls, uriPrefix);
            }
        });
    }

    private static void paraseHandlerFromController(Class<?> cls, String uriPrefix) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                String uri = String.format("%s%s", uriPrefix, requestMapping.value());
                RequestMethod requestMethod = requestMapping.method().length>0?requestMapping.method()[0]:null;
                if(uriMap.containsKey(uri)){
                    if(uriMap.get(uri).equals(requestMethod.name())){
                        // 随便跑了个异常
                        throw new IllegalArgumentException(String.format("重复的uri=%s，class=%s,method=%s", uri, cls.getName(), method.getName()));
                    }
                }else{
                    uriMap.put(uri,requestMethod==null?"":requestMethod.name());
                }

                List<String> paramList = new ArrayList<>();
                for (Parameter parameter : method.getParameters()) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        paramList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                    }
                }
                String[] params = paramList.toArray(new String[paramList.size()]);
                MappingHandler mappingHandler = null;
                try {

                    mappingHandler = new MappingHandler(uri, method,requestMethod, cls, params);
                    mappingHandlerList.add(mappingHandler);
                    System.out.printf("成功解析URI=%s,controller=%s%n", uri, cls);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
