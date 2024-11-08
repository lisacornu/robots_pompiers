package acteur;

import environment.Case;
import environment.NatureTerrain;
import environment.Direction;
import environment.Carte;
import io.DonneeSimulation;

import static io.DonneeSimulation.getCarte;

public class RobotDrone extends RobotAerien {

    private static final int vitesseDefaut = 100;
    private static final int vitesseMax = 150; // km/h
    private static final int tailleReservoir = 10000;
    private static final int vitesseRemplissage = 30 * 60;
    private static final int vitesseDeversement = 10000/30;


    public RobotDrone(Case position, String vitesse) {
        super (
                position,
                (vitesse == null ? vitesseDefaut : (Math.min(Double.parseDouble(vitesse), vitesseMax))),
                vitesseRemplissage,
                vitesseDeversement,
                tailleReservoir
        );
    }


    public void moveNextCase (Direction dir){
        Carte carte = getCarte();
        this.position = carte.getVoisin(this.position, dir);
    }

    @Override
    public String getSpritePath() {
        return "images/drone.png";
    }
}
