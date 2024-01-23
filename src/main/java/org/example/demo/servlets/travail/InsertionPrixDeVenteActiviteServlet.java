package org.example.demo.servlets.travail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.Activite;
import org.example.demo.models.travail.ActivitePrixVente;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "insertionPrixDeVenteActiviteServlet", value = "/insertionPrixDeVenteActivite-servlet")
public class InsertionPrixDeVenteActiviteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionPrixDeVenteActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        Double prixVente=Double.parseDouble(request.getParameter("prixVente"));

        try(Connection connection = ConnexionPool.getConnection()){
            ActivitePrixVente activitePrixVente =new ActivitePrixVente(new Activite(idActivite),prixVente);
            ActivitePrixVente.insertActivitePrixVente(connection,activitePrixVente);
            getInfo(request, connection);
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionPrixDeVenteActivite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("messageError",e.getMessage());
            try(Connection connection = ConnexionPool.getConnection()){
                getInfo(request,connection);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionPrixDeVenteActivite.jsp");
            dispatcher.forward(request, response);
        }
    }
    private void getInfo(HttpServletRequest request, Connection connection) throws Exception {
        List<Activite> listActivite = Activite.readAll(connection);
        request.setAttribute("list-activite", listActivite);
    }
}
