package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.models.Emp;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;

@WebServlet(name = "empServlet", value = "/emp-servlet")
public class EmpServlet extends HttpServlet {

    //@Resource(name = "jdbc/MyPool")
    private DataSource dataSource;

    // Affichage servlet dans la base de données
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Tous les emp avec pagination
        int pagination_debut = Integer.parseInt(request.getParameter("debut"));
        int pagination_fin = Integer.parseInt(request.getParameter("fin"));

        try(Connection connection = dataSource.getConnection()) {
            List<Emp> listEmp = Emp.readAllEmp(connection, pagination_debut, pagination_fin);
            request.setAttribute("list-emp", listEmp);
            RequestDispatcher dispatcher = request.getRequestDispatcher("aff-emp.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
