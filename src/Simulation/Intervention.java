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
        if(super.robot.isEvenementEnCours() & (!Simulateur.getExecutingEvent().contains(this))){
            super.getRobot().setEvenement(this);
            return;
        }

        Case currentCase = super.robot.getPosition();
        int reservoirRobot = this.robot.getVolActuelReservoir();
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
                super.getRobot().setEvenement(this);
                super.getRobot().setInterventionEnCours(true);
            }
        }
        super.robot.setVolActuelReservoir(Math.max(reservoirRobot - super.robot.getVitesseDeversement(),0));
        if(super.robot.getVolActuelReservoir() == 0){
            super.robot.setInterventionEnCours(false);
            super.setDone(true);
        }
        currentIncendie.setIntensite(max(currentIncendie.getIntensite() - super.robot.getVitesseDeversement(),0));

        if(currentIncendie.getIntensite() == 0) {
            currentCase.setOnFire(false);
            super.setDone(true);
        }
        System.out.println("Intensite : " + currentIncendie.getIntensite() + " Reservoir : " + robot.getVolActuelReservoir());


    }

}
