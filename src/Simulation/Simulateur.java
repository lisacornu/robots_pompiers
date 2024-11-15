package Simulation;

import acteur.Robot;
import environment.Carte;
import environment.Case;
import environment.Incendie;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import io.DonneeSimulation;
import io.LecteurDonnees;

import java.awt.*;
import java.io.FileNotFoundException;
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
    // HashMap avec comme clés les dates d'évenements et comme valeur une liste d'évenements,
    // afin d'avoir accès à tout les evenements ayant lieu à une certaine date
    private static final HashMap<Long,ArrayList<Evenement>> Evenements = new HashMap<>();


    public static void ajouteEvenement(Evenement e){
        Evenements.computeIfAbsent(e.date, k -> new ArrayList<Evenement>());
        Evenements.get(e.date).add(e);
    }

    public static ArrayList<Evenement> getExecutingEvent() {return executingEvent;}

    public static void setExecutingEvent(ArrayList<Evenement> executingEvent) {Simulateur.executingEvent = executingEvent;}

    public static void supprimeEvenement(Evenement e){Evenements.get(e.date).remove(e);}

    public static long getDateSimulation() {return dateSimulation;}

    public static void setDateSimulation(long dateSimulation) {Simulateur.dateSimulation = dateSimulation;}

    public HashMap<Long, ArrayList<Evenement>> getEvenements() {
        return Evenements;
    }

    private void incrementeDate(){
        dateSimulation++;
    }

    /**
     * @return boolean indiquant si la simulation est terminée
     */
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
        this.donneeSimulation = initDonneeSimulation(donnees);
        gui.setSimulable(this);
        draw();
    }

    /**
     * Dessine la carte à l'aide d'images qui sont choisis aléatoirement
     */
    private void draw_map() {

        int height_case = gui.getHeight()/DonneeSimulation.getCarte().getNbCol();
        int width_case  = gui.getWidth()/DonneeSimulation.getCarte().getNblignes();
        int y = 0;
        int x = 0;
        Carte carte = DonneeSimulation.getCarte();
        Case[][] matriceCase = carte.getMatriceCase();
        ArrayList<Incendie> incendies = DonneeSimulation.getIncendies();
        //Dessine les terrains
        for(int i = 0; i < carte.getNblignes(); i++){
            for (int j = 0; j < carte.getNbCol(); j++){
                gui.addGraphicalElement(new ImageElement(x,y,matriceCase[i][j].getImagePath(),width_case,height_case, null));
                //Dessine les incendies
                for(Incendie incendie:incendies){
                    if(incendie.getPosition() == matriceCase[i][j]){
                        gui.addGraphicalElement(new ImageElement(x,y,"images/feu.png",width_case,height_case, null));
                    }
                }
                x+= width_case;
            }
            y += height_case;
            x = 0;
        }
    }

    /**
     * Dessine les robots de la carte
     */
    private void draw_robots(){
        ArrayList<Robot> robots = DonneeSimulation.getRobots();
        int x;
        int y;
        int height_case = gui.getHeight()/DonneeSimulation.getCarte().getNbCol();
        int width_case  = gui.getWidth()/DonneeSimulation.getCarte().getNblignes();
        //Dessine les robots
        for(Robot robot:robots) {
            x = robot.getPosition().getX() * width_case;
            y = robot.getPosition().getY() * height_case;
            gui.addGraphicalElement(new ImageElement(x, y, robot.getSpritePath(), width_case, height_case, null));
        }

    }

    private void draw(){
        //Dessine toute la carte.
        gui.reset();
        draw_map();
        draw_robots();
    }

    @Override
    public void next() {
        if(dateSimulation == 0){
            System.out.println("START");
        }

        for (Robot robot : DonneeSimulation.getRobots()) {
            System.out.println(robot.getEvenementEnAttente());
        }
        System.out.println("debut boucle brain");
        DonneeSimulation.getBrain().resetOrders();
        DonneeSimulation.getBrain().giveNewOrders();
        System.out.println("fin bucle brain");

        incrementeDate();
        HashMap<Long, ArrayList<Evenement>> evenements = this.getEvenements();
        ArrayList<Evenement> eventsToDate = evenements.get(getDateSimulation()); //Liste des evenements commencant à la date dateSimulation
        ArrayList<Evenement> eventToAdd = new ArrayList<>();

        if(eventsToDate != null) {
            //Si la liste est non nulle, alors on appelle la methode execute de tous ces evenements
            for (Evenement evenement : eventsToDate) {
                evenement.execute();
            }
        }
        //Ensuite on execute tous les evenements déjà en cours
        for (Iterator<Evenement> it = executingEvent.iterator(); it.hasNext();) {
            Evenement evenement = it.next();
            evenement.execute();

            if(evenement.isDone()){
                //Si "evenement" est fini alors on le supprime de la liste des evenements en cours
                it.remove();

                //On regarde si le robot a d'autres evenement en cours
                if(evenement.getRobot().getEvenementEnAttente().isEmpty()){
                    //Si il n'en a pas, il est en attente.
                    evenement.getRobot().setEvenementEnCours(false);
                }
                else{
                    //Sinon, on ajoute le premier evenement de la liste evenementEnAttente et on le rajoute
                    //à la liste des evenements en cours, on actualise la date de début d'execution.
                    //et enfin, on le supprime de la liste des evenements en cours
                    Evenement evenementAjouter =  evenement.getRobot().getEvenementEnAttente().get(0);
                    evenementAjouter.setDate(getDateSimulation());
                    eventToAdd.add(evenementAjouter);
                    evenement.getRobot().getEvenementEnAttente().remove(evenementAjouter);
                }
            }
        }
        //Finalement on ajoute les nouveaux evenements à executer dans la liste des evenements en cours.
        executingEvent.addAll(eventToAdd);
        draw();
    }



    @Override
    public void restart() {
        ArrayList<Robot> robotsCopy = new ArrayList<Robot>(DonneeSimulation.getRobots());
        for (ArrayList<Evenement> listEvent : Evenements.values()){
            listEvent.clear();
        }

        try {
            DonneeSimulation.getRobots().clear();
            DonneeSimulation.getIncendies().clear();

            //Relis les données de la carte initiale.
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
        setDateSimulation(0);
        draw();
    }


}
