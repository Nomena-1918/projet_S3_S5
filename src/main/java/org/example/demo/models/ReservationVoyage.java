package org.example.demo.models;

import org.example.demo.database.Connexion;
import org.example.demo.models.travail.Voyage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReservationVoyage {
    private Long id;
    private int nombre_billet;
    private Voyage voyage;

    public ReservationVoyage( Voyage voyage,int nombre_billet) {
        this.nombre_billet = nombre_billet;
        this.voyage = voyage;
    }

    public int getNombre_billet() {
        return nombre_billet;
    }

    public void setNombre_billet(int nombre_billet) {
        this.nombre_billet = nombre_billet;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static void insertReservationVoyage(Connection connection, ReservationVoyage reservationVoyage) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }

        ReservationVoyage.checkNombreActivite(connection, reservationVoyage.getVoyage(), reservationVoyage.getNombre_billet());
        String query = "INSERT INTO reservation_voyage(id_voyage,nombre_billet) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,reservationVoyage.getVoyage().getId());
            statement.setLong(2, reservationVoyage.getNombre_billet());

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

    public static void checkNombreActivite(Connection connection,Voyage voyage, int nombreBillet) throws Exception {
        List<VoyageActivite> listVoyageActivite=VoyageActivite.getByVoyage(connection,voyage.getId());
        List<ResteActivite> resteActivitesInsufisant=new ArrayList<>();

        for (VoyageActivite voyageActivite: listVoyageActivite){
            List<ResteActivite> resteActivites=ResteActivite.selectWhere(connection,voyageActivite.getActivite().getId());

            for (ResteActivite restact: resteActivites) {
                if(restact.getResteBillet() < nombreBillet * voyageActivite.getNombre()){
                    resteActivitesInsufisant.add(restact);
                }
            }
        }

        if(resteActivitesInsufisant.size()>0){
            throw new Exception("Activite insuffisant :"+resteActivitesInsufisant);
        }

    }

    @Override
    public String toString() {
        return String.format("ReservationVoyage{id=%s}",getId());
    }

}
