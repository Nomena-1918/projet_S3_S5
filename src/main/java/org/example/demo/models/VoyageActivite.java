package org.example.demo.models;

import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VoyageActivite {
    private Long id;
    private Long idActivite;
    private String nomActivite;

    private Long idCategorieLieu;
    private String nomCategorieLieu;

    private Long idTypeDuree;
    private String nomTypeDuree;

    private int nombre;
    private  Long idBouquet;
    private String nomBouquet;

    private int debutJour;
    private int finJour;

    public VoyageActivite(Long id,Long idActivite,String nomActivite,Long idBouquet,String nomBouquet){
        this.id=id;
        this.idActivite=idActivite;
        this.nomActivite=nomActivite;
        this.idBouquet=idBouquet;
        this.nomBouquet=nomBouquet;
    }

    public VoyageActivite(Long id, Long idActivite, String nomActivite, Long idCategorieLieu, String nomCategorieLieu, Long idTypeDuree, String nomTypeDuree, int nombre, Long idBouquet, String nomBouquet) {
        this.id = id;
        this.idActivite = idActivite;
        this.nomActivite = nomActivite;
        this.idCategorieLieu = idCategorieLieu;
        this.nomCategorieLieu = nomCategorieLieu;
        this.idTypeDuree = idTypeDuree;
        this.nomTypeDuree = nomTypeDuree;
        this.nombre = nombre;
        this.idBouquet = idBouquet;
        this.nomBouquet = nomBouquet;
    }

    public VoyageActivite(){

    }

    public int getDebutJour() {
        return debutJour;
    }

    public void setDebutJour(int debutJour) {
        this.debutJour = debutJour;
    }

    public int getFinJour() {
        return finJour;
    }

    public void setFinJour(int finJour) {
        this.finJour = finJour;
    }

    public Long getIdCategorieLieu() {
        return idCategorieLieu;
    }

    public void setIdCategorieLieu(Long idCategorieLieu) {
        this.idCategorieLieu = idCategorieLieu;
    }

    public String getNomCategorieLieu() {
        return nomCategorieLieu;
    }

    public void setNomCategorieLieu(String nomCategorieLieu) {
        this.nomCategorieLieu = nomCategorieLieu;
    }

    public Long getIdTypeDuree() {
        return idTypeDuree;
    }

    public void setIdTypeDuree(Long idTypeDuree) {
        this.idTypeDuree = idTypeDuree;
    }

    public String getNomTypeDuree() {
        return nomTypeDuree;
    }

    public void setNomTypeDuree(String nomTypeDuree) {
        this.nomTypeDuree = nomTypeDuree;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
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

    public static void insertActiviteBouquet(Connection connection, VoyageActivite voyageActivite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO bouquet_activite(id_activite,id_bouquet,id_categorie_lieu,id_duree,nombre) VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, voyageActivite.getIdActivite());
            statement.setLong(2, voyageActivite.getIdBouquet());
            statement.setLong(3, voyageActivite.getIdCategorieLieu());
            statement.setLong(4, voyageActivite.getIdTypeDuree());
            statement.setLong(5, voyageActivite.getNombre());

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

    public static List<VoyageActivite> findActiviteBouquet(Connection connection, Long idBouquet) throws Exception {
        boolean new_connex = false;
        List<VoyageActivite> listba=new ArrayList<>();

        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "select * from vue_activite_bouquet where id_bouquet=?";


        VoyageActivite voyageActivite;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,idBouquet);
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyageActivite = new VoyageActivite();
                    voyageActivite.setId(resultSet.getLong("id"));
                    voyageActivite.setIdActivite(resultSet.getLong("id_activite"));
                    voyageActivite.setNomActivite(resultSet.getString("nom_activite"));
                    voyageActivite.setIdBouquet(resultSet.getLong("id_bouquet"));
                    voyageActivite.setNomBouquet(resultSet.getString("nom_bouquet"));
                    listba.add(voyageActivite);
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

    public static List<VoyageActivite> getByActivite(Connection connection, Long idActivite) throws Exception {
        boolean new_connex = false;
        List<VoyageActivite> listba=new ArrayList<>();

        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "select * from vue_activite_bouquet_nombre where id_activite=?";


        VoyageActivite voyageActivite;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,idActivite);
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyageActivite = new VoyageActivite();
                    voyageActivite.setId(resultSet.getLong("id"));
                    voyageActivite.setIdActivite(resultSet.getLong("id_activite"));
                    voyageActivite.setNomActivite(resultSet.getString("nom_activite"));
                    voyageActivite.setIdBouquet(resultSet.getLong("id_bouquet"));
                    voyageActivite.setNomBouquet(resultSet.getString("nom_bouquet"));
                    voyageActivite.setIdCategorieLieu(resultSet.getLong("id_categorie_lieu"));
                    voyageActivite.setNombre(resultSet.getInt("nombre"));
                    voyageActivite.setNomCategorieLieu(resultSet.getString("nom_categorie_lieu"));
                    voyageActivite.setIdTypeDuree(resultSet.getLong("id_type_duree"));
                    voyageActivite.setNomTypeDuree(resultSet.getString("nom_type_duree"));
                    voyageActivite.setDebutJour(resultSet.getInt("debutjour"));
                    voyageActivite.setFinJour(resultSet.getInt("finjour"));
                    listba.add(voyageActivite);
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
        return String.format("VoyageActivite{id=%s,idactivite=%s,nomactivite='%s',idbouquet=%s, nombouquet='%s'}",getId(), getIdActivite(),getNomActivite(),getIdBouquet(),getNomBouquet());
    }

}
