package org.voyage.demo.servlets.gestion_personnel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_personnel.Candidat;
import org.voyage.demo.models.composition_voyage.Voyage;
import org.voyage.demo.models.gestion_personnel.SituationProPersonne;
import org.voyage.demo.models.gestion_personnel.VoyageEmploye;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "insertionVoyageEmployeServlet", value = "/insertionVoyageEmploye-servlet")
public class InsertionVoyageEmployeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idVoyage=Integer.parseInt(request.getParameter("idVoyage"));
        Integer idEmploye=Integer.parseInt(request.getParameter("idEmploye"));
        int heureTravail=Integer.parseInt(request.getParameter("heure"));
        try(Connection connection = ConnexionPool.getConnection()){
            Voyage voyage=new Voyage(idVoyage);
            Candidat candidat =new Candidat(idEmploye);

            VoyageEmploye voyageEmploye=new VoyageEmploye(voyage, candidat,heureTravail);
            VoyageEmploye.insertVoyageEmploye(connection,voyageEmploye);
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
        List<SituationProPersonne> situationProPersonnes = SituationProPersonne.readAll(connection);

        // Prendre les candidats de situation pro personne list
        List<Candidat> listCandidat = situationProPersonnes.stream()
                .map(SituationProPersonne::getCandidat)
                .collect(Collectors.toList());

        request.setAttribute("list-candidat", listCandidat);

        List<Voyage> listVoyage = Voyage.readAll(connection);
        request.setAttribute("list-voyage", listVoyage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("travail/InsertionVoyageEmploye.jsp");
        dispatcher.forward(request, response);
    }
}
