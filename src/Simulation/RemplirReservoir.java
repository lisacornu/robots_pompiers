package Simulation;

import acteur.Robot;

public class RemplirReservoir extends Evenement{
    public RemplirReservoir(long date, Robot robot) {
        super(date, robot);
        this.duration =  robot.getTailleReservoir() / robot.getVitesseRemplissage() ;
    }
    public void execute(){

        if((!Simulateur.getExecutingEvent().contains(this))){
            //Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
            //Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du roboot
            super.getRobot().setEvenement(this);
            return;
        }
        //On remplit le reservoir du Robot d'une unité de temps (x Litres d'eau en une seconde)
        super.getRobot().remplirReservoir();
        if(super.getRobot().getTailleReservoir() == super.getRobot().getVolActuelReservoir()){
            //Si le réservoir du robot est plein l'evenement est terminé
            super.setDone(true);
            super.getRobot().setSearchingForWater(false);
        }
        System.out.println("Reservoir : " + super.robot.getVolActuelReservoir());

    }
}
