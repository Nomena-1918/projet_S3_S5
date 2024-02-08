package org.voyage.demo.connexion;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnexionPool {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(Connexion.getUrl());
        // Min connexions fermées
        ds.setMinIdle(2);
        // Max connexions fermées
        ds.setMaxIdle(5);
        ds.setMaxOpenPreparedStatements(3);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection=ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }
}
