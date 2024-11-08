import Simulation.Deplacement;
import Simulation.Evenement;
import acteur.Robot;
import environment.Direction;
import gui.GUISimulator;
import Simulation.Simulateur;
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
            System.out.println(robots.getFirst().getPosition().getX());
            System.out.println(robots.getFirst().getPosition().getY());
            System.out.println(robots.getFirst().getTypeRobot());
            Simulateur.ajouteEvenement(new Deplacement(1, robots.getFirst(), Direction.SUD ));
            Simulateur.ajouteEvenement(new Deplacement(10, robots.getFirst(), Direction.SUD ));
            Simulateur.ajouteEvenement(new Deplacement(20, robots.getFirst(), Direction.SUD ));
            Simulateur.ajouteEvenement(new Deplacement(30, robots.getFirst(), Direction.SUD ));


        }
        catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
