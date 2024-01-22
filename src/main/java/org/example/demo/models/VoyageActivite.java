package org.example.demo.models;

import org.example.demo.database.Connexion;
import org.example.demo.models.travail.Voyage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VoyageActivite {
    private Long id;
    private Activite activite;
    private Voyage voyage;
    private int nombre;

    public VoyageActivite(){

    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public VoyageActivite(Activite activite, Voyage voyage, int nombre) {
        this.activite = activite;
        this.voyage = voyage;
        this.nombre = nombre;
    }

    public static void insertVoyageActivite(Connection connection, VoyageActivite voyageActivite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO voyage_activite(id_voyage,id_activite,nombre) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, voyageActivite.getActivite().getId());
            statement.setLong(2, voyageActivite.getVoyage().getId());
            statement.setLong(3, voyageActivite.getNombre());

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
                    voyageActivite.setActivite(new Activite(resultSet.getLong("id_activite"),
                                                            resultSet.getString("nom_activite")));
                    voyageActivite.setVoyage(new Voyage(
                            new Bouquet(resultSet.getLong("id_bouquet"),
                                        resultSet.getString("nom_bouquet")))
                    );
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
        List<VoyageActivite> listVA=new ArrayList<>();

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
                    voyageActivite.setActivite(
                            new Activite(   resultSet.getLong("id_activite"),
                                            resultSet.getString("nom_activite"))
                    );
                    voyageActivite.setVoyage(new Voyage(
                            new Bouquet(    resultSet.getLong("id_bouquet"),
                                            resultSet.getString("nom_bouquet")
                            ),
                            new TypeDuree(  resultSet.getLong("id_type_duree"),
                                            resultSet.getString("nom_type_duree"),
                                            resultSet.getInt("debutjour"),
                                            resultSet.getInt("finjour")
                            ),
                            new CategorieLieu(  resultSet.getLong("id_categorie_lieu"),
                                                resultSet.getString("nom_categorie_lieu")
                            )
                    ));
                    voyageActivite.setNombre(resultSet.getInt("nombre"));
                    listVA.add(voyageActivite);
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

        return listVA;
    }

    public static List<Voyage> readAll(Connection connection) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        List<Voyage> listVoyage = new ArrayList<>();
        String query = "select * from voyage_activite";
        Voyage voyage;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyage = new Voyage();
                    voyage.setId(resultSet.getLong("id"));
                    voyage.setBouquet(new Bouquet(  resultSet.getLong("id_bouquet"),
                            resultSet.getString("nom_bouquet")));
                    voyage.setCategorieLieu(new CategorieLieu(  resultSet.getLong("id_categorie_lieu"),
                            resultSet.getString("nom_categorie_lieu")));
                    voyage.setTypeDuree(new TypeDuree(  resultSet.getLong("id_duree"),
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

    @Override
    public String toString() {
        return String.format("VoyageActivite{id=%s}",getId());
    }

}
