package org.example.demo.servlets.client;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.*;
import org.example.demo.models.client.StatistiqueSexe;
import org.example.demo.models.promotionPoste.Embauche;
import org.example.demo.models.promotionPoste.Sexe;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;
import org.example.demo.models.travail.Voyage;
import org.example.demo.models.travail.VoyageEmploye;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        try(Connection connection = ConnexionPool.getConnection()){
            Sexe sexe=new Sexe(idSexe);
            Client client=new Client(sexe);
            Client.insertClient(connection,client);

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
        List<Sexe> sexes = Sexe.readAll(connection);
        request.setAttribute("list-sexe",sexes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("client/client.jsp");
        dispatcher.forward(request, response);
    }
}
