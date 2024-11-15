package Simulation;

import acteur.Robot;

public abstract class Evenement  {
    protected long date;
    protected double duration;
    protected Robot robot;
    protected boolean Done;


    public Evenement(long date, Robot robot) {
        this.date = date;
        this.robot = robot;
        this.Done = false;
    }


    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public Robot getRobot() {
        return robot;
    }

    public long getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    /**
     * S'occupe d'éxécuter l'événement en question
     */
    public abstract void execute();

    protected void setDate(long date) {
        this.date = date;
    }
}
