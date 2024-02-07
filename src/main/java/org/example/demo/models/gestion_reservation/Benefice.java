package org.example.demo.models.gestion_reservation;

import org.example.demo.connexion.Connexion;
import org.example.demo.models.composition_voyage.Bouquet;
import org.example.demo.models.composition_voyage.CategorieLieu;
import org.example.demo.models.composition_voyage.TypeDuree;
import org.example.demo.models.composition_voyage.Voyage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Benefice {
    private Voyage voyage;
    private Double benefice;

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Double getBenefice() {
        return benefice;
    }

    public void setBenefice(Double benefice) {
        this.benefice = benefice;
    }

    public Benefice(Voyage voyage, Double benefice) {
        this.voyage = voyage;
        this.benefice = benefice;
    }

    public static List<Benefice> getBeneficeBetweenPrix(Connection connection, Double prixMin, Double prixMax) throws Exception {
        boolean new_connex = false;

        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "select * from vue_voyage_complet_benefice_total where benefice_voyage between ? and ?;";

        List<Benefice> listben=new ArrayList<>();

        Benefice ben;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1,prixMin);
            statement.setDouble(2,prixMax);
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Voyage voyage =new Voyage(
                            new Bouquet(    resultSet.getLong("id_bouquet"),
                                    resultSet.getString("nom_bouquet")
                            ),
                            new TypeDuree(  resultSet.getLong("id_duree"),
                                    resultSet.getString("nom")
                            ),
                            new CategorieLieu(  resultSet.getLong("id_categorie_lieu"),
                                    resultSet.getString("nom_categorie_lieu")
                            )
                    );
                    ben = new Benefice(voyage,resultSet.getDouble("benefice_voyage"));

                    listben.add(ben);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }

        if (new_connex)
            connection.close();

        return listben;
    }
}
