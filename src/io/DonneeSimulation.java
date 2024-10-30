package io;

import acteur.Robot;
import environment.Carte;
import environment.Incendie;

import java.util.ArrayList;

public class DonneeSimulation {
    private Carte carte;
    private ArrayList<Incendie> incendies;
    private ArrayList<Robot> robots;


    public Carte getCarte() {
        return carte;
    }

    public ArrayList<Incendie> getIncendies() {
        return incendies;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setIncendies(ArrayList<Incendie> incendies) {
        this.incendies = incendies;
    }

    public void setRobots(ArrayList<Robot> robots) {
        this.robots = robots;
    }
}
