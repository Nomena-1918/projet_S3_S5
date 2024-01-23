package org.example.demo.models.promotionPoste;

import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;

import java.time.LocalDate;

public class Embauche {
    private Employe employe;
    private Fonction fonction;
    private LocalDate date;

    public Embauche(Employe employe, Fonction fonction, LocalDate date) {
        this.employe = employe;
        this.fonction = fonction;
        this.date = date;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
