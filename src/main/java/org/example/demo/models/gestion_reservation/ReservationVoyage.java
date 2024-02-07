package org.example.demo.models.gestion_reservation;

import org.example.demo.connexion.Connexion;
import org.example.demo.models.composition_voyage.VoyageActivite;
import org.example.demo.models.composition_voyage.ResteActivite;
import org.example.demo.models.composition_voyage.Voyage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class ReservationVoyage {
    private Long id;
    private int nombre_billet;
    private Voyage voyage;
    private Client client;


    public ReservationVoyage(int nombre_billet, Voyage voyage, Client client) {
        this.nombre_billet = nombre_billet;
        this.voyage = voyage;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
        String query = "INSERT INTO reservation_voyage(id_voyage,nombre_billet,id_client) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,reservationVoyage.getVoyage().getId());
            statement.setLong(2, reservationVoyage.getNombre_billet());
            statement.setInt(3, reservationVoyage.getClient().getId());

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

        if(!resteActivitesInsufisant.isEmpty()){
            throw new Exception("Activite insuffisant : "+resteActivitesInsufisant);
        }

    }

    @Override
    public String toString() {
        return String.format("ReservationVoyage{id=%s}",getId());
    }

}
