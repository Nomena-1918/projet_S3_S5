package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.Connexion;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Emp;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet(name = "empServlet", value = "/emp-servlet")
public class EmpServlet extends HttpServlet {

    // Affichage servlet dans la base de données
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Tous les emp avec pagination
        int nbr_ligne = Integer.parseInt(request.getParameter("nbr_ligne"));
        int pagination_debut = Integer.parseInt(request.getParameter("debut"));

        try {
            Connection connection = ConnexionPool.getConnection();

            List<Emp> listEmp = Emp.readAllEmp(connection, nbr_ligne, pagination_debut);

            request.setAttribute("list-emp", listEmp);
            RequestDispatcher dispatcher = request.getRequestDispatcher("aff-emp.jsp");

            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
