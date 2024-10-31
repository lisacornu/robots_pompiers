package Simulation;

import acteur.Robot;
import environment.Case;
import environment.Incendie;
import io.DonneeSimulation;

import java.util.ArrayList;

import static java.lang.Math.max;

public class Intervention extends Evenement {

    public Intervention(long date, Robot robot) {
        super(date, robot);
    }

    @Override
    public void execute() {

        Case currentCase = super.robot.getPosition();

        if(currentCase.isOnFire()) {
            int reservoirRobot = this.robot.getReservoir();
            int quantityWater;
            ArrayList<Incendie> incendies = DonneeSimulation.getIncendies();
            Incendie currentIncendie = null;
            for (Incendie incendie : incendies) {
                if (incendie.getPosition() == currentCase) {
                    currentIncendie = incendie;
                    break;
                }
            }
            if(currentIncendie == null) {
                return;
            }

            switch (super.robot.getTypeRobot()){
                case ROUES:
                    quantityWater = (Math.min(reservoirRobot, 100));
                    reservoirRobot = reservoirRobot - quantityWater;
                    this.duration = 5;
                    break;
                case DRONE:
                    quantityWater = 10000;
                    reservoirRobot = 0;
                    this.duration = 30;
                    break;
                case CHENILLE:
                    quantityWater = (Math.min(reservoirRobot, 100));
                    reservoirRobot = max(reservoirRobot - 100,0);
                    this.duration = 8;
                    break;
                case PATTES:
                    quantityWater = 10;
                    this.duration = 1; //Réservoir supposé infini;
                    break;
                default:
                    quantityWater = 0;
                    break;
            }
            this.robot.setReservoir(reservoirRobot);
            currentIncendie.setIntensite(max(currentIncendie.getIntensite() - quantityWater,0));

            if(currentIncendie.getIntensite() == 0) {
                currentCase.setOnFire(false);
            }
        }
    }

}
