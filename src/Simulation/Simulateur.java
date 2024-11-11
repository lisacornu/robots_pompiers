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
import java.util.*;
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
            gui.addGraphicalElement(new ImageElement(x, y, robot.getSpritePath(), width_case, height_case, null));
        }

    }

    private void draw(){
        gui.reset();
        draw_map();
        draw_robots();
    }

    @Override
    public void next() {
        if(dateSimulation == 0){
            System.out.println("START");
        }
        dateSimulation++;
        HashMap<Long, ArrayList<Evenement>> evenements = this.getEvenements();
        ArrayList<Evenement> eventsToDate = evenements.get(dateSimulation);
        ArrayList<Evenement> eventToAdd = new ArrayList<>();
        if(eventsToDate != null) {
            for (Evenement evenement : eventsToDate) {
                evenement.execute();
            }
        }
        for (Iterator<Evenement> it = executingEvent.iterator(); it.hasNext();) {
            Evenement evenement = it.next();
            evenement.execute();

            if(evenement.isDone()){
                it.remove();
                if(evenement.getRobot().getEvenementEnAttente().isEmpty()) {
                    evenement.getRobot().setEvenementEnCours(false);
                }
                else{
                    Evenement evenementAjouter =  evenement.getRobot().getEvenementEnAttente().getFirst();
                    evenementAjouter.setDate(dateSimulation);
                    eventToAdd.add(evenementAjouter);
                    evenement.getRobot().getEvenementEnAttente().remove(evenementAjouter);
                }
            }
        }
        executingEvent.addAll(eventToAdd);
        draw();
    }

    @Override
    public void restart() {
        ArrayList<Robot> robotsCopy = (ArrayList<Robot>) DonneeSimulation.getRobots().clone();

        try {
            DonneeSimulation.getRobots().clear();
            DonneeSimulation.getIncendies().clear();
            LecteurDonnees.resetDonneeSimulation(this.donneeSimulation, this.filename);
        } catch (DataFormatException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Simulateur.getExecutingEvent().clear();
        Collection<ArrayList<Evenement>> evenements = Simulateur.Evenements.values();
        for(ArrayList<Evenement> ListEvenement:evenements){
            for(Evenement evenement:ListEvenement){
                int robotToSet =  robotsCopy.indexOf(evenement.getRobot());
                evenement.setRobot(DonneeSimulation.getRobots().get(robotToSet));
                evenement.setDone(false);;
            }
        }
        dateSimulation = 0;
        draw();
    }


}
