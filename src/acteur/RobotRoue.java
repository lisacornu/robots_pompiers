package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;
import static io.DonneeSimulation.getCarte;



public class RobotRoue extends RobotTerrestre {
    // d'après la spécification
    private static final double vitesseDefaut = 80; // km/h
    private static final int tailleReservoir = 5000;
    private static final int vitesseRemplissage = 10 * 60;
    private static final int vitesseDeversement = 100 / 5;

    // Constructeur
    public RobotRoue(Case position, String vitesse) {
        super (
                position,
                (vitesse == null ? vitesseDefaut : Double.parseDouble(vitesse)),
                vitesseRemplissage,
                vitesseDeversement,
                tailleReservoir
        );
    }


    public void moveNextCase(Direction dir){
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if(newCase.getNatureTerrain() == NatureTerrain.HABITAT || newCase.getNatureTerrain() == NatureTerrain.TERRAIN_LIBRE){
            this.position = newCase;
        }
    }


    @Override
    public String getSpritePath() {
        return "images/roue.png";
    }


    @Override
    public double getSpeedOnCase(Case pos) {
        NatureTerrain natureTerrrainCase = pos.getNatureTerrain();
        if (natureTerrrainCase == NatureTerrain.HABITAT || natureTerrrainCase == NatureTerrain.TERRAIN_LIBRE)
            return this.vitesseDeplacement;
        else
            return 1/Double.MAX_VALUE;
    }

}
