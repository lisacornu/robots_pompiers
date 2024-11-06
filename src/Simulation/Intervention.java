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


    private void init_execute(){

    }

    @Override
    public void execute() {

        Case currentCase = super.robot.getPosition();

        //faire un  bool/Case pour intervention en cours et savoir sur quelle case intervient le robot
        //Pour savoir si on refait l'init ou pas.
        //Faire en sorte

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
                    this.duration = (currentIncendie.getIntensite())/(100/5);
                    quantityWater = (Math.min(reservoirRobot, 100));
                    reservoirRobot = reservoirRobot - quantityWater;
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
