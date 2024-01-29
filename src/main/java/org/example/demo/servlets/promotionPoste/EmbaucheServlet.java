package org.example.demo.servlets.promotionPoste;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.database.ConnexionPool;
import org.example.demo.models.promotionPoste.Embauche;
import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;

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
            Employe emp=Employe.findById(connection, idEmploye);
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

        List<Employe> employes = Employe.readAll(connection);
        request.setAttribute("list-employe",employes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("promotionPoste/Embauche.jsp");
        dispatcher.forward(request, response);
    }
}
