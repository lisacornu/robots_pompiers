package brainrobot;

import Simulation.Evenement;
import acteur.Robot;
import environment.*;
import io.DonneeSimulation;
import pathFinding.Chemin;

import java.util.ArrayList;
import java.util.Iterator;

public class basicBrain {

    private final Carte carte = DonneeSimulation.getCarte();
    private ArrayList<ArrayList<Robot>> robots_en_cours = new ArrayList<>();
    private ArrayList<Robot> inactive_robots = new ArrayList<>();
    private ArrayList<Case[]> couples_feux_eau = new ArrayList<>();

    public basicBrain() {
        getCoupleCases();
        getInactiveRobots();
        initializeRobotEnCours();
    }

    private void initializeRobotEnCours(){
        for (Incendie elt : DonneeSimulation.getIncendies()) {
            robots_en_cours.add(new ArrayList<>());
        }
    }

    private void getInactiveRobots() {
        inactive_robots.clear();
        for (Robot robot : DonneeSimulation.getRobots()) {
            if (!robot.isEvenementEnCours() || robot.getVolActuelReservoir() == 0){
                if (!robot.isSearchingForWater()){
                    this.inactive_robots.add(robot);
                    robot.getEvenementEnAttente().clear();
                    robot.setSearchingForWater(true);
                }
            }
        }
        System.out.println(inactive_robots);
    }


    private Case getNearestWater(Case fire){
        int x = fire.getX();
        int y = fire.getY();
        Case caseRet = null;
        int nearest = carte.getNbCol() + carte.getNblignes();
        int cur_dist ;
        for (int i = 0; i<carte.getNbCol(); i++){
            for (int j = 0; j<carte.getNblignes(); j++){
                if (carte.getCase(i,j).getNatureTerrain() == NatureTerrain.EAU) {
                    cur_dist = Math.abs(x-i) + Math.abs(y-j) ;
                    if (cur_dist<nearest){
                        nearest = cur_dist;
                        caseRet = carte.getCase(i,j);
                    }
                }
            }
        }
        return caseRet;
    }

    private Case[] getNearestWater(Robot robot){
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        Case[] caseRet = null;
        int nearest = carte.getNbCol() + carte.getNblignes();
        int cur_dist ;
        for (Case[] cases : this.couples_feux_eau){
            cur_dist = Math.abs(x-cases[1].getX()) + Math.abs(y-cases[1].getY());
            if (cur_dist<nearest){nearest = cur_dist; caseRet = new Case[]{cases[0], cases[1]};}
        }
        return caseRet;
    }

    private Case[] getNearestFire(Robot robot){
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        Case[] caseRet = null;
        int nearest = carte.getNbCol() + carte.getNblignes();
        int cur_dist ;
        for (Case[] cases : this.couples_feux_eau){
            cur_dist = Math.abs(x-cases[0].getX()) + Math.abs(y-cases[0].getY());
            if (cur_dist<nearest && cases[0].isOnFire()){nearest = cur_dist; caseRet = new Case[]{cases[0], cases[1]};}
        }
        return caseRet;
    }

    private void getCoupleCases() {
        ArrayList<Case[]> casesRet = new ArrayList<>();
        for (Incendie elt : DonneeSimulation.getIncendies()){
            Case currentFire = elt.getPosition();
            Case nearestWater = getNearestWater(currentFire);
            casesRet.add(new Case[]{currentFire,nearestWater});
        }
        this.couples_feux_eau = casesRet;
    }

    private int findIncendieInListe(Case fire){
        for (int i = 0; i < DonneeSimulation.getIncendies().size(); i++){
            if (DonneeSimulation.getIncendies().get(i).getPosition().getX() == fire.getX() && DonneeSimulation.getIncendies().get(i).getPosition().getY() == fire.getY()){
                return i;
            }
        }
        return -1;
    }

    public void giveNewOrders() {
        getInactiveRobots();
        for (Robot robot : this.inactive_robots){
            if (robot.getVolActuelReservoir() == 0) {
                System.out.println("je n'ai plus d'eau");
                Case[] cases = getNearestWater(robot);
                Case water = cases[1];
                if (!robot.isFlying()){
                    System.out.println("je ne suis pas un drone");
                    int max_dist = Integer.MAX_VALUE;
                    Direction rightdir = Direction.NORD;
                    for (Direction dir : Direction.values()){
                        if (carte.voisinExiste(water,dir) && carte.getVoisin(water,dir).getNatureTerrain() != NatureTerrain.EAU){
                            int dist = Math.abs(carte.getVoisin(water,dir).getX()-cases[0].getX()) + Math.abs(carte.getVoisin(water,dir).getY()-cases[0].getY());
                            if (dist < max_dist){max_dist = dist; rightdir = dir;}
                        }
                    }
                    System.out.println(rightdir);
                    water = carte.getVoisin(water,rightdir);
                }
                System.out.println("mdr : "+water);
                int pos_inc = findIncendieInListe(cases[0]);
                this.robots_en_cours.get(pos_inc).add(robot);
                robot.goToDestination(robot.getPlusCourtChemin(robot.getPosition(),water).getDescChemin());
                robot.remplirReservoir();
                System.out.println(cases[0]);
                robot.goToDestination(robot.getPlusCourtChemin(water,cases[0]).getDescChemin());
                robot.intervient();
            }
            else {
                System.out.println("issou");
                Case[] cases = getNearestFire(robot);
                int pos_inc = findIncendieInListe(cases[0]);
                this.robots_en_cours.get(pos_inc).add(robot);
                System.out.println(cases[0]);
                Chemin chemin = robot.getPlusCourtChemin(robot.getPosition(),carte.getCase(cases[0].getY(),cases[0].getX()));
                robot.goToDestination(chemin.getDescChemin());
                robot.intervient();
            }
        }
    }

    public void resetOrders() {
        for (Incendie incendie : DonneeSimulation.getIncendies()) {
            if (incendie.getIntensite() == 0){
                int ind_inc = findIncendieInListe(incendie.getPosition());
                for (Iterator<Robot> it = this.robots_en_cours.get(ind_inc).iterator(); it.hasNext();)
                {
                    Robot robot = it.next();
                    robot.getEvenementEnAttente().clear();
                    it.remove();
                }
            }
        }
    }

}
