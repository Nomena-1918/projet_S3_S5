package org.example.demo.models.travail;

public class VoyageEmploye {
    private Long id;
    private Voyage voyage;
    private Employe employe;
    private Long heureTravail;

    public VoyageEmploye(Voyage voyage, Employe employe, Long heureTravail) {
        this.voyage = voyage;
        this.employe = employe;
        this.heureTravail = heureTravail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Long getHeureTravail() {
        return heureTravail;
    }

    public void setHeureTravail(Long heureTravail) {
        this.heureTravail = heureTravail;
    }
}
