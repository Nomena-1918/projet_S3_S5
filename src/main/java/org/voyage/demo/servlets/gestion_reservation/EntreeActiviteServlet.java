package org.voyage.demo.servlets.gestion_reservation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.composition_voyage.Activite;
import org.voyage.demo.models.gestion_reservation.EntreeActivite;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "entreeactiviteServlet", value = "/entreeactivite-servlet")
public class EntreeActiviteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        Integer quantite=Integer.parseInt(request.getParameter("quantite"));
        Double prixUnitaire=Double.parseDouble(request.getParameter("prixUnitaire"));
        try(Connection connection = ConnexionPool.getConnection()){
            EntreeActivite entreeActivite = new EntreeActivite(idActivite,prixUnitaire,quantite);
            EntreeActivite.insertEntreeActivite(connection,entreeActivite);
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("EntreeActivite.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getInfo(HttpServletRequest request, Connection connection) throws Exception {
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);
    }
}
