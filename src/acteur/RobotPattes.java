package acteur;

import environment.Case;

public class RobotPattes extends RobotTerrestre{
    private static final int vitesseDefaut = 30; // km/h

    public RobotPattes(Case position) {
        super(position, null, vitesseDefaut);
    }
}
