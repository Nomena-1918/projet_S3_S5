package org.example.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    public static Connection getConnexionPostgreSql() throws Exception {
        /////// CONFIG ///////////////
        String database = "demoemp";
        String user = "nomena";
        String pwd = "root";
        String host = "localhost";
        String port = "5432";
        //////////////////////////////

        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, database, user, pwd);
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
