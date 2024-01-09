package org.example.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
     static String getUrl() {
        String user = getUsername();
        String pwd = getPassword();

        String database = "voyage_db";
        String host = "localhost";
        String port = "5432";

        return String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, database, user, pwd);
    }

    static String getUsername() {
        return "nomena";
    }

    static String getPassword() {
        return "root";
    }

    public static Connection getConnexionPostgreSql() throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = getUrl();
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }

    public static Connection getConnexionPostgreSql(String database, String user, String pwd, String host, String port) throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, database, user, pwd);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
}
