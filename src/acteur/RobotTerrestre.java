package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

public abstract class RobotTerrestre extends Robot {

    protected RobotTerrestre (Case pos, double speed, int vitesseRemplissage, int vitesseDeversement,int tailleReservoir) {
        super(pos, speed, vitesseRemplissage, vitesseDeversement, tailleReservoir);
    }

    protected RobotTerrestre (Case pos, double speed, int vitesseDeversement) {
        super(pos, speed, vitesseDeversement);
    }

    @Override
    public final void RemplirReservoir() {
        Carte carte = getCarte();
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(this.position, dir)
                    && carte.getVoisin(this.position, dir).getNatureTerrain() == NatureTerrain.EAU) {
                this.volActuelReservoir = Math.min(this.getVolActuelReservoir() + this.getVitesseRemplissage() , this.getTailleReservoir());
            }
        }
    }
}
