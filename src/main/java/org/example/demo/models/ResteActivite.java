package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResteActivite {
    private Activite resteActivite;
    private int resteBillet;

    public ResteActivite() {

    }

    public Activite getActivite() {
        return resteActivite;
    }

    public void setActivite(Activite resteActivite) {
        this.resteActivite = resteActivite;
    }

    public int getResteBillet() {
        return resteBillet;
    }

    public void setResteBillet(int resteBillet) {
        this.resteBillet = resteBillet;
    }

    public ResteActivite(Activite resteActivite, int resteBillet) {
        this.resteActivite = resteActivite;
        this.resteBillet = resteBillet;
    }

    public static List<ResteActivite> selectWhere(Connection connection, Long idCategorie, Long idType, Long idBouquet, Long idActivite) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = """
SELECT id_activite,nom_activite,nombre_billet_restant FROM reste_activite_voyage 
where id_categorie_lieu=? and id_type_duree=? and id_bouquet=?
""";

        if(idActivite!=null){
            query+=" and id_activite=?";
        }
        List<ResteActivite> listResteActivite = new ArrayList<>();
        ResteActivite resteActivite;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,idCategorie);
            statement.setLong(2,idType);
            statement.setLong(3,idBouquet);
            if(idActivite!=null){
                statement.setLong(4,idActivite);
            }
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resteActivite = new ResteActivite(
                            new Activite(resultSet.getLong("id_activite"), resultSet.getString("nom_activite"))
                            ,resultSet.getInt("nombre_billet_restant")
                    );
                    listResteActivite.add(resteActivite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listResteActivite;
    }

    public static List<ResteActivite> findAll(Connection connection,Long idActivite) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = """
SELECT id_activite,nom_activite,nombre_billet_restant FROM reste_activite_voyage 
""";

        if(idActivite!=null){
            query+=" where id_activite=?";
        }
        List<ResteActivite> listResteActivite = new ArrayList<>();
        ResteActivite resteActivite;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if(idActivite!=null){
                statement.setLong(1,idActivite);
            }
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resteActivite = new ResteActivite(
                            new Activite(resultSet.getLong("id_activite"), resultSet.getString("nom_activite"))
                            ,resultSet.getInt("nombre_billet_restant")
                    );
                    listResteActivite.add(resteActivite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (new_connex)
            connection.close();

        return listResteActivite;
    }

    @Override
    public String toString() {
        return String.format("ResteActivite{idActivite=%s, nomActivite='%s', resteBillet='%s'}",getActivite().getId(),getActivite().getNom(),getResteBillet());
    }

}
