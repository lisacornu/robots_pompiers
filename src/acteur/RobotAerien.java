package acteur;

import environment.Carte;
import environment.Case;
import environment.NatureTerrain;
import io.DonneeSimulation;

abstract public class RobotAerien extends Robot{

    protected RobotAerien(Case pos, double speed, int vitesseRemplissage, int vitesseDeversement, int tailleReservoir) {
        super(pos, speed, vitesseRemplissage, vitesseDeversement, tailleReservoir);
    }

    public boolean isFlying(){
        return true;
    }

    @Override
    public final void remplirReservoir() {
        Carte carte = DonneeSimulation.getCarte();
        if (this.position.getNatureTerrain() == NatureTerrain.EAU) {
            this.volActuelReservoir = Math.min(this.getVolActuelReservoir() + this.getVitesseRemplissage(), this.getTailleReservoir());
        }
    }

}
