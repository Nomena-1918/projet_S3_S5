package org.example.demo.servlets.client;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.connexion.ConnexionPool;
import org.example.demo.models.gestion_personnel.Genre;
import org.example.demo.models.gestion_reservation.Client;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "clientservlet", value = "/client-servlet")
public class ClientServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idSexe=Integer.parseInt(request.getParameter("idsexe"));
        String nom = request.getParameter("nom");
        try(Connection connection = ConnexionPool.getConnection()){
            Genre genre =new Genre(idSexe);
            Client client=new Client(nom, genre);
            Client.insertClient(connection,client);
            connection.commit();
            getInfo(request, response, connection);
        } catch (Exception e) {
            try(Connection connection = ConnexionPool.getConnection()){
                request.setAttribute("messageError",e.getMessage());
                getInfo(request, response, connection);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Genre> sexes = Genre.readAll(connection);
        request.setAttribute("list-sexe",sexes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("client/client.jsp");
        dispatcher.forward(request, response);
    }
}
