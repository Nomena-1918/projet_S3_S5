package org.example.demo.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Activite;
import org.example.demo.models.EntreeActivite;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "entreeactiviteServlet", value = "/entreeactivite-servlet")
public class EntreeActiviteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        Integer quantite=Integer.parseInt(request.getParameter("quantite"));
        try(Connection connection = ConnexionPool.getConnection()){
            EntreeActivite entreeActivite = new EntreeActivite();
            entreeActivite.setId(idActivite);
            entreeActivite.setQuantite(quantite);
            EntreeActivite.insertEntreeActivite(connection,entreeActivite);
            getInfo(request, response, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            try(Connection connection = ConnexionPool.getConnection()){
                getInfo(request, response, connection);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);
    }
}
