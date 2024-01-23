package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntreeActivite {
    private Long id;

    private Double prixUnitaire;
    private Integer quantite;

    public EntreeActivite(){

    }

    public EntreeActivite(Long id, Double prixUnitaire, Integer quantite) {
        this.id = id;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public void setId(Long id) {this.id = id;}

    public Long getId() {return id;}


    public static void insertEntreeActivite(Connection connection,EntreeActivite entreeActivite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO entree_activite(id_activite,prix_unitaire,quantite) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entreeActivite.getId());
            statement.setDouble(2, entreeActivite.getPrixUnitaire());
            statement.setInt(3, entreeActivite.getQuantite());

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

    public static List<EntreeActivite> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM entree_activite";
        List<EntreeActivite> listEntreeActivite = new ArrayList<>();
        EntreeActivite entreeActivite;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entreeActivite = new EntreeActivite();
                    entreeActivite.setId(resultSet.getLong("id"));
                    entreeActivite.setQuantite(resultSet.getInt("quantite"));
                    listEntreeActivite.add(entreeActivite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listEntreeActivite;
    }

    @Override
    public String toString() {
        return String.format("EntreeActivite{id=%s, nom='%s'}", getId(), getQuantite());
    }

}
