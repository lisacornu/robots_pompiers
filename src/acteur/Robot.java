package acteur;

import environment.Case;
import environment.Direction;

public abstract class Robot {
    protected Case position;
    protected double vitesseDeplacement;    // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    protected int reservoir;                // a quel point le reservoir est rempli, sa taille se trouve dans les classes filles


    public Case getPosition() {
        return position;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public int getReservoir() {
        return reservoir;
    }

    void setPosition (Case case_obj){
        this.position = case_obj;
    }
    abstract void moveNextCase (Direction dir);
    abstract void remplirReservoir ();
}

