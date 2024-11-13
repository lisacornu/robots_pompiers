package Simulation;

import acteur.Robot;

public abstract class Evenement  {
    protected long date;
    protected double duration;
    protected Robot robot;
    protected boolean Done;

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public Evenement(long date, Robot robot) {
        this.date = date;
        this.robot = robot;
        this.Done = false;
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

    /**
     * S'occupe d'éxécuter l'évenment en question
     */
    public abstract void execute();

    protected void setDate(long date) {
        this.date = date;
    }
}
