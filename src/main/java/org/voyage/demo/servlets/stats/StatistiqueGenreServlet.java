package org.voyage.demo.servlets.stats;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_personnel.Genre;
import org.voyage.demo.models.stats.StatistiqueGenre;
import org.voyage.demo.models.composition_voyage.Activite;

import java.io.IOException;
import java.sql.Connection;
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
        Integer idActivite=Integer.parseInt(request.getParameter("idActivite"));
        try(Connection connection = ConnexionPool.getConnection()){
            Activite activite=new Activite(idActivite,"NomTestActivite");
            List<Genre> sexes = Genre.readAll(connection);

            List<StatistiqueGenre> statistiqueSexeHomme= StatistiqueGenre.readAll(connection,sexes.get(0),activite);
            List<StatistiqueGenre> statistiqueGenreFemme = StatistiqueGenre.readAll(connection,sexes.get(1),activite);

            // Pourcentage Homme - Femme
            double nbrTotalHomme = 0;
            for (StatistiqueGenre s : statistiqueSexeHomme) {
                nbrTotalHomme+=s.getNombre();
            }

            double nbrTotalFemme = 0;
            for (StatistiqueGenre s : statistiqueGenreFemme) {
                nbrTotalFemme+=s.getNombre();
            }

            double nbrTotal = nbrTotalHomme + nbrTotalFemme;

            Double pourcentHomme = (nbrTotalHomme * 100.0) / nbrTotal;
            Double pourcentFemme = (nbrTotalFemme * 100.0) / nbrTotal;

            request.setAttribute("pourcentHomme",pourcentHomme);
            request.setAttribute("pourcentFemme",pourcentFemme);

            request.setAttribute("list_statHomme",statistiqueSexeHomme);
            request.setAttribute("list_statFemme", statistiqueGenreFemme);

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
