package com.github;

import com.github.config.ServerConfig;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;

public class ServerApp {

    public static void main(String[] args) {
        try {
            Tomcat tomcat = ServerConfig.getTomcat();
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException e) {
            System.out.printf("Enter: %s", e.getMessage());
        }
    }

}
