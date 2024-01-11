package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReservationVoyage {
    private Long id;
    private Long idCategorieLieu;
    private Long idTypeDuree;
    private int nombre_billet;
    private  Long idBouquet;

    public ReservationVoyage() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategorieLieu() {
        return idCategorieLieu;
    }

    public void setIdCategorieLieu(Long idCategorieLieu) {
        this.idCategorieLieu = idCategorieLieu;
    }

    public Long getIdTypeDuree() {
        return idTypeDuree;
    }

    public void setIdTypeDuree(Long idTypeDuree) {
        this.idTypeDuree = idTypeDuree;
    }

    public int getNombre_billet() {
        return nombre_billet;
    }

    public void setNombre_billet(int nombre_billet) {
        this.nombre_billet = nombre_billet;
    }

    public Long getIdBouquet() {
        return idBouquet;
    }

    public void setIdBouquet(Long idBouquet) {
        this.idBouquet = idBouquet;
    }

    public ReservationVoyage( Long idCategorieLieu, Long idTypeDuree, int nombre_billet, Long idBouquet) {
        this.idCategorieLieu = idCategorieLieu;
        this.idTypeDuree = idTypeDuree;
        this.nombre_billet = nombre_billet;
        this.idBouquet = idBouquet;
    }

    public static void insertReservationVoyage(Connection connection, ReservationVoyage reservationVoyage) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        ReservationVoyage.checkNombreActivite(connection, reservationVoyage.getIdCategorieLieu(), reservationVoyage.getIdTypeDuree(), reservationVoyage.getIdBouquet(), reservationVoyage.getNombre_billet());
        String query = "INSERT INTO reservation_voyage(id_categorie_lieu,id_duree,id_bouquet,nombre_billet) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, reservationVoyage.getIdCategorieLieu());
            statement.setLong(2, reservationVoyage.getIdTypeDuree());
            statement.setLong(3, reservationVoyage.getIdBouquet());
            statement.setLong(4, reservationVoyage.getNombre_billet());

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

    public static void checkNombreActivite(Connection connection, Long idCategorie, Long idType, Long idBouquet, int nombreBillet) throws Exception {
        List<ResteActivite> resteActivites=ResteActivite.selectWhere(connection,idCategorie,idType,idBouquet,null);
        List<ResteActivite> resteActivitesInsufisant=new ArrayList<>();

        for (ResteActivite item: resteActivites) {
            if(item.getResteBillet() < nombreBillet){
                resteActivitesInsufisant.add(item);
            }
        }

        if(resteActivitesInsufisant.size()>0){
            throw new Exception("Activite insuffisant :"+resteActivitesInsufisant);
        }

    }

    @Override
    public String toString() {
        return String.format("ReservationVoyage{id=%s,idactivite=%s,nomactivite='%s',idbouquet=%s, nombouquet='%s'}",getId(),getIdBouquet());
    }

}
