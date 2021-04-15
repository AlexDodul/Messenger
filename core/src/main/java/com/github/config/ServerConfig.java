package com.github.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import javax.servlet.ServletException;
import java.io.File;

public class ServerConfig {

    public static Tomcat tomcat() throws ServletException {
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.valueOf(webPort));
        Context ctx = tomcat.addWebapp("/", new File(".").getAbsolutePath());

        tomcat.addServlet("","UsersHandler", HandlerConfig.usersHandler());
        ctx.addServletMappingDecoded("/*", "UsersHandler");
        return tomcat;
    }
//    public static UserService userService(){
//        UserService origin = UserService.getUserService();
//        InvocationHandler handler = new UsersHandler(origin);
//        return (UserService) Proxy.newProxyInstance(origin.getClass().getClassLoader(),
//                new Class[] {UserService.class}, handler);
//    }
}
