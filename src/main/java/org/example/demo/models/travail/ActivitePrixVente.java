package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.Activite;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivitePrixVente {
    private Long id;
    private Activite activite;
    private Double prixVente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public ActivitePrixVente(Long id, Activite activite, Double prixVente) {
        this.id = id;
        this.activite = activite;
        this.prixVente = prixVente;
    }

    public ActivitePrixVente(Activite activite, Double prixVente) {
        this.activite = activite;
        this.prixVente = prixVente;
    }

    public static void insertActivitePrixVente(Connection connection, ActivitePrixVente activitePrixVente) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO prix_vente_activite(id_activite,prix_vente) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, activitePrixVente.getActivite().getId());
            statement.setDouble(2, activitePrixVente.getPrixVente());

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
