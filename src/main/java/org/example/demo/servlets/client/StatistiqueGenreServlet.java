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

@WebServlet(name = "StatGenre", value = "/stat-genre-servlet")
public class StatistiqueGenreServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long idActivite=Long.parseLong(request.getParameter("idActivite"));
        try(Connection connection = ConnexionPool.getConnection()){
            Activite activite=new Activite(idActivite,"NomTestActivite");
            List<Sexe> sexes = Sexe.readAll(connection);

            List<StatistiqueSexe> statistiqueSexeHomme=StatistiqueSexe.readAll(connection,sexes.get(0),activite);
            List<StatistiqueSexe> statistiqueSexeFemme=StatistiqueSexe.readAll(connection,sexes.get(1),activite);

            // Pourcentage Homme - Femme
            int nbrTotalHomme = 0;
            for (StatistiqueSexe s : statistiqueSexeHomme) {
                nbrTotalHomme+=s.getNombre();
            }

            int nbrTotalFemme = 0;
            for (StatistiqueSexe s : statistiqueSexeFemme) {
                nbrTotalFemme+=s.getNombre();
            }

            int nbrTotal = nbrTotalHomme + nbrTotalFemme;

            double pourcentHomme = (double) (nbrTotalHomme * 100) / nbrTotal;
            double pourcentFemme = (double) (nbrTotalHomme * 100) / nbrTotal;

            request.setAttribute("pourcentHomme",pourcentHomme);
            request.setAttribute("pourcentFemme",pourcentFemme);

            request.setAttribute("list_statHomme",statistiqueSexeHomme);
            request.setAttribute("list_statFemme",statistiqueSexeFemme);

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
        List<Activite> activites = Activite.readAll(connection);
        request.setAttribute("list_activite",activites);

        RequestDispatcher dispatcher = request.getRequestDispatcher("client/statsActGenre.jsp");
        dispatcher.forward(request, response);
    }
}
