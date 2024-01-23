package org.example.demo.models.promotionPoste;

import org.example.demo.models.travail.Employe;
import org.example.demo.models.travail.Fonction;

public class SituationProPersonne {
    private Employe employe;
    private Fonction fonction;
    private GradeFonction gradeFonction;

    public SituationProPersonne(Employe employe, Fonction fonction, GradeFonction gradeFonction) {
        this.employe = employe;
        this.fonction = fonction;
        this.gradeFonction = gradeFonction;
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

    public GradeFonction getGradeFonction() {
        return gradeFonction;
    }

    public void setGradeFonction(GradeFonction gradeFonction) {
        this.gradeFonction = gradeFonction;
    }

}
