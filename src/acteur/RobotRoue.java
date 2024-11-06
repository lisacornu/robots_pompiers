package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;
import static io.DonneeSimulation.getCarte;



public class RobotRoue extends RobotTerrestre {
    private static final int vitesseDefaut = 80; // km/h
    private static final int tailleReservoir = 5000;
    private static final int vitesseDeversement = 100/5;

    public RobotRoue(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut,Double.parseDouble(vitesse), 10 * 60,vitesseDeversement,tailleReservoir);
        this.typeRobot = TypeRobot.ROUES;
    }

    public void moveNextCase(Direction dir){
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if(newCase.getNatureTerrain() == NatureTerrain.HABITAT || newCase.getNatureTerrain() == NatureTerrain.TERRAIN_LIBRE){
            this.position = newCase;
        }
    }

    public void remplirReservoir () {
        Carte carte = getCarte();
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(this.position, dir)
                && carte.getVoisin(this.position, dir).getNatureTerrain() == NatureTerrain.EAU) {
                    super.reservoir = tailleReservoir;
            }
        }
    }
}
