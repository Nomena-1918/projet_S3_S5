package org.example.demo.models.promotionPoste;

public class GradeFonction {
    private String nom;
    private Double tauxHoraireCoeff;
    private int debutAncien;
    private int finAncien;

    public GradeFonction(String nom, Double tauxHoraireCoeff, int debutAncien, int finAncien) {
        this.nom = nom;
        this.tauxHoraireCoeff = tauxHoraireCoeff;
        this.debutAncien = debutAncien;
        this.finAncien = finAncien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTauxHoraireCoeff() {
        return tauxHoraireCoeff;
    }

    public void setTauxHoraireCoeff(Double tauxHoraireCoeff) {
        this.tauxHoraireCoeff = tauxHoraireCoeff;
    }

    public int getDebutAncien() {
        return debutAncien;
    }

    public void setDebutAncien(int debutAncien) {
        this.debutAncien = debutAncien;
    }

    public int getFinAncien() {
        return finAncien;
    }

    public void setFinAncien(int finAncien) {
        this.finAncien = finAncien;
    }
}
