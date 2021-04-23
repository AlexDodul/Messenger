package com.github.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import javax.servlet.ServletException;
import java.io.File;

public class ServerConfig {

    private static void configureTomcat(Tomcat tomcat) throws ServletException {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.parseInt(webPort));
        Context ctx = tomcat.addWebapp("", new File(".").getAbsolutePath());

        tomcat.addServlet("","UsersHandler", AppConfig.getUsersHandler());
        ctx.addServletMappingDecoded("/user/*", "UsersHandler");
    }

    public static Tomcat getTomcat() throws ServletException {
        Tomcat tomcat = new Tomcat();
        configureTomcat(tomcat);
        return tomcat;
    }
}
