package acteur;

import environment.Case;
import environment.Direction;

public abstract class Robot {
    private Case position;
    private double vitesseDeplacement;  // faire évoluer en hashmap ? Pour prendre en compte le changement de vitesse en fonction du chemin
    private int tailleReservoir;

    public Case getPosition() {
        return position;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public int getTailleReservoir() {
        return tailleReservoir;
    }

    abstract void setPosition (Case case_obj);
    abstract void moveNextCase (Direction dir);
    abstract void remplirReservoir ();
}

