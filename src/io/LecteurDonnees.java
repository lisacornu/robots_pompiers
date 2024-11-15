package io;


import acteur.*;
import environment.Carte;
import environment.Case;
import environment.Incendie;
import environment.NatureTerrain;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;


public class LecteurDonnees {

    /**
     * Lit fichierDonnees et créé les instances de classes correspondantes au contenu du fichier
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneeSimulation lire (String fichierDonnees) throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        DonneeSimulation donneeSimulation = new DonneeSimulation();

        donneeSimulation.setCarte(lecteur.initCarte());
        donneeSimulation.setIncendies(lecteur.lireIncendies(DonneeSimulation.getCarte()));
        donneeSimulation.setRobots(lecteur.lireRobots(DonneeSimulation.getCarte()));
        DonneeSimulation.InitBrain();

        scanner.close();
        return donneeSimulation;
    }


    // Remets à zéro les données de la simulation en re parcourant le fichier
    public static void resetDonneeSimulation(DonneeSimulation donneeSimulation ,String fichierDonnees) throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        donneeSimulation.setCarte(lecteur.initCarte());
        donneeSimulation.setIncendies(lecteur.lireIncendies(DonneeSimulation.getCarte()));
        donneeSimulation.setRobots(lecteur.lireRobots(DonneeSimulation.getCarte()));
        DonneeSimulation.InitBrain();
        scanner.close();
    }



    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }


    private Carte initCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            Carte carte = new Carte(tailleCases, nbLignes, nbColonnes);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    initMatriceCase(lig, col, carte);
                }
            }
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. " + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }


    private void initMatriceCase(int lig, int col, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature;
        try {
            chaineNature = scanner.next();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature); // recup la valeur de l'enum

            verifieLigneTerminee();

            carte.setCaseMatrice(lig, col, nature);
            carte.getCase(lig,col).setImagePath();
            carte.getCase(lig,col).setOnFire(false);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. " + "Attendu: nature altitude [valeur_specifique]");
        }
    }


    /**
     * Lit et retourne dans un ArrayList les donnees des incendies.
     */
    private ArrayList<Incendie> lireIncendies(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            ArrayList<Incendie> incendies = new ArrayList<Incendie>();
            for (int i = 0; i < nbIncendies; i++) {
                incendies.add(createIncendie(carte));
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. " + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et créé chaque objet incendie
     * @param carte carte de la simulation
     */
    private Incendie createIncendie(Carte carte) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();


            if (intensite <= 0)
                throw new DataFormatException("nb litres pour eteindre incendie doit etre > 0");

            verifieLigneTerminee();
            carte.getCase(lig,col).setOnFire(true);
            return new Incendie(carte.getCase(lig, col), intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. " + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     * @param carte carte de la simulation
     */
    private ArrayList<Robot> lireRobots(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            ArrayList<Robot> robots = new ArrayList<Robot>();
            for (int i = 0; i < nbRobots; i++) {
                robots.add(createRobot(carte));
            }
            return robots;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. " + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param carte carte de la simulation
     */
    private Robot createRobot(Carte carte) throws DataFormatException {
        ignorerCommentaires();

        try {
            // les scanner.nextInt vont respectivement recup la ligne et la colonne
            Case position = carte.getCase(scanner.nextInt(), scanner.nextInt());
            TypeRobot type = TypeRobot.valueOf(scanner.next());

            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?

            return switch (type) {
                case ROUES -> {
                    verifieLigneTerminee();
                    yield new RobotRoue(position, s);
                }
                case CHENILLES -> {
                    verifieLigneTerminee();
                    yield new RobotChenille(position, s);
                }
                case PATTES -> {
                    verifieLigneTerminee();
                    yield new RobotPattes(position);
                }
                case DRONE -> {
                    verifieLigneTerminee();
                    yield new RobotDrone(position, s);
                }
            };

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. " + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws DataFormatException
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
