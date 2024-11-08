package Simulation;

import acteur.Robot;
import environment.Carte;
import environment.Case;
import environment.Incendie;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import io.DonneeSimulation;
import io.LecteurDonnees;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.DataFormatException;




public class Simulateur implements Simulable{

    private final GUISimulator gui;
    private final String filename;
    private DonneeSimulation donneeSimulation;
    private static ArrayList<Evenement> executingEvent;

    private Image[] textureTerrain, textureRobot;
    private Image textureIncendie;

    private static long dateSimulation;
    private static final HashMap<Long,ArrayList<Evenement>> Evenements = new HashMap<>(); //On utilise une HashMap avec comme clés les dates d'évenements et comme valeur une liste d'évenements

                                                                             //Comme cela on a directement tous les évenements associés à la date.
    public static void ajouteEvenement(Evenement e){
        Evenements.computeIfAbsent(e.date, k -> new ArrayList<Evenement>());
        Evenements.get(e.date).add(e);
    }

    public static ArrayList<Evenement> getExecutingEvent() {
        return executingEvent;
    }

    public static void setExecutingEvent(ArrayList<Evenement> executingEvent) {
        Simulateur.executingEvent = executingEvent;
    }

    public static void supprimeEvenement(Evenement e){
        Evenements.get(e.date).remove(e);
    }

    public static long getDateSimulation() {
        return dateSimulation;
    }

    public HashMap<Long, ArrayList<Evenement>> getEvenements() {
        return Evenements;
    }
    private void incrementeDate(){
        dateSimulation++;
    }
    private boolean simulationTerminee(){
        Set<Long> dates = Evenements.keySet();
        for(Long date:dates){
            if (date > dateSimulation){
                return false;
            }
        }
        return true;
    }

    private DonneeSimulation initDonneeSimulation(String donnees) throws DataFormatException, FileNotFoundException {
        return LecteurDonnees.lire(donnees);
    }


    public Simulateur(GUISimulator gui, String donnees) throws DataFormatException, FileNotFoundException {
        this.gui = gui;
        this.filename = donnees;
        executingEvent = new ArrayList<Evenement>();
        gui.setSimulable(this);
        this.donneeSimulation = initDonneeSimulation(donnees);
        draw();
    }


    private void draw_map() {

        int height_case = gui.getHeight()/DonneeSimulation.getCarte().getNbCol();
        int width_case  = gui.getWidth()/DonneeSimulation.getCarte().getNblignes();
        int y = height_case/2;
        int x = width_case/2;
        Carte carte = DonneeSimulation.getCarte();
        Case[][] matriceCase = carte.getMatriceCase();
        ArrayList<Incendie> incendies = DonneeSimulation.getIncendies();
        for(int i = 0; i < carte.getNblignes(); i++){
            for (int j = 0; j < carte.getNbCol(); j++){
                switch(matriceCase[i][j].getNatureTerrain()){
                    case EAU:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.CYAN,Color.CYAN,width_case,height_case));
                        break;
                    case ROCHE:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.RED,Color.RED,width_case,height_case));
                        break;
                    case FORET:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.GREEN,Color.GREEN,width_case,height_case));
                        break;
                    case TERRAIN_LIBRE:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.WHITE,Color.WHITE,width_case,height_case));
                        break;
                    case HABITAT:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.ORANGE,Color.ORANGE,width_case,height_case));
                        break;
                }
                for(Incendie incendie:incendies){
                    if(incendie.getPosition() == matriceCase[i][j]){
                        gui.addGraphicalElement(new ImageElement(x - width_case/2 ,y - height_case/2,"images/feu.png",width_case,height_case, null));
                    }
                }
                x+= width_case;
            }
            y += height_case;
            x = width_case/2;
        }
    }


    private void draw_robots(){
        ArrayList<Robot> robots = DonneeSimulation.getRobots();
        int x;
        int y;
        int height_case = gui.getHeight()/DonneeSimulation.getCarte().getNbCol();
        int width_case  = gui.getWidth()/DonneeSimulation.getCarte().getNblignes();
        for(Robot robot:robots) {
            x = robot.getPosition().getX() * width_case;
            y = robot.getPosition().getY() * height_case;
            switch (robot.getTypeRobot()){
                case ROUES :
                    gui.addGraphicalElement(new ImageElement(x, y, "images/roue.png", width_case, height_case, null));
                    break;
                case CHENILLE:
                    gui.addGraphicalElement(new ImageElement(x, y, "images/chenille.png", width_case, height_case, null));
                    break;
                case DRONE:
                    gui.addGraphicalElement(new ImageElement(x, y, "images/drone.png", width_case, height_case, null));
                    break;
                case PATTES:
                    gui.addGraphicalElement(new ImageElement(x, y, "images/patte.png", width_case, height_case, null));
                    break;
            }
        }

    }

    private void draw(){
        gui.reset();
        draw_map();
        draw_robots();
    }

    @Override
    public void next() {
        dateSimulation++;
        HashMap<Long, ArrayList<Evenement>> evenements = this.getEvenements();
        ArrayList<Evenement> eventsToDate = evenements.get(dateSimulation);
        if(eventsToDate != null) {
            executingEvent.addAll(eventsToDate);
            for (Evenement evenement : executingEvent) {
                evenement.execute();
            }
            draw();
        }
    }

    @Override
    public void restart() {
        try {
            this.donneeSimulation = initDonneeSimulation(this.filename);
        } catch (DataFormatException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        dateSimulation = 0;
        draw();
    }


}
