package com.github;

import com.github.config.ServerConfig;
import com.github.utils.ServerRunner;

import javax.servlet.ServletException;

public class ServerApp {

    public static void main(String[] args) throws ServletException {
        ServerRunner tomcat = ServerConfig.tomcat();
        tomcat.run();
    }
}
