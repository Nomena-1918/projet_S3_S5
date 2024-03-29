package org.voyage.demo.models.composition_voyage;

import org.voyage.demo.connexion.Connexion;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Table("voyage")
public class Voyage {
    @PrimaryKey
    @Column("id")
    private Integer id;

    @ForeignKey(recursive = true)
    @Column("id_bouquet")
    private Bouquet bouquet;

    @ForeignKey(recursive = true)
    @Column("id_duree")
    private TypeDuree typeDuree;

    @ForeignKey(recursive = true)
    @Column("id_categorie_lieu")
    private CategorieLieu categorieLieu;

    public Voyage(Integer id, Bouquet bouquet, TypeDuree typeDuree, CategorieLieu categorieLieu) {
        this.id = id;
        this.bouquet = bouquet;
        this.typeDuree = typeDuree;
        this.categorieLieu = categorieLieu;
    }

    public Voyage(Bouquet bouquet, TypeDuree typeDuree, CategorieLieu categorieLieu) {
        this.bouquet = bouquet;
        this.typeDuree = typeDuree;
        this.categorieLieu = categorieLieu;
    }

    public Voyage() {

    }

    public Voyage(Integer id) {
        this.id = id;
    }

    public Voyage(Bouquet bouquet) {
        this.bouquet = bouquet;
    }


    public Integer getId() {
        return id;
    }

    public Voyage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    public TypeDuree getTypeDuree() {
        return typeDuree;
    }

    public void setTypeDuree(TypeDuree typeDuree) {
        this.typeDuree = typeDuree;
    }

    public CategorieLieu getCategorieLieu() {
        return categorieLieu;
    }

    public void setCategorieLieu(CategorieLieu categorieLieu) {
        this.categorieLieu = categorieLieu;
    }

    public static void insertVoyage(Connection connection, Voyage voyage) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO voyage(id_bouquet,id_categorie_lieu,id_duree) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, voyage.getBouquet().getId());
            statement.setLong(2, voyage.getCategorieLieu().getId());
            statement.setLong(3, voyage.getTypeDuree().getId());

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

    public static List<Voyage> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        List<Voyage> listVoyage = new ArrayList<>();
        String query = "select * from vue_voyage_complet";
        Voyage voyage;
          try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyage = new Voyage();
                    voyage.setId(resultSet.getInt("id"));
                    voyage.setBouquet(new Bouquet(  resultSet.getInt("id_bouquet"),
                                                    resultSet.getString("nom_bouquet")));
                    voyage.setCategorieLieu(new CategorieLieu(  resultSet.getInt("id_categorie_lieu"),
                                                                resultSet.getString("nom_categorie_lieu")));
                    voyage.setTypeDuree(new TypeDuree(  resultSet.getInt("id_duree"),
                                                        resultSet.getString("nom")));
                    listVoyage.add(voyage);
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

        return listVoyage;
    }
}
