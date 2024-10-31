package Simulation;

import environment.Carte;
import environment.Case;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import io.DonneeSimulation;
import io.LecteurDonnees;

import java.awt.*;
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



    private void draw() {
        gui.reset();

        int height_case = gui.getHeight()/DonneeSimulation.getCarte().getNbCol();
        int width_case  = gui.getWidth()/DonneeSimulation.getCarte().getNblignes();
        int y = height_case/2;
        int x = width_case/2;
        Carte carte = DonneeSimulation.getCarte();
        Case[][] matriceCase = carte.getMatriceCase();
        for(int i = 0; i < carte.getNblignes(); i++){
            for (int j = 0; j < carte.getNbCol(); j++){
                switch(matriceCase[i][j].getNatureTerrain()){
                    case EAU:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.CYAN,width_case,height_case));
                        break;
                    case ROCHE:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.RED,width_case,height_case));
                        break;
                    case FORET:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.GREEN,width_case,height_case));
                        break;
                    case TERRAIN_LIBRE:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.WHITE,width_case,height_case));
                        break;
                    case HABITAT:
                        gui.addGraphicalElement(new Rectangle(x,y,Color.BLACK,Color.ORANGE,width_case,height_case));
                        break;
                }
                x+= width_case;
            }
            y += height_case;
            x = width_case/2;
        }
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
