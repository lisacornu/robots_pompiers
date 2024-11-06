package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;    // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    protected int reservoir;                // a quel point le reservoir est rempli, sa taille se trouve dans les classes filles
    protected TypeRobot typeRobot;
    protected int tempsRemplissage;
    protected int vitesseDeversement;
    protected int vitesseRemplissage;
    private boolean interventionEnCours = false;
    protected int tailleReservoir;


    public boolean isInterventionEnCours() {
        return interventionEnCours;
    }

    public void setInterventionEnCours(boolean interventionEnCours) {
        this.interventionEnCours = interventionEnCours;
    }

    public int getVitesseDeversement() {
        return vitesseDeversement;
    }

    public void setVitesseDeversement(int vitesseDeversement) {
        this.vitesseDeversement = vitesseDeversement;
    }

    public TypeRobot getTypeRobot() {
        return typeRobot;
    }

    public void setTypeRobot(TypeRobot typeRobot) {
        this.typeRobot = typeRobot;
    }

    public Case getPosition() {
        return position;
    }

    public int getTempsRemplissage() {
        return tempsRemplissage;
    }

    public void setTempsRemplissage(int tempsRemplissage) {
        this.tempsRemplissage = tempsRemplissage;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public int getReservoir() {
        return reservoir;
    }

    public void setReservoir(int reservoir) {
        this.reservoir = reservoir;
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

    public void remplirReservoir (){
        Carte carte = getCarte();
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(this.position, dir)
                    && carte.getVoisin(this.position, dir).getNatureTerrain() == NatureTerrain.EAU) {
                this.reservoir = Math.min(this.getReservoir() + this.getReservoir() * this.getTempsRemplissage() , this.getTailleReservoir());
            }
        }

    }
}

