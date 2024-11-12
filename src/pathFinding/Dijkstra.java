package pathFinding;

import acteur.Robot;
import environment.Carte;
import environment.Case;
import environment.Direction;
import io.DonneeSimulation;

import java.util.*;

public class Dijkstra {

    private static final Carte carte = DonneeSimulation.getCarte();
    private static HashMap<Case, Double> potentiels = new HashMap<>();
    private static HashSet<Case> casesNonMarquees = new HashSet<>();
    private static HashMap<Case, Case> predecesseurs = new HashMap<>();
    private static Chemin chemin = new Chemin();


    private static void initAttributes () {
        potentiels = new HashMap<>();
        casesNonMarquees = new HashSet<>();
        predecesseurs = new HashMap<>();
        chemin = new Chemin();
    }


    // les potentiels de tout les sommets sont set à +infini excepté celui du sommet de départ
    private static void initDijkstra (Case startCase) {
        for (Case[]  ligne : carte.getMatriceCase()) {
            for (Case caseMat : ligne) {
                potentiels.put(caseMat, Double.MAX_VALUE);
                casesNonMarquees.add(caseMat);
            }
        }
        potentiels.put(startCase, 0.0);
        casesNonMarquees.remove(startCase);
    }


    // trouve le sommet de plus petit potentiel dans les sommetss non marqués
    private static Case trouveMinDistance () {
        double min = Double.MAX_VALUE;
        Case caseActuelle = null;
        for (Case caseNonMarquee : casesNonMarquees) {
            if (potentiels.get(caseNonMarquee) <= min) {
                min = potentiels.get(caseNonMarquee);
                caseActuelle = caseNonMarquee;
            }
        }
        if (caseActuelle != null) return caseActuelle;
        else throw new IllegalArgumentException("Impossible de trouver une case");
    }


    // Return le temps pour aller d'une case à celle d'à côté indiquée par @case2
    private static double getTemps (Case case1, Case case2, Robot robot) {
        return ((double) carte.getTailleCases() /
                (robot.getSpeedOnCase(case1) + robot.getSpeedOnCase(case2)) /2) * 3.6;
    }


    // getTemps return Double.MAX_VALUE si le robot ârcourt une case EAU,
    // cette fonction gères ce cas pour éviter les dépassements
    private static double addPotentielTemps (double potentiel, double temps) {
        return (potentiel == Double.MAX_VALUE || temps == Double.MAX_VALUE)
                ? Double.MAX_VALUE
                : potentiel + temps;
    }


    // update potentiel d'un sommet si conditions remplies
    private static void updateDistances (Case case1, Case case2, Robot robot) {
        double temps = getTemps(case1, case2, robot);  // temps pour aller de case 1 à case 2 pour le robot

        if (potentiels.get(case2) > addPotentielTemps(potentiels.get(case1), temps)) {
            potentiels.put(case2, potentiels.get(case1) + temps);
            predecesseurs.put(case2, case1);
            chemin.updateTemps(temps);
        }
    }


    // corps principale de l'algorithme de dijkstra
    private static void dijkstra (Case startCase, Robot robot) {
        initDijkstra(startCase);

        while (! casesNonMarquees.isEmpty()) {
            Case case1 = trouveMinDistance();
            casesNonMarquees.remove(case1);

            for (Direction dir : Direction.values())
                if (carte.voisinExiste(case1, dir))
                    updateDistances(case1, carte.getVoisin(case1, dir), robot);
        }
    }


    // return la direction vers laquelle se diriger pour aller de case1 à case2
    private static Direction getDirection (Case case1, Case case2) {
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(case1, dir))
                if (carte.getVoisin(case1, dir) == case2)
                    return dir;
        }
        throw new IllegalArgumentException("Impossible de trouver la direction du prédécesseur");
    }


    public static Chemin getPlusCourtChemin (Case startCase, Case destination, Robot robot) {
        initAttributes();
        dijkstra(startCase, robot);

        ArrayList<Direction> descChemin = chemin.getDescChemin();
        Case pred = destination;

        // construction du chemin sous forme de suite de direction en partant de la fin
        while (pred != startCase) {
            // direction du prédecesseur vers la case actuelle
            descChemin.add(getDirection(predecesseurs.get(pred), pred));
            pred = predecesseurs.get(pred);
        }

        // inversion pour avoir la suite de direction à prendre depuis le reobot de départ
        Collections.reverse(descChemin);
        chemin.setDescChemin(descChemin);

        return chemin;
    }
}
