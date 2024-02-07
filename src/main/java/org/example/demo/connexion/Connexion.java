package org.example.demo.connexion;

import veda.godao.utils.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    public final static String database;
    public final static String host;
    public final static String port;
    public final static String username;
    public final static String password;
    public final static boolean use_ssl;
    public final static int SGBD;

    static {
        database = "voyage_db";
        host = "localhost";
        port = "5432";
        username = "postgres";
        password = "postgres";
        use_ssl=false;
        SGBD = Constantes.PSQL_ID;
    }

    public static String getUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, database, username, password);
    }
    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
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
