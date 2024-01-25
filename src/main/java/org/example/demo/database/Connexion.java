package org.example.demo.database;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Constant;
import veda.godao.utils.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    public final static String database = "voyage_db";
    public final static String host = "localhost";
    public final static String port = "5432";
    public final static String username = "postgres";
    public final static String password = "postgres";
    public final static boolean use_ssl=false;
    public final static int SGBD = Constantes.PSQL_ID;
     static String getUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, database, username, password);
    }

    static String getUsername() {
        return username;
    }

    static String getPassword() {
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
