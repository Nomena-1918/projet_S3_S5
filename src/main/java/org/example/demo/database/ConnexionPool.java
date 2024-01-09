package org.example.demo.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnexionPool {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(Connexion.getUrl());
        ds.setUsername(Connexion.getUsername());
        ds.setPassword(Connexion.getPassword());
        // Min connexions fermées
        ds.setMinIdle(5);

        // Max connexions fermées
        ds.setMaxIdle(15);
        ds.setMaxOpenPreparedStatements(50);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection=ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }
}
