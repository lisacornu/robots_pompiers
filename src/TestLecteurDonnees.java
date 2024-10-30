
import environment.Carte;
import environment.Incendie;
import io.DonneeSimulation;
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneeSimulation donneeSimulation = LecteurDonnees.lire(args[0]);
            donneeSimulation.getCarte().printMatriceCase();
            ArrayList<Incendie> incendies = donneeSimulation.getIncendies();

            for (Incendie i : incendies) {
                System.out.println(i);
            }

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

