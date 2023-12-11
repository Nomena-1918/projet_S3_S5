package org.example.demo.models;


import org.example.demo.database.Connexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Emp {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private LocalDate dtn;
    private Integer genre;
    private Boolean actif;

    public Emp(String matricule, String nom, String prenom, LocalDate dtn, Integer genre, Boolean actif) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dtn = dtn;
        this.genre = genre;
        this.actif = actif;
    }

    public Emp() {
    }


    /////// CRUD //////////////
    public static void createEmp(Connection connection, Emp emp) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "INSERT INTO employee(id, matricule, nom, prenom, dtn, genre, actif) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, emp.getId());
            statement.setString(2, emp.getMatricule());
            statement.setString(3, emp.getNom());
            statement.setString(4, emp.getPrenom());
            statement.setDate(5, Date.valueOf(emp.getDtn()));
            statement.setInt(6, emp.getGenre());
            statement.setBoolean(7, emp.getActif());

            System.out.println("\n"+query+"\n");

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

    public static Emp readEmp(Connection connection, Long id) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM employee WHERE id = ?";
        Emp emp = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            System.out.println("\n"+query+"\n");


            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    emp = new Emp();
                    emp.setId(resultSet.getLong("id"));
                    emp.setMatricule(resultSet.getString("matricule"));
                    emp.setNom(resultSet.getString("nom"));
                    emp.setPrenom(resultSet.getString("prenom"));
                    emp.setDtn(resultSet.getDate("dtn").toLocalDate());
                    emp.setGenre(resultSet.getInt("genre"));
                    emp.setActif(resultSet.getBoolean("actif"));
                }
            }
        }

        if (new_connex)
            connection.close();

        return emp;
    }

    public static List<Emp> readAllEmp(Connection connection, int nb_lignes, int pagination_debut) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "SELECT * FROM emp ORDER BY id LIMIT ? OFFSET ?";
        List<Emp> listEmp = new ArrayList<>();
        Emp emp;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nb_lignes);
            statement.setInt(2, pagination_debut);

            System.out.println("\n"+query+"\n");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    emp = new Emp();
                    emp.setId(resultSet.getLong("id"));
                    emp.setMatricule(resultSet.getString("matricule"));
                    emp.setNom(resultSet.getString("nom"));
                    emp.setPrenom(resultSet.getString("prenom"));
                    emp.setDtn(resultSet.getDate("dtn").toLocalDate());
                    emp.setGenre(resultSet.getInt("genre"));
                    emp.setActif(resultSet.getBoolean("actif"));
                    listEmp.add(emp);
                }
            }
        }

        if (new_connex)
            connection.close();

        return listEmp;
    }

    public static void updateEmp(Connection connection, Emp emp) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "UPDATE employee SET matricule = ?, nom = ?, prenom = ?, dtn = ?, genre = ?, actif = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, emp.getMatricule());
            statement.setString(2, emp.getNom());
            statement.setString(3, emp.getPrenom());
            statement.setDate(4, Date.valueOf(emp.getDtn()));
            statement.setInt(5, emp.getGenre());
            statement.setBoolean(6, emp.getActif());
            statement.setLong(7, emp.getId());

            System.out.println("\n"+query+"\n");

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

    public static void deleteEmp(Connection connection, Long id) throws Exception {
        boolean new_connex = false;
        if(connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        String query = "DELETE FROM employee WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            System.out.println("\n"+query+"\n");

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
    ////////////////////////////////////////////////////


    public void setId(long id) {
        this.id = id;
    }

    private long getId() {
        return id;
    }


    //////////////////////////


    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDtn() {
        return dtn;
    }

    public void setDtn(LocalDate dtn) {
        this.dtn = dtn;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Integer getGenre() {
        return genre;
    }

        public String getGenreLettre() {
        if (genre==0)
            return "F";
        else
            return "H";
    }
    public String getActifLettre() {
        if (actif)
            return "Oui";
        else
            return "Non";
    }

    public Boolean getActif() {
        return actif;
    }

    @Override
    public String toString() {
        return """
           
               Emp{
                   id=%s,
                   matricule='%s',
                   nom='%s',
                   prenom='%s',
                   dtn=%s,
                   genre=%d,
                   actif=%s
               }
           """.formatted(getId(), getMatricule(), getNom(), getPrenom(), getDtn(), getGenre(), getActif());
    }
}
