package acteur;

import environment.Case;
import environment.Direction;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;            // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    protected int volActuelReservoir;                // a quel point le volActuelReservoir est rempli, sa taille se trouve dans les classes filles
    protected int tailleReservoir;

    protected int vitesseRemplissage;
    protected int vitesseDeversement;
    private boolean interventionEnCours = false;


    // Constructeur robots classiques
    protected Robot (Case pos, double speed, int vitesseRemplissage, int vitesseDeversement,int tailleReservoir) {
        this.position = pos;
        this.vitesseDeplacement = speed;
        this.vitesseRemplissage =  vitesseRemplissage;
        this.vitesseDeversement = vitesseDeversement;
        this.tailleReservoir = tailleReservoir;
        this.volActuelReservoir = tailleReservoir;
    }

    // Constructeur robots pattes
    protected Robot (Case pos, double vitesseDefaut, int vitesseDeversement) {
        this.position = pos;
        this.vitesseRemplissage = 0;
        this.vitesseDeplacement = vitesseDefaut;
        this.vitesseDeversement = vitesseDeversement;
        this.tailleReservoir = Integer.MAX_VALUE;
        this.volActuelReservoir = Integer.MAX_VALUE;
    }


    public boolean isInterventionEnCours() {
        return interventionEnCours;
    }
    public void setInterventionEnCours(boolean interventionEnCours) {this.interventionEnCours = interventionEnCours;}

    public int getVitesseDeversement() {
        return vitesseDeversement;
    }
    public void setVitesseDeversement(int vitesseDeversement) {
        this.vitesseDeversement = vitesseDeversement;
    }


    public Case getPosition() {
        return position;
    }

    public int getVitesseRemplissage() {
        return vitesseRemplissage;
    }
    public void setVitesseRemplissage(int vitesseRemplissage) {
        this.vitesseRemplissage = vitesseRemplissage;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public int getVolActuelReservoir() {
        return volActuelReservoir;
    }
    public void setVolActuelReservoir(int volActuelReservoir) {
        this.volActuelReservoir = volActuelReservoir;
    }

    void setPosition (Case case_obj){
        this.position = case_obj;
    }
    public abstract void moveNextCase (Direction dir);

    public int getTailleReservoir() {
        return tailleReservoir;
    }
    public void setTailleReservoir(int tailleReservoir) {
        this.tailleReservoir = tailleReservoir;
    }


    public abstract void RemplirReservoir();
    public abstract String getSpritePath();
    public abstract double getSpeedOnCase(Case pos);
}

