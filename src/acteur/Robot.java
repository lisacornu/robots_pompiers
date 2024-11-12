package acteur;

import Simulation.Deplacement;
import Simulation.Evenement;
import Simulation.Simulateur;
import environment.Case;
import environment.Direction;
import pathFinding.Chemin;
import pathFinding.Dijkstra;

import java.util.ArrayList;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;            // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    protected int volActuelReservoir;                // a quel point le volActuelReservoir est rempli, sa taille se trouve dans les classes filles
    protected int tailleReservoir;

    protected int vitesseRemplissage;
    protected int vitesseDeversement;
    private boolean interventionEnCours = false; //booléen pour regarder si le robot intervient sur un feu
    private final ArrayList<Evenement> evenementEnAttente = new ArrayList<>();  //Liste tous les evenements en attente que le robot doit effectuer
    private boolean evenementEnCours = false; //booléen pour verifier si le robot est en train d'effectuer une action

    public ArrayList<Evenement> getEvenementEnAttente() {
        return evenementEnAttente;
    }

    public boolean isEvenementEnCours() {
        return evenementEnCours;
    }

    public void setEvenementEnCours(boolean evenementEnCours) {
        this.evenementEnCours = evenementEnCours;
    }

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

    public void addEvenementEnAttente(Evenement e) {this.evenementEnAttente.add(e);}
    public void removeEvenementEnAttente(Evenement e) {this.evenementEnAttente.remove(e);}


    public void setEvenement(Evenement e) {
        if(!isEvenementEnCours()) {
            //Si le robot est en attente, alors ona joute l'evenement e dans la liste des evenements en cours
            //et on enlève le robot de son état d'attente

            Simulateur.getExecutingEvent().add(e);
            this.setEvenementEnCours(true);
        }
        else{
            //Sinon, on ajoute e à la liste d'attente des actions du robot.

            this.addEvenementEnAttente(e);
        }
    }

    /**
     * @param dest Case vers laquelle on cherche le plus court chemin
     * @return instance de Chemin (temps et suite de direction) représentant le plus cours chemin vers dest
     */
    public Chemin getPlusCourtChemin (Case dest) {
        return Dijkstra.getPlusCourtChemin(this.position, dest, this);
    }

    public void goToDestination (ArrayList<Direction> descChemin) {
        int date = 0;
        for (Direction dir : descChemin) {
            Deplacement deplacement = new Deplacement(date, this, dir);
            Simulateur.ajouteEvenement(deplacement);
            date++;
        }
    }

    public boolean isInterventionEnCours() {return interventionEnCours;}
    public void     setInterventionEnCours(boolean interventionEnCours) {this.interventionEnCours = interventionEnCours;}

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


    public abstract void remplirReservoir();
    public abstract String getSpritePath();
    public abstract double getSpeedOnCase(Case pos);
}

