package acteur;

import environment.Case;
import environment.NatureTerrain;
import environment.Direction;
import environment.Carte;
import io.DonneeSimulation;

import static io.DonneeSimulation.getCarte;

public class RobotDrone extends RobotAerien{
    private static final int vitesseDefaut = 100;
    private static final int vitesseMax = 150; // km/h
    private static final int tailleReservoir = 10000;

    public RobotDrone(Case position, String s) {
        super(position, s, vitesseDefaut, vitesseMax);
    }

    public void remplirReservoir () {
        if (this.position.getNatureTerrain() == NatureTerrain.EAU)
            super.reservoir = tailleReservoir;
    }
    public void moveNextCase (Direction dir){
        Carte carte = getCarte();
        this.position = carte.getVoisin(this.position,dir);
    }

}
