package acteur;

import environment.Case;

public class RobotChenille extends RobotTerrestre {
    private static final int vitesseDefaut = 60; // km/h
    private static final int vitesseMax = 80;

    public RobotChenille(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut, vitesseMax);
    }
}
