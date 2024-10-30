package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;

public class RobotRoue extends RobotTerrestre {
    private static final int vitesseDefaut = 80; // km/h
    private static final int tailleReservoir = 5000;

    public RobotRoue(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut);
    }

    public void remplirReservoir (Carte carte) {
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(this.position, dir)
                && carte.getVoisin(this.position, dir).getNatureTerrain() == NatureTerrain.EAU) {
                    super.reservoir = tailleReservoir;
            }
        }
    }
}
