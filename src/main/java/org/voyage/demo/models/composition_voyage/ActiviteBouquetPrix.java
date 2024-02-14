package org.voyage.demo.models.composition_voyage;

import org.voyage.demo.connexion.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiviteBouquetPrix extends VoyageActivite {
    private Double prix;


    public ActiviteBouquetPrix(Double prix) {
        this.prix = prix;
    }

    public ActiviteBouquetPrix() {
        super();
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }


    public static List<ActiviteBouquetPrix> getVoyageBetweenPrix(Connection connection,Double prixMin,Double prixMax) throws Exception {
        boolean new_connex = false;

        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "Select * from vue_activite_bouquet_nombre_prix where prix_total between ? and ?";

        List<ActiviteBouquetPrix> listba=new ArrayList<>();

        ActiviteBouquetPrix activiteBouquetPrix;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1,prixMin);
            statement.setDouble(2,prixMax);
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    activiteBouquetPrix = new ActiviteBouquetPrix(resultSet.getDouble("prix_total"));
                    activiteBouquetPrix.setVoyage(new Voyage(
                            new Bouquet(    resultSet.getInt("id_bouquet"),
                                    resultSet.getString("nom_bouquet")
                            ),
                            new TypeDuree(  resultSet.getInt("id_type_duree"),
                                    resultSet.getString("nom_type_duree"),
                                    resultSet.getInt("debutjour"),
                                    resultSet.getInt("finjour")
                            ),
                            new CategorieLieu(  resultSet.getInt("id_categorie_lieu"),
                                    resultSet.getString("nom_categorie_lieu")
                            )
                    ));
                    listba.add(activiteBouquetPrix);
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

        return listba;
    }


}
