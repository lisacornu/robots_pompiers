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
    }


    /**
     * Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
     * Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du robot
     */
    @Override
    public void execute() {
        if((!Simulateur.getExecutingEvent().contains(this))){
            //Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
            //Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du robot

            super.getRobot().setEvenement(this);
        }
        else{
            //Sinon, on déplace le robot.
            super.robot.moveNextCase(this.direction);
            super.setDone(true);

        }
    }
}
