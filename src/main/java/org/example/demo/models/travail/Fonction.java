package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Activite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import veda.godao.annotations.*;

@Table("fonction_employe")
public class Fonction {

    @PrimaryKey
    @Column("id")
    private Long id;
    @Column("nom")
    private String nom;
    @Column("salaire_horaire")
    private Double salaireHoraire;

    public Fonction(String nom, Double salaireHoraire) {
        this.nom = nom;
        this.salaireHoraire = salaireHoraire;
    }

    public Fonction() {
    }

    public Fonction(Long id) {
        this.id = id;
    }

    public Fonction(Long id, String nom, Double salaireHoraire) {
        this.id = id;
        this.nom = nom;
        this.salaireHoraire = salaireHoraire;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getSalaireHoraire() {
        return salaireHoraire;
    }

    public void setSalaireHoraire(Double salaireHoraire) {
        this.salaireHoraire = salaireHoraire;
    }
    public static void insertFonction(Connection connection, Fonction fonction) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO fonction_employe(nom,salaire_horaire) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fonction.getNom());
            statement.setDouble(2, fonction.getSalaireHoraire());

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

    public static List<Fonction> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        List<Fonction> listFonction = new ArrayList<>();
        Fonction fonction;
        String query = "SELECT * FROM fonction_employe";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    fonction = new Fonction(
                            resultSet.getLong("id"),
                            resultSet.getString("nom"),
                            resultSet.getDouble("salaire_horaire")
                    );
                    listFonction.add(fonction);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listFonction;
    }
}
