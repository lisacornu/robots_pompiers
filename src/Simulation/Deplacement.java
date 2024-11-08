package Simulation;

import acteur.Robot;
import environment.Direction;
import io.DonneeSimulation;
import Simulation.Simulateur;
import Simulation.Evenement;



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
            super.getRobot().setEvenement(this);
        }
        else if (Simulateur.getExecutingEvent().contains(this) && super.date + super.duration >= Simulateur.getDateSimulation() ){
            super.robot.moveNextCase(this.direction);
            System.out.println("X : " +super.robot.getPosition().getX() + " Y : " + super.robot.getPosition().getY());
            super.setDone(true);

        }
    }
}
