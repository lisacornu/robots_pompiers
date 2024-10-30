package acteur;

import environment.Case;

public class RobotDrone extends RobotAerien{
    private static final int vitesseDefaut = 100;
    private static final int vitesseMax = 150; // km/h

    public RobotDrone(Case position, String s) {
        super(position, s, vitesseDefaut, vitesseMax);
    }
}
