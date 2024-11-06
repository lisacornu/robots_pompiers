package Simulation;

import acteur.Robot;

public class RemplirReservoir extends Evenement{
    public RemplirReservoir(long date, Robot robot) {
        super(date, robot);
        this.duration = robot.getTempsRemplissage();
    }
    public void execute(){
        super.robot.remplirReservoir();
    }
}
