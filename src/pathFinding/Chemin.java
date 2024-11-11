package pathFinding;

import environment.Direction;

import java.util.ArrayList;

public class Chemin {

    private double temps = 0;
    private ArrayList<Direction> descChemin = new ArrayList<>();

    public double getTemps() {
        return temps;
    }

    public void updateTemps(double temps) {
        this.temps += temps;
    }

    public ArrayList<Direction> getDescChemin() {
        return descChemin;
    }

    public void setDescChemin(ArrayList<Direction> descChemin) {
        this.descChemin = descChemin;
    }
}
