package pathFinding;

import acteur.Robot;
import environment.Carte;
import environment.Case;
import environment.Direction;
import io.DonneeSimulation;

import java.util.*;

public class Dijkstra {

    private final Carte carte = DonneeSimulation.getCarte();
    private final HashMap<Case, Double> potentiels = new HashMap<>();
    private final HashSet<Case> casesNonMarquees = new HashSet<>();
    private final HashMap<Case, Case> predecesseurs = new HashMap<>();
    private final Chemin chemin = new Chemin();


    // les potentiels de tout les sommets sont set à +infini excepté celui du sommet de départ
    private void initDijkstra (Case startCase) {
        for (Case[]  ligne : carte.getMatriceCase()) {
            for (Case caseMat : ligne) {
                this.potentiels.put(caseMat, Double.MAX_VALUE);
                this.casesNonMarquees.add(caseMat);
            }
        }
        this.potentiels.put(startCase, 0.0);
        this.casesNonMarquees.remove(startCase);
    }


    // trouve le sommet de plus petit potentiel dans les sommetss non marqués
    private Case trouveMinDistance () {
        double min = Double.MAX_VALUE;
        Case caseActuelle = null;
        for (Case caseNonMarquee : this.casesNonMarquees) {
            if (this.potentiels.get(caseNonMarquee) <= min) {
                min = this.potentiels.get(caseNonMarquee);
                caseActuelle = caseNonMarquee;
            }
        }
        if (caseActuelle != null) return caseActuelle;
        else throw new IllegalArgumentException("Impossible de trouver une case");
    }


    // Return le temps pour aller d'une case à celle d'à côté indiquée par @case2
    private double getTemps (Case case1, Case case2, Robot robot) {
        return ((double) carte.getTailleCases() /
                (robot.getSpeedOnCase(case1) + robot.getSpeedOnCase(case2)) /2) * 3.6;
    }


    // getTemps return Double.MAX_VALUE si le robot ârcourt une case EAU,
    // cette fonction gères ce cas pour éviter les dépassements
    private double addPotentielTemps (double potentiel, double temps) {
        return (potentiel == Double.MAX_VALUE || temps == Double.MAX_VALUE)
                ? Double.MAX_VALUE
                : potentiel + temps;
    }


    // update potentiel d'un sommet si conditions remplies
    private void updateDistances (Case case1, Case case2, Robot robot) {
        double temps = this.getTemps(case1, case2, robot);  // temps pour aller de case 1 à case 2 pour le robot

        if (this.potentiels.get(case2) > addPotentielTemps(this.potentiels.get(case1), temps)) {
            this.potentiels.put(case2, this.potentiels.get(case1) + temps);
            this.predecesseurs.put(case2, case1);
            this.chemin.updateTemps(temps);
        }
    }


    // corps principale de l'algorithme de dijkstra
    private void dijkstra (Case startCase, Robot robot) {
        this.initDijkstra(startCase);

        while (! this.casesNonMarquees.isEmpty()) {
            Case case1 = trouveMinDistance();
            this.casesNonMarquees.remove(case1);

            for (Direction dir : Direction.values())
                if (this.carte.voisinExiste(case1, dir))
                    updateDistances(case1, this.carte.getVoisin(case1, dir), robot);
        }
    }


    // return la direction vers laquelle se diriger pour aller de case1 à case2
    private Direction getDirection (Case case1, Case case2) {
        for (Direction dir : Direction.values()) {
            if (this.carte.voisinExiste(case1, dir))
                if (this.carte.getVoisin(case1, dir) == case2)
                    return dir;
        }
        throw new IllegalArgumentException("Impossible de trouver la direction du prédécesseur");
    }


    public Chemin getPlusCourtChemin (Case startCase, Case destination, Robot robot) {
        dijkstra(startCase, robot);

        ArrayList<Direction> descChemin = this.chemin.getDescChemin();
        Case pred = destination;

        // construction du chemin sous forme de suite de direction en partant de la fin
        while (pred != startCase) {
            // direction du prédecesseur vers la case actuelle
            descChemin.add(getDirection(this.predecesseurs.get(pred), pred));
            pred = this.predecesseurs.get(pred);
        }

        // inversion pour avoir la suite de direction à prendre depuis le reobot de départ
        Collections.reverse(descChemin);
        this.chemin.setDescChemin(descChemin);

        return this.chemin;
    }
}
