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
        if(super.date + super.duration < Simulateur.getDateSimulation() & (!Simulateur.getExecutingEvent().contains(this))){
            Simulateur.getExecutingEvent().add(this);
        }
        else {
            super.robot.moveNextCase(this.direction);
            Simulateur.getExecutingEvent().remove(this);
        }
    }
}
