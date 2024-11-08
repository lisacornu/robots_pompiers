import Simulation.*;
import acteur.Robot;
import environment.Direction;
import gui.GUISimulator;
import io.DonneeSimulation;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class TestPartOne {


    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        try {
            GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
            Simulateur simulateur = new Simulateur(gui, args[0]);
            ArrayList<Robot> robots = DonneeSimulation.getRobots();
            Robot robot = robots.get(1);
            Simulateur.ajouteEvenement(new Deplacement(1, robot, Direction.NORD ));
            Simulateur.ajouteEvenement(new Intervention(2, robot));
            Simulateur.ajouteEvenement(new Deplacement(3, robot, Direction.OUEST));
            Simulateur.ajouteEvenement(new Deplacement(4, robot, Direction.OUEST ));
            Simulateur.ajouteEvenement(new RemplirReservoir(5, robot));
            Simulateur.ajouteEvenement(new Deplacement(6, robot, Direction.EST ));
            Simulateur.ajouteEvenement(new Deplacement(7, robot, Direction.EST ));
            Simulateur.ajouteEvenement(new Intervention(8, robot));


        }
        catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
