package acteur;

import environment.Case;

public class RobotRoue extends RobotTerrestre {
    private static final int vitesseDefaut = 80; // km/h

    public RobotRoue(Case position, String vitesse) {
        super(position, vitesse, vitesseDefaut);
    }
}
