package org.example.demo.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ConnexionPool {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(Connexion.getUrl());
        ds.setUsername(Connexion.getUsername());
        ds.setPassword(Connexion.getPassword());
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        ds.setMinEvictableIdleTimeMillis(Duration.of(60000));
        ds.setTimeBetweenEvictionRunsMillis(30000);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
