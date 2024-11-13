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
        if((!Simulateur.getExecutingEvent().contains(this))){
            //Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
            //Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du roboot
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
        //On obtient l'incendie sur lequel le robot intervient
        if (currentIncendie == null) {
            return;
        }

        if(!super.robot.isInterventionEnCours()) {
            //Si c'est le premier appel de cet evenement alors on initialise la durée d'evenement et on met à vrai le booleen interventionEnCours
            if (currentCase.isOnFire()) {

                this.duration = Math.min((currentIncendie.getIntensite() / super.robot.getVitesseDeversement()), reservoirRobot / super.robot.getVitesseDeversement());
                super.getRobot().setEvenement(this);
                super.getRobot().setInterventionEnCours(true);
            }
        }
        //On diminue le reservoir du robot
        super.robot.setVolActuelReservoir(Math.max(reservoirRobot - super.robot.getVitesseDeversement(),0));

        if(super.robot.getVolActuelReservoir() == 0){
            //Si le robot n'a plus d'eau, l'evenement est fini
            super.robot.setInterventionEnCours(false);
            super.setDone(true);
        }
        //On diminue l'intensité de l'Incendie
        currentIncendie.setIntensite(max(currentIncendie.getIntensite() - super.robot.getVitesseDeversement(),0));

        if(currentIncendie.getIntensite() == 0) {
            //Si il n'y a plus d'incendie, on termine l'evenement.
            currentCase.setOnFire(false);
            super.setDone(true);
        }
        System.out.println("Intensite : " + currentIncendie.getIntensite() + " Reservoir : " + robot.getVolActuelReservoir());


    }

}
