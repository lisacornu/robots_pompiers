package pathFinding;

import acteur.Robot;
import environment.Carte;
import environment.Case;
import environment.Direction;
import io.DonneeSimulation;

import java.util.*;

public class  Dijkstra {

    private static final Carte carte = DonneeSimulation.getCarte();
    private static HashMap<Case, Double> potentiels = new HashMap<>();
    private static LinkedList<Case> sommets = new LinkedList<>();
    private static HashMap<Case, Case> predecesseurs = new HashMap<>();
    private static Chemin chemin = new Chemin();


    private static void initAttributes () {
        potentiels = new HashMap<>();
        //casesNonMarquees = new HashSet<>();
        predecesseurs = new HashMap<>();
        chemin = new Chemin();
    }

    /**
     * les potentiels de tout les sommets sont set à +infini(MAX_VALUE) excepté celui du sommet de départ qui set à 0
     * @param startCase
     * @param robot
     */
    private static void initDijkstra (Case startCase,Robot robot) {
        for (Case[]  ligne : carte.getMatriceCase()) {
            for (Case caseMat : ligne) {
                potentiels.put(caseMat, Double.MAX_VALUE);
            }
        }
        potentiels.put(startCase, 0.0);
    }

    // Return le temps pour aller d'une case à celle d'à côté indiquée par @case2
    public static double getTemps(Case case1, Case case2, Robot robot) {
        return ((double) carte.getTailleCases() / (2 * robot.getSpeedOnCase(case1)) + (double) carte.getTailleCases() / (2 * robot.getSpeedOnCase(case2))
        )* 3.6;
    }

    /**
     *  Retourne Double.MAX_VALUE si le robot parcourt une case EAU. Cette fonction gères ce cas pour éviter les dépassements
     * @param potentiel
     * @param temps
     * @return
     */
    private static double addPotentielTemps (double potentiel, double temps) {
        return (potentiel == Double.MAX_VALUE || temps == Double.MAX_VALUE)
                ? Double.MAX_VALUE
                : potentiel + temps;
    }


    // update potentiel d'un sommet si conditions remplies
    private static void updateDistances (Case case1, Case case2, Robot robot) {
        double temps = getTemps(case1, case2, robot);  // temps pour aller de case 1 à case 2 pour le robot

        if (potentiels.get(case2) > addPotentielTemps(potentiels.get(case1), temps)) {
            potentiels.put(case2, addPotentielTemps(potentiels.get(case1), temps));
            sommets.add(case2);
            predecesseurs.put(case2, case1);
            chemin.updateTemps(temps);
        }
    }


    /**
     * Algorithme de Dijkstra permettant de déterminer le plus court chemin
     * @param startCase Case de départ du robot
     * @param robot robot qui se déplace
     */
    private static void dijkstra (Case startCase, Robot robot) {
        initDijkstra(startCase,robot);
        sommets.add(startCase);
        while (!sommets.isEmpty()) {
            Case case1 = sommets.poll();

            for (Direction dir : Direction.values())
                if (carte.voisinExiste(case1, dir))
                    updateDistances(case1, carte.getVoisin(case1, dir), robot);
        }
    }


    /**
     *      Retourne la direction vers laquelle se diriger pour aller de case1 à case2
     * @param case1
     * @param case2
     * @return la direction
     */
    private static Direction getDirection (Case case1, Case case2) {
        System.out.println(case1);
        for (Direction dir : Direction.values()) {
            if (carte.voisinExiste(case1, dir))
                if (carte.getVoisin(case1, dir) == case2)
                    return dir;
        }
        throw new IllegalArgumentException("Impossible de trouver la direction du prédécesseur");
    }

    /**
     * Obtiens la plus courte de distance entre startCase et destination pour le robot robot
     * @param startCase
     * @param destination
     * @param robot
     * @return le chemin en question
     */
    public static Chemin getPlusCourtChemin (Case startCase, Case destination, Robot robot) {
        initAttributes();
        dijkstra(startCase, robot);

        ArrayList<Direction> descChemin = chemin.getDescChemin();
        Case pred = destination;

        // construction du chemin sous forme de suite de direction en partant de la fin
        if(predecesseurs.get(pred) == null) {
            throw new NullPointerException("La case qui veut être atteinte n'est pas une case accessible par le robot");
        }

        while (pred != startCase) {
            // direction du prédecesseur vers la case actuelle
            System.out.println(pred);
            descChemin.add(getDirection(predecesseurs.get(pred), pred));

            pred = predecesseurs.get(pred);
        }

        // inversion pour avoir la suite de direction à prendre depuis le reobot de départ
        Collections.reverse(descChemin);
        chemin.setDescChemin(descChemin);
        System.out.println(chemin.getDescChemin());

        return chemin;
    }


}
