package acteur;

import environment.Case;
import environment.Direction;
import environment.Carte;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;


public class RobotRoue extends RobotTerrestre {
    private static final int vitesseDefaut = 80; // km/h

    public RobotRoue(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut);
    }

    public void moveNextCase(Direction dir){
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if(newCase.getNatureTerrain() == NatureTerrain.HABITAT || newCase.getNatureTerrain() == NatureTerrain.TERRAIN_LIBRE){
            this.position = newCase;
        }
    }
}
