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

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.DataFormatException;

public class Simulateur implements Simulable{

    private final GUISimulator gui;
    private final Carte init_carte;
    private final DonneeSimulation donneeSimulation;
    public long dateSimulation;
    private HashMap<Long,ArrayList<Evenement>> Evenements = new HashMap<>(); //On utilise une HashMap avec comme clés les dates d'évenements et comme valeur une liste d'évenements
                                                                             //Comme cela on a directement tous les évenements associés à la date.
    public void ajouteEvenement(Evenement e){
        Evenements.computeIfAbsent(e.date, k -> new ArrayList<Evenement>());
        Evenements.get(e.date).add(e);
    }

    public HashMap<Long, ArrayList<Evenement>> getEvenements() {
        return Evenements;
    }



    private void incrementeDate(){
        this.dateSimulation++;
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


    public Simulateur(GUISimulator gui,String donnees) throws DataFormatException, FileNotFoundException {
        this.gui = gui;
        gui.setSimulable(this);
        this.donneeSimulation = initDonneeSimulation(donnees);
        this.init_carte = DonneeSimulation.getCarte();
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
                        ImageObs obs = new ImageObs();
                        gui.addGraphicalElement(new ImageElement(x,y,"feu.png",width_case,height_case,null));
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
            x = robot.getPosition().getX() * width_case + width_case / 2;
            y = robot.getPosition().getY() * height_case + height_case / 2;
            switch (robot.getTypeRobot()){
                case ROUES :
                    gui.addGraphicalElement(new ImageElement(x, y, "roue.png", width_case, height_case, null));
                    break;
                case CHENILLE:
                    gui.addGraphicalElement(new ImageElement(x, y, "chenille.png", width_case, height_case, null));
                    break;
                case DRONE:
                    gui.addGraphicalElement(new ImageElement(x, y, "drone.png", width_case, height_case, null));
                    break;
                case PATTES:
                    gui.addGraphicalElement(new ImageElement(x, y, "patte.png", width_case, height_case, null));
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
        this.dateSimulation++;
        HashMap<Long, ArrayList<Evenement>> evenements = this.getEvenements();
        ArrayList<Evenement> eventsToDate = evenements.get(dateSimulation);
        for (Evenement e : eventsToDate) {
            e.execute();
        }
        draw();
    }

    @Override
    public void restart() {
        this.donneeSimulation.setCarte(init_carte);
        this.dateSimulation = 0;
        draw();
    }


}
