package org.example.demo.models.travail;

import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoyageEmploye {
    private Long id;
    private Voyage voyage;
    private Employe employe;
    private int heureTravail;

    public VoyageEmploye(Voyage voyage, Employe employe, int heureTravail) {
        this.voyage = voyage;
        this.employe = employe;
        this.heureTravail = heureTravail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public int getHeureTravail() {
        return heureTravail;
    }

    public void setHeureTravail(int heureTravail) {
        this.heureTravail = heureTravail;
    }

    public static void insertVoyageEmploye(Connection connection, VoyageEmploye voyageEmploye) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO voyage_employe(id_voyage,id_emp,heures_travail) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, voyageEmploye.getVoyage().getId());
            statement.setLong(2, voyageEmploye.getEmploye().getId());
            statement.setInt(3,voyageEmploye.getHeureTravail());

            System.out.println("\n" + query + "\n");

            statement.executeUpdate();
            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();

    }


}
