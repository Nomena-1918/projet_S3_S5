package org.example.demo.models.travail;

import org.example.demo.database.Connexion;
import org.example.demo.models.*;

import javax.swing.plaf.BorderUIResource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Voyage {
    private Long id;
    private Bouquet bouquet;
    private TypeDuree typeDuree;
    private CategorieLieu categorieLieu;

    public Voyage(Long id, Bouquet bouquet, TypeDuree typeDuree, CategorieLieu categorieLieu) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    

    public static List<Voyage> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
//      Test
        List<Voyage> listActivite = new ArrayList<>();
        List<ActiviteBouquetPrix> listActiviteBouquet=ActiviteBouquetPrix.getVoyageBetweenPrix(connection,0.0,10000000.0);
        for (ActiviteBouquetPrix item: listActiviteBouquet) {
            Voyage voyage=new Voyage(1L,new Bouquet(item.getIdBouquet(),item.getNomBouquet()),
                                    new TypeDuree(item.getIdTypeDuree(),item.getNomTypeDuree()),
                                    new CategorieLieu(item.getIdCategorieLieu(),item.getNomCategorieLieu()));
            listActivite.add(voyage);
        }
//        String query = "SELECT * FROM voyage";
//        Voyage voyage;
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            System.out.println("\n"+query+"\n");
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    voyage = new Voyage();
//                    voyage.setId(resultSet.getLong("id"));
//                    listActivite.add(voyage);
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }

        if (new_connex)
            connection.close();

        return listActivite;
    }
}
