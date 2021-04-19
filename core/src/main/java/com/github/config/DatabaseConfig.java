package com.github.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final HikariConfig config = new HikariConfig();//System.getProperty("user.dir") + "/core/src/main/resources/web/tologin.html");

    static{
        config.setJdbcUrl("jdbc:postgresql://localhost:54320/mydb");
        config.setUsername("john");
        config.setPassword("pwd0123456789");
    }

    private static DataSource dataSource = new HikariDataSource(config);

    public static HikariConfig getConfig() {
        return config;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
