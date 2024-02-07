package org.voyage.demo.models.composition_voyage;

import org.voyage.demo.connexion.Connexion;

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

    public static List<ResteActivite> selectWhere(Connection connection, Integer idActivite) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        String query = "SELECT id_activite, quantite_reste FROM vue_reste_activite_voyage ";
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
                            new Activite(resultSet.getInt("id_activite"))
                            ,resultSet.getInt("quantite_reste")
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

    public static List<ResteActivite> findAll(Connection connection, Long idActivite) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        String query = "SELECT id_activite,quantite_reste,nom_activite FROM vue_reste_activite_complet_voyage ";
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
                            new Activite(resultSet.getInt("id_activite"),resultSet.getString("nom_activite"))
                            ,resultSet.getInt("quantite_reste")
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
