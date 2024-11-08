package Simulation;

import acteur.Robot;

public abstract class Evenement  {
    protected long date;
    protected int duration;
    protected Robot robot;


    public Evenement(long date, Robot robot) {
        this.date = date;
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    public long getDate() {
        return date;
    }


    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public abstract void execute();

    protected void setDate(long date) {
        this.date = date;
    }
}
