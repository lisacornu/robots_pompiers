package io;

import acteur.Robot;
import environment.Carte;
import environment.Incendie;

import java.util.ArrayList;
import brainrobot.basicBrain;



public class DonneeSimulation {
    private static Carte carte;
    private static ArrayList<Incendie> incendies;
    private static  ArrayList<Robot> robots;
    private static basicBrain brain;

    public static basicBrain getBrain() {
        return brain;
    }

    public static void InitBrain() {
        brain = new basicBrain();
    }

    public static Carte getCarte() {
        return carte;
    }

    public static ArrayList<Incendie> getIncendies() {
        return incendies;
    }

    public static ArrayList<Robot> getRobots() {return robots;}

    public void setCarte(Carte carte) {
        DonneeSimulation.carte = carte;
    }

    public void setIncendies(ArrayList<Incendie> incendies) {
        DonneeSimulation.incendies = incendies;
    }

    public void setRobots(ArrayList<Robot> robots) {
        DonneeSimulation.robots = robots;
    }
}
