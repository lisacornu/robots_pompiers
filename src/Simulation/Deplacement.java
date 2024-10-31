package Simulation;

import acteur.Robot;
import environment.Direction;

public class Deplacement extends Evenement {
    private final Direction direction;

    public Deplacement(long date, Robot robot, Direction direction) {
        super(date, robot);
        this.direction = direction;
    }

    @Override
    public void execute() {
        super.robot.moveNextCase(this.direction);
    }
}
