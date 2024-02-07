package org.example.demo.models.stats;


import org.example.demo.connexion.Connexion;
import org.example.demo.models.composition_voyage.Activite;
import org.example.demo.models.gestion_personnel.Genre;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Table("vue_stats_genre")
public class StatistiqueGenre {
    private static final DAO dao;
    static {
        dao=new DAO(
                Connexion.database,
                Connexion.host,
                Connexion.port,
                Connexion.username,
                Connexion.password,
                Connexion.use_ssl,
                Connexion.SGBD);
    }

    @ForeignKey(recursive = true)
    @Column("id_sexe")
    private Genre genre;
    @ForeignKey(recursive = true)
    @Column("id_activite")
    private Activite activite;
    @Column("nombre")
    Integer nombre;
    @Column("prix_total")
    Double prixTotal;

    public StatistiqueGenre(Genre genre, Activite activite, int nombre, double prixTotal) {
        this.genre = genre;
        this.activite = activite;
        this.nombre = nombre;
        this.prixTotal = prixTotal;
    }

    public StatistiqueGenre() {
    }

    public Genre getSexe() {
        return genre;
    }

    public void setSexe(Genre genre) {
        this.genre = genre;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }


    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public static List<StatistiqueGenre> readAll(Connection connection, Genre genre, Activite activite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        StatistiqueGenre[] statistiquesSexe;

        // Par défaut :
        if (activite == null && genre == null)
            return Arrays.asList(dao.select(connection, StatistiqueGenre.class));

        // Recherche par activité
        else {
            StatistiqueGenre where = new StatistiqueGenre();
            if (genre != null) {
                where.setSexe(genre);
            }

            if (activite != null) {
                where.setActivite(activite);
            }
            statistiquesSexe=dao.select(connection, StatistiqueGenre.class,where);
        }

        if (new_connex)
            connection.close();

        return Arrays.asList(statistiquesSexe);
    }
}
