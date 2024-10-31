package Simulation;

import acteur.Robot;

public class RemplirReservoir extends Evenement{
    public RemplirReservoir(long date, Robot robot) {
        super(date, robot);
    }
    public void execute(){
        super.robot.remplirReservoir();
    }
}
