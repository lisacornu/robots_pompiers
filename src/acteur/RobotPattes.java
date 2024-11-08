package acteur;

import environment.Case;
import environment.Direction;
import environment.Carte;
import environment.NatureTerrain;

import static io.DonneeSimulation.getCarte;

public class RobotPattes extends RobotTerrestre{

    private static final double vitesseDefaut = 30; // km/h
    private static final int vitesseDeversement = 10;

    public RobotPattes(Case position) {
        super(position, vitesseDefaut, vitesseDeversement);
    }


    public void moveNextCase(Direction dir) {
        Carte carte = getCarte();
        Case newCase = carte.getVoisin(this.position,dir);
        if(newCase.getNatureTerrain() == NatureTerrain.EAU)
            return;

        this.setPosition(newCase);
        if (this.position.getNatureTerrain() == NatureTerrain.ROCHE)
            this.vitesseDeplacement = 10;
    }


    @Override
    public String getSpritePath() {
        return "images/patte.png";
    }
}
