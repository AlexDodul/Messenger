package com.github.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final HikariConfig config = new HikariConfig("src/main/resources/hikari.properties");

    private static DataSource dataSource = new HikariDataSource(config);

    public static HikariConfig getConfig() {
        return config;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }


}
