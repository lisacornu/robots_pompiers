package acteur;


import environment.Case;
import environment.Direction;
import environment.Carte;
import io.DonneeSimulation;

import static io.DonneeSimulation.getCarte;

public class RobotDrone extends RobotAerien{
    private static final int vitesseDefaut = 100;
    private static final int vitesseMax = 150; // km/h

    public RobotDrone(Case position, String s) {
        super(position, s, vitesseDefaut, vitesseMax);
    }
    public void moveNextCase (Direction dir){
        Carte carte = getCarte();
        this.position = carte.getVoisin(this.position,dir);
    }
    void remplirReservoir (){}
}
