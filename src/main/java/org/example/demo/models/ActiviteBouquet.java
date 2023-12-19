package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ActiviteBouquet {
    private Long id;
    private Long idActivite;
    private String nomActivite;
    private  Long idBouquet;
    private String nomBouquet;

    public ActiviteBouquet(Long id,Long idActivite,String nomActivite,Long idBouquet,String nomBouquet){
        this.id=id;
        this.idActivite=idActivite;
        this.nomActivite=nomActivite;
        this.idBouquet=idBouquet;
        this.nomBouquet=nomBouquet;
    }

    public ActiviteBouquet(Long idActivite,Long idBouquet){
        this.idActivite=idActivite;
        this.idBouquet=idBouquet;
    }


    public ActiviteBouquet(){

    }


    public void setId(Long id) {this.id = id;}

    public void setIdActivite(Long idActivite) {this.idActivite = idActivite;}

    public void setNomActivite(String nomActivite) {this.nomActivite = nomActivite;}

    public void setIdBouquet(Long idBouquet) {this.idBouquet = idBouquet;}

    public void setNomBouquet(String nomBouquet) {this.nomBouquet = nomBouquet;}

    public Long getId() {return id;}

    public Long getIdActivite() {return idActivite;}

    public String getNomActivite() {return nomActivite;}

    public Long getIdBouquet() {return idBouquet;}

    public String getNomBouquet() {return nomBouquet;}

    public static void insertActiviteBouquet(Connection connection, ActiviteBouquet activitebouquet) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO bouquet_activite(id_activite,id_bouquet) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, activitebouquet.getIdActivite());
            statement.setLong(2, activitebouquet.getIdBouquet());

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

    public static List<ActiviteBouquet> findActiviteBouquet(Connection connection, Long idBouquet) throws Exception {
        boolean new_connex = false;
        List<ActiviteBouquet> listba=new ArrayList<>();

        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "select * from vue_activite_bouquet where id_bouquet=?";


        ActiviteBouquet activitebouquet;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,idBouquet);
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    activitebouquet = new ActiviteBouquet();
                    activitebouquet.setId(resultSet.getLong("id"));
                    activitebouquet.setIdActivite(resultSet.getLong("id_activite"));
                    activitebouquet.setNomActivite(resultSet.getString("nom_activite"));
                    activitebouquet.setIdBouquet(resultSet.getLong("id_bouquet"));
                    activitebouquet.setNomBouquet(resultSet.getString("nom_bouquet"));
                    listba.add(activitebouquet);
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

    @Override
    public String toString() {
        return String.format("ActiviteBouquet{id=%s,idactivite=%s,nomactivite='%s',idbouquet=%s, nombouquet='%s'}",getId(), getIdActivite(),getNomActivite(),getIdBouquet(),getNomBouquet());
    }

}
