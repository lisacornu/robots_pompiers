package io;

import environment.Carte;
import environment.Case;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.zip.DataFormatException;

public class Simulateur implements Simulable{

    private final GUISimulator gui;

    private final Carte init_carte;

    private final DonneeSimulation donneeSimulation;


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
    }

    @Override
    public void restart() {
        this.donneeSimulation.setCarte(init_carte);
        draw();
    }
}
