package acteur;

import environment.Case;
import environment.Direction;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;    // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    protected int reservoir;                // a quel point le reservoir est rempli, sa taille se trouve dans les classes filles
    protected TypeRobot typeRobot;
    protected int tempsRemplissage;



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
    public abstract void remplirReservoir ();
}

