package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;


public class RobotChenille extends RobotTerrestre {
    private static final int vitesseDefaut = 60; // km/h
    private static final int vitesseMax = 80;
    private static final int tailleReservoir = 2000;
    private static final int vitesseDeversement = 100/8;
    private static final int vitesseRemplissage = 5 * 60;

    public RobotChenille(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut, vitesseMax, vitesseRemplissage,vitesseDeversement,tailleReservoir);
        this.typeRobot = TypeRobot.CHENILLE;
    }



    public void moveNextCase(Direction dir) {
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if( newCase.getNatureTerrain() == NatureTerrain.EAU || newCase.getNatureTerrain() == NatureTerrain.ROCHE){
            return;
        }
        else if(newCase.getNatureTerrain() == NatureTerrain.FORET){

        }
        this.position = newCase;
    }

}
