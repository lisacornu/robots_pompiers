package acteur;

import environment.Case;
import environment.NatureTerrain;

abstract public class RobotAerien extends Robot{

    protected RobotAerien (Case pos, String vitesse, double vitesseDefaut, double vitesseMax) {
        super.position = pos;
        super.vitesseDeplacement = (
                vitesse == null
                        ? vitesseDefaut
                        : (Math.min(Double.parseDouble(vitesse), vitesseMax)) // vitesse lue dans le fichier ne peux pas être > à vitesse max
        );
    }

}
