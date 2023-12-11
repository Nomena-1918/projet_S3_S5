package org.example.demo.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        //create database connection from init parameters and set it to context
        Connection connection;
        try {
            connection = Connexion.getConnexionPostgreSql();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.setAttribute("DBConnection", connection);
        System.out.println("\nDatabase connection initialized for Application. 🚀\n");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        Connection connection = (Connection) ctx.getAttribute("DBConnection");
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Database connection closed for Application. 🤝");
    }

}