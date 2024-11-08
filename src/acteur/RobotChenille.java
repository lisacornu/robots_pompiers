package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;
import io.DonneeSimulation;



public class RobotChenille extends RobotTerrestre {
    private static final double vitesseDefaut = 60; // km/h
    private static final double vitesseMax = 80;
    private static final int tailleReservoir = 2000;
    private static final int vitesseRemplissage = 5 * 60;
    private static final int vitesseDeversement = 100/8;

    public RobotChenille(Case position, String vitesse) {
        super (
                position,
                (vitesse == null ? vitesseDefaut : (Math.min(Double.parseDouble(vitesse), vitesseMax))),
                vitesseRemplissage,
                vitesseDeversement,
                tailleReservoir
        );
    }

    public void moveNextCase(Direction dir) {
        Carte carte = DonneeSimulation.getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if( newCase.getNatureTerrain() == NatureTerrain.EAU || newCase.getNatureTerrain() == NatureTerrain.ROCHE){
            return;
        }
        else if(newCase.getNatureTerrain() == NatureTerrain.FORET){

        }
        this.position = newCase;
    }

    @Override
    public String getSpritePath() {
        return "images/chenille.png";
    }

}
