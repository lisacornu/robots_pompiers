package acteur;

import environment.Case;

public abstract class RobotTerrestre extends Robot {

    // Robots sans vitesse max
    protected RobotTerrestre (Case pos, String vitesse, double vitesseDefaut) {
        super.position = pos;
        super.vitesseDeplacement = (vitesse == null ? vitesseDefaut : Double.parseDouble(vitesse));
    }

    // Robots avec vitesse max
    protected RobotTerrestre (Case pos, String vitesse, double vitesseDefaut, double vitesseMax,int tempsRemplissage) {
        super.position = pos;
        super.vitesseDeplacement = (
                vitesse == null
                        ? vitesseDefaut
                        : (Math.min(Double.parseDouble(vitesse), vitesseMax)) // vitesse lue dans le fichier ne peux pas être > à vitesse max
        );
        super.tempsRemplissage =  tempsRemplissage;
    }

}
