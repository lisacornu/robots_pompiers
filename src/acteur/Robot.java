package acteur;

import Simulation.Deplacement;
import Simulation.Evenement;
import Simulation.Intervention;
import Simulation.Simulateur;
import environment.Case;
import environment.Direction;
import pathFinding.Chemin;
import pathFinding.Dijkstra;

import java.util.ArrayList;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;
    protected int volActuelReservoir;
    protected int tailleReservoir;
    protected int vitesseRemplissage;
    protected int vitesseDeversement;

    private boolean interventionEnCours = false; //est-ce que le robot est en train d'intervenir sur un feu
    private final ArrayList<Evenement> evenementEnAttente = new ArrayList<>();  //Liste les prochains evenements que le robot doit effectuer
    private boolean evenementEnCours = false; //est-ce que le robot est en train d'effectuer une action
    private boolean isSearchingForWater = false;

    public abstract boolean isFlying();

    public ArrayList<Evenement> getEvenementEnAttente() {
        return evenementEnAttente;
    }

    public boolean isEvenementEnCours() {
        return evenementEnCours;
    }

    public void setEvenementEnCours(boolean evenementEnCours) {
        this.evenementEnCours = evenementEnCours;
    }

    /**
     * Constructeur classiques pour robots
     * @param pos position initiale du robot
     * @param speed vitesse lue dans le fichier
     * @param vitesseRemplissage vitesse de remplissage du robot
     * @param vitesseDeversement vitesse vidage de l'eau
     * @param tailleReservoir taille du réservoir
     */
    protected Robot (Case pos, double speed, int vitesseRemplissage, int vitesseDeversement,int tailleReservoir) {
        this.position = pos;
        this.vitesseDeplacement = speed;
        this.vitesseRemplissage =  vitesseRemplissage;
        this.vitesseDeversement = vitesseDeversement;
        this.tailleReservoir = tailleReservoir;
        this.volActuelReservoir = tailleReservoir;
    }


    /**
     * Constucteur pour robot à pattes car réservoir spécial
     * @param pos position initiale
     * @param vitesseDefaut vitesse de déplacement du robot
     * @param vitesseDeversement vitesse de vidage du robot
     */
    protected Robot (Case pos, double vitesseDefaut, int vitesseDeversement) {
        this.position = pos;
        this.vitesseRemplissage = 0;
        this.vitesseDeplacement = vitesseDefaut;
        this.vitesseDeversement = vitesseDeversement;
        this.tailleReservoir = Integer.MAX_VALUE;
        this.volActuelReservoir = Integer.MAX_VALUE;
    }

    public boolean isSearchingForWater() {
        return isSearchingForWater;
    }

    public void setSearchingForWater(boolean searchingForWater) {
        isSearchingForWater = searchingForWater;
    }

    public void addEvenementEnAttente(Evenement e) {this.evenementEnAttente.add(e);}


    /**
     * Si le robot est en attente, alors on ajoute l'evenement e dans la liste des evenements en cours et on enlève
     * le robot de son état d'attente, sinon on ajoute e à la liste d'attente des actions du robot
     * @param e Evenement à effectuer
     */
    public void setEvenement(Evenement e) {
        if(!isEvenementEnCours()) {
            Simulateur.getExecutingEvent().add(e);
            this.setEvenementEnCours(true);
        }
        else{
            this.addEvenementEnAttente(e);
        }
    }


    /**
     * @param dest Case vers laquelle on cherche le plus court chemin
     * @return instance de Chemin (temps et suite de direction) représentant le plus cours chemin vers dest
     */
    public Chemin getPlusCourtChemin (Case source, Case dest) {
        return Dijkstra.getPlusCourtChemin(source, dest, this);
    }


    private int getNextDate(){
        if (Simulateur.getDateSimulation() == 0) return 1;
        return (int) Simulateur.getDateSimulation() + 1;
    }


    /**
     * Programme la suite d'évènements déplacement permettant au robot de suivre la suite de direction
     * @param descChemin Tableau de direction représentant le chemin à parcourir
     */
    public void goToDestination (ArrayList<Direction> descChemin) {
        int date = getNextDate();
        for (Direction dir : descChemin) {
            Deplacement deplacement = new Deplacement(date, this, dir);
            Simulateur.ajouteEvenement(deplacement);
        }
    }


    /**
     * Déclenche une intervention sur un feu
     */
    public void intervient(){
        int date = getNextDate();
        Simulateur.ajouteEvenement(new Intervention(date,this));
    }


    /**
     * Remplir réservoir si conditions remplies
     */
    public abstract void remplirReservoir();


    /**
     * @return Le chemin correspondant au sprite du robot à afficher dans le simulateur
     */
    public abstract String getSpritePath();


    /**
     * Fonction utile au calcul de plus court chemin
     * @param pos case sur laquelle on cherche la vitesse de ce robot
     * @return vitesse sur la case pos
     */
    public abstract double getSpeedOnCase(Case pos);


    /**
     * Déplace sur la case voisine dans la direction dir et update la vitesse du robot
     * @param dir direction dans laquelle se déplacer sur la case voisine
     */
    public abstract void moveNextCase (Direction dir);


    // getters et setters en dessous

    public boolean isInterventionEnCours() {return interventionEnCours;}
    public void setInterventionEnCours(boolean interventionEnCours) {this.interventionEnCours = interventionEnCours;}

    public int getVitesseDeversement() {
        return vitesseDeversement;
    }

    public Case getPosition() {
        return position;
    }

    public int getVitesseRemplissage() {
        return vitesseRemplissage;
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

    public int getTailleReservoir() {
        return tailleReservoir;
    }

}

