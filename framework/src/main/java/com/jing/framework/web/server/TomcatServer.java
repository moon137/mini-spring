package com.jing.framework.web.server;

import com.jing.framework.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author GUO
 * @date 2019/9/24
 */
public class TomcatServer {
    private Tomcat tomcat;
    private String [] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }
    public void startServer(int port) throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        Tomcat.addServlet(context,"dispatcherServlet", dispatcherServlet).setAsyncSupported(true);

        context.addServletMappingDecoded("/","dispatcherServlet");

        tomcat.getHost().addChild(context);

        Thread awaitThread = new Thread(()-> TomcatServer.this.tomcat.getServer().await(),"tomcat-wait-thread");
        // 非守护进程
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
