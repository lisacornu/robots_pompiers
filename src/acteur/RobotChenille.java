package acteur;

import environment.Carte;
import environment.Case;
import environment.Direction;
import environment.NatureTerrain;
import io.DonneeSimulation;



public class RobotChenille extends RobotTerrestre {
    // d'après la spécification
    private static final double vitesseDefaut = 60;
    private static final double vitesseMax = 80;
    private static final int tailleReservoir = 2000;
    private static final int vitesseRemplissage = 5 * 60;
    private static final int vitesseDeversement = 100/8;

    // vitesse de base, utile pour getSpeedOnCase car l'attribut vitesseDeplacement peut être modifié selon le terrain
    private final double baseSpeed;

    // Constructeur
    public RobotChenille(Case position, String vitesse) {
        super (
                position,
                (vitesse == null ? vitesseDefaut : (Math.min(Double.parseDouble(vitesse), vitesseMax))),
                vitesseRemplissage,
                vitesseDeversement,
                tailleReservoir
        );
        this.baseSpeed = this.vitesseDeplacement;
    }


    public void moveNextCase(Direction dir) {
        Carte carte = DonneeSimulation.getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if( newCase.getNatureTerrain() == NatureTerrain.EAU || newCase.getNatureTerrain() == NatureTerrain.ROCHE)
            return;

        this.position = newCase;
        if(newCase.getNatureTerrain() == NatureTerrain.FORET && this.vitesseDeplacement == this.baseSpeed)
            this.vitesseDeplacement = this.baseSpeed/2;
        else
            this.vitesseDeplacement = this.baseSpeed;
    }


    @Override
    public double getSpeedOnCase(Case pos) {
        NatureTerrain natureTerrrainCase = pos.getNatureTerrain();
        if ( natureTerrrainCase == NatureTerrain.EAU || natureTerrrainCase == NatureTerrain.ROCHE)
            return 1/Double.MAX_VALUE;
        else if ( natureTerrrainCase == NatureTerrain.FORET)
            return this.baseSpeed / 2;
        else
            return this.baseSpeed;
    }

    @Override
    public String getSpritePath() {
        return "images/chenille.png";
    }

}
