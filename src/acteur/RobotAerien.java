package acteur;

import environment.Carte;
import environment.Case;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

abstract public class RobotAerien extends Robot{

    protected RobotAerien(Case pos, double speed, int vitesseRemplissage, int vitesseDeversement, int tailleReservoir) {
        super(pos, speed, vitesseRemplissage, vitesseDeversement, tailleReservoir);
    }

    @Override
    public final void RemplirReservoir() {
        Carte carte = getCarte();
        if (this.position.getNatureTerrain() == NatureTerrain.EAU)
            this.volActuelReservoir = Math.min(this.getVolActuelReservoir() + this.getVolActuelReservoir() * this.getVitesseRemplissage() , this.getTailleReservoir());
    }

}
