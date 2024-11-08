package acteur;

import environment.Case;
import environment.Direction;
import environment.Carte;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

public class RobotPattes extends RobotTerrestre{
    private static final int vitesseDefaut = 30; // km/h
    private static final int vitesseDeversement = 10;

    public RobotPattes(Case position) {

        super(position, null,vitesseDefaut,vitesseDefaut,0,vitesseDeversement,0);
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
        this.setPosition(newCase);
    }
}
