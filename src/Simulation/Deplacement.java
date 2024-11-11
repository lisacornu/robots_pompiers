package Simulation;

import acteur.Robot;
import environment.Direction;
import io.DonneeSimulation;


public class Deplacement extends Evenement {
    private final Direction direction;

    public Deplacement(long date, Robot robot, Direction direction) {
        super(date, robot);
        this.direction = direction;
        double viteseMs =  super.robot.getVitesseDeplacement()/3.6;
        int tailleCase = DonneeSimulation.getCarte().getTailleCases();
        super.duration = (int) (tailleCase/viteseMs);
    }

    @Override
    public void execute() {
        System.out.println(super.getRobot().getPosition().getX() + " " + super.getRobot().getPosition().getY());
        if((!Simulateur.getExecutingEvent().contains(this))){
            //Si le robot effectue déjà une action et que la liste des Evenements globaux ne contient pas cet evenement
            //Alors on place l'evenement soit dans la liste des evenements en cours ou dans la liste d'attente du robot

            super.getRobot().setEvenement(this);
        }
        else if (Simulateur.getExecutingEvent().contains(this) && super.date + super.duration >= Simulateur.getDateSimulation() ){
            //Si cet evenement est dans la liste des evenements en cour ET que la date de fin de déplacement est plus grande que celle de la Simulation
            //Alors on se déplace.

            super.robot.moveNextCase(this.direction);
            System.out.println("X : " +super.robot.getPosition().getX() + " Y : " + super.robot.getPosition().getY());
            super.setDone(true);

        }
    }
}
