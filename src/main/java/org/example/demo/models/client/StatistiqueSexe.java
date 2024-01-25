package org.example.demo.models.client;


import org.example.demo.database.Connexion;
import org.example.demo.models.Activite;
import org.example.demo.models.promotionPoste.Sexe;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.Table;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

@Table("vue_stats_genre")
public class StatistiqueSexe {
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
    private Sexe sexe;

    @ForeignKey(recursive = true)
    @Column("id_activite")
    private Activite activite;
    @Column("nombre")
    int nombre;
    @Column("prix_total")
    double prixTotal;

    public StatistiqueSexe(Sexe sexe, Activite activite, int nombre, double prixTotal) {
        this.sexe = sexe;
        this.activite = activite;
        this.nombre = nombre;
        this.prixTotal = prixTotal;
    }

    public StatistiqueSexe() {
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public static List<StatistiqueSexe> readAll(Connection connection,Sexe sexe,Activite activite) throws Exception {
        boolean new_connex = false;
        if (connection == null) {
            connection = Connexion.getConnexionPostgreSql();
            new_connex = true;
        }
        StatistiqueSexe[] statistiquesSexe;

        // Par défaut :
        if (activite==null)
            statistiquesSexe=dao.select(connection,StatistiqueSexe.class);

        // Recherche par activité
        else {
            StatistiqueSexe where = new StatistiqueSexe();
            where.setSexe(sexe);
            where.setActivite(activite);
            statistiquesSexe=dao.select(connection,StatistiqueSexe.class,where);
        }

        if (new_connex)
            connection.close();

        return Arrays.asList(statistiquesSexe);
    }
}
