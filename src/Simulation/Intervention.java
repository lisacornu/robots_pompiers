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
        int reservoirRobot = this.robot.getReservoir();
        ArrayList<Incendie> incendies = DonneeSimulation.getIncendies();
        Incendie currentIncendie = null;
        for (Incendie incendie : incendies) {
            if (incendie.getPosition() == currentCase) {
                currentIncendie = incendie;
                break;
            }
        }
        if (currentIncendie == null) {
            return;
        }


        //faire un  bool/Case pour intervention en cours et savoir sur quelle case intervient le robot
        //Pour savoir si on refait l'init ou pas.
        //Faire en sorte



        if(!super.robot.isInterventionEnCours()) {
            if (currentCase.isOnFire()) {

                this.duration = Math.min((currentIncendie.getIntensite() / super.robot.getVitesseDeversement()), reservoirRobot / super.robot.getVitesseDeversement());
                Simulateur.getExecutingEvent().add(this);
                super.robot.setInterventionEnCours(true);
            }
        }
        super.robot.setReservoir(Math.max(reservoirRobot - super.robot.getVitesseDeversement(),0));
        if(super.robot.getReservoir() == 0){
            super.robot.setInterventionEnCours(false);
            Simulateur.getExecutingEvent().remove(this);
        }
        currentIncendie.setIntensite(max(currentIncendie.getIntensite() - super.robot.getVitesseDeversement(),0));

        if(currentIncendie.getIntensite() == 0) {
            currentCase.setOnFire(false);
            Simulateur.getExecutingEvent().remove(this);
        }


    }

}
