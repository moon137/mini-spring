package com.jing.framework.starter;

import com.jing.framework.beans.BeanFactory;
import com.jing.framework.core.ClassScanner;
import com.jing.framework.web.handler.HandlerManager;
import com.jing.framework.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @author GUO
 * @date 2019/9/24
 */
public class MiniApplication {
    public static void run(Class<?> cls,String [] args){
        System.out.println("Hello FrameworkApplication");
        //实现内置 tomcat
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            // 扫描与cls 统计目录一下的所有注解类，这也是springboot要求见application放到最外层的原因
            // 因为扫描是从 application 开始的
            List<Class<?>> classList = ClassScanner.scannerClass(cls.getPackage().getName());
            // 初始化bean，实现依赖注入
            System.out.println("===============开始依赖注入===============");
            BeanFactory.intBean(classList);
            System.out.println("===============结束依赖注入===============\n\n");
            // 解决mapping
            System.out.println("===============注册requestMapping===============");
            HandlerManager.resolveMappingHandler(classList);
            System.out.println("===============注册requestMapping完成===============\n\n");
            System.out.println(" .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------. \n" +
                    "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                    "| | ____    ____ | || |     _____    | || | ____  _____  | || |     _____    | || |    _______   | || |   ______     | || |  _______     | || |     _____    | || | ____  _____  | || |    ______    | |\n" +
                    "| ||_   \\  /   _|| || |    |_   _|   | || ||_   \\|_   _| | || |    |_   _|   | || |   /  ___  |  | || |  |_   __ \\   | || | |_   __ \\    | || |    |_   _|   | || ||_   \\|_   _| | || |  .' ___  |   | |\n" +
                    "| |  |   \\/   |  | || |      | |     | || |  |   \\ | |   | || |      | |     | || |  |  (__ \\_|  | || |    | |__) |  | || |   | |__) |   | || |      | |     | || |  |   \\ | |   | || | / .'   \\_|   | |\n" +
                    "| |  | |\\  /| |  | || |      | |     | || |  | |\\ \\| |   | || |      | |     | || |   '.___`-.   | || |    |  ___/   | || |   |  __ /    | || |      | |     | || |  | |\\ \\| |   | || | | |    ____  | |\n" +
                    "| | _| |_\\/_| |_ | || |     _| |_    | || | _| |_\\   |_  | || |     _| |_    | || |  |`\\____) |  | || |   _| |_      | || |  _| |  \\ \\_  | || |     _| |_    | || | _| |_\\   |_  | || | \\ `.___]  _| | |\n" +
                    "| ||_____||_____|| || |    |_____|   | || ||_____|\\____| | || |    |_____|   | || |  |_______.'  | || |  |_____|     | || | |____| |___| | || |    |_____|   | || ||_____|\\____| | || |  `._____.'   | |\n" +
                    "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                    "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                    " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'");
            tomcatServer.startServer(9090);
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
