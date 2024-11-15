package Simulation;

import acteur.Robot;
import environment.Carte;
import environment.Direction;
import io.DonneeSimulation;

import static pathFinding.Dijkstra.getTemps;


public class Deplacement extends Evenement {
    private final Direction direction;

    public Deplacement(long date, Robot robot, Direction direction) {
        super(date, robot);
        this.direction = direction;
        double viteseMs =  super.robot.getVitesseDeplacement()/3.6;
        int tailleCase = DonneeSimulation.getCarte().getTailleCases();
    }


    /**
     * Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
     * Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du robot
     */
    @Override
    public void execute() {
        if((!Simulateur.getExecutingEvent().contains(this))){
            super.getRobot().setEvenement(this);
        }
        else{
            super.robot.moveNextCase(this.direction);
            super.setDone(true);

        }
    }
}
