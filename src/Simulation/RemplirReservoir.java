package Simulation;

import acteur.Robot;

public class RemplirReservoir extends Evenement{
    public RemplirReservoir(long date, Robot robot) {
        super(date, robot);
        this.duration =  robot.getTailleReservoir() / robot.getVitesseRemplissage() ;
    }
    public void execute(){
        if(super.robot.isEvenementEnCours() & (!Simulateur.getExecutingEvent().contains(this))){
            super.getRobot().setEvenement(this);
            return;
        }
        super.getRobot().remplirReservoir();
        if(super.getRobot().getTailleReservoir() == super.getRobot().getVolActuelReservoir()){
            super.setDone(true);
        }
        System.out.println("Reservoir : " + super.robot.getVolActuelReservoir());

    }
}
