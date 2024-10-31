package acteur;

import environment.Case;
import environment.Direction;
import environment.Carte;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

public class RobotPattes extends RobotTerrestre{
    private static final int vitesseDefaut = 30; // km/h

    public RobotPattes(Case position) {

        super(position, null, vitesseDefaut);
        this.typeRobot = TypeRobot.PATTES;
    }

    public void remplirReservoir () {
    }

    public void moveNextCase(Direction dir) {
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if(newCase.getNatureTerrain() == NatureTerrain.EAU){
            return;
        }
        this.position = newCase;
    }
}
