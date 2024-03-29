package org.voyage.demo.servlets.gestion_personnel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.voyage.demo.connexion.ConnexionPool;
import org.voyage.demo.models.gestion_personnel.Candidat;
import org.voyage.demo.models.gestion_personnel.Embauche;
import org.voyage.demo.models.gestion_personnel.Fonction;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@WebServlet(name = "embaucheServlet", value = "/embauche-servlet")
public class EmbaucheServlet extends HttpServlet {
    private long AGE_MAJEUR;

    public void init() {
        AGE_MAJEUR = Long.parseLong(getInitParameter("majeur"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(Connection connection = ConnexionPool.getConnection()) {
            getInfo(request, response, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idFonction=Integer.parseInt(request.getParameter("idFonction"));
        Integer idEmploye=Integer.parseInt(request.getParameter("idEmploye"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getParameter("date"), formatter);

        try(Connection connection = ConnexionPool.getConnection()) {
            Candidat emp= Candidat.findById(connection, idEmploye);
            long ageEmp = ChronoUnit.YEARS.between(emp.getDtn(), LocalDate.now());

            if (ageEmp < AGE_MAJEUR)
                throw new Exception("Cette personne est encore mineure");

            Embauche.insertEmbauche(connection, new Embauche(emp, new Fonction(idFonction), date));
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
        List<Fonction> fonctions = Fonction.readAll(connection);
        request.setAttribute("list_fonction",fonctions);

        List<Candidat> candidats = Candidat.readAll(connection);
        request.setAttribute("list-candidat", candidats);

        RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/Embauche.jsp");
        dispatcher.forward(request, response);
    }
}
