package org.azul.visuel;

import org.azul.fabrique.Tuile;
import org.azul.plateau.Mur;

import java.util.ArrayList;
import java.util.HashMap;

public class StatsParties {

    private ArrayList<ArrayList<Integer>> botGagnants = new ArrayList<>();

    //Moyenne du nombre de manches par parties
    private int nbManches;

    //Nombre d'égalités
    private int egalite;


    //Nombre de tours de toutes les parties
    private int nbTours = 0;

    //Nombre de points perdus avec la ligne plancher par bot
    private HashMap<Integer, Integer> nbPointPerdus = new HashMap<>();

    //Nombre de colonnes complétées par bot
    private HashMap<Integer, Integer> nbColonneComplete = new HashMap<>();

    //Nombre de lignes complétées par bot
    private HashMap<Integer, Integer> nbLignesComplete = new HashMap<>();

    //Nombre de couleurs des murs des bots
    private ArrayList<Mur> nbCouleurs = new ArrayList<>();
    /**
     *
     * @param bot Ajout le bot gagnant de chaque partie
     */
    public synchronized void ajouterBotGagnants(ArrayList<Integer> bot) {
        this.botGagnants.add(bot);
    }

    /**
     * Ajoute une manche
     */
    public synchronized void ajouterNbManches() {
        this.nbManches++;
    }

    /**
     * Ajoute une égalité
     */
    public synchronized void ajouterEgalite() {
        this.egalite++;
    }

    /**
     * Ajoute un tour
     */
    public synchronized void ajouterNbTours() {
        nbTours++;
    }

    /**
     * Ajoute les points perdus
     */
    public synchronized void ajouterPointsPerdus(int idBot, int points){
        nbPointPerdus.put(idBot, points);
    }

    /**
     *
     * @param idBot Id du bot
     * @param colonnes Nombre de colonne complétées
     */
    public synchronized void ajouterNbColonneComplete(int idBot, int colonnes){ nbColonneComplete.put(idBot, colonnes); }

    /**
     *
     * @param idBot Id du bot
     * @param lignes Nombre de lignes complétées
     */
    public synchronized void ajouterNbLignesComplete(int idBot, int lignes){ nbLignesComplete.put(idBot, lignes); }

    public synchronized void ajouterNbCouleurs(Mur mur){
        nbCouleurs.add(mur);
    }

    /**
     * @return Liste des gagnants
     */
    public ArrayList<ArrayList<Integer>> getBotGagnants() {
        return botGagnants;
    }

    /**
     *
     * @return Nombre d'égalité
     */
    public int getEgalite() {
        return egalite;
    }

    /**
     *
     * @return Nombre de manches
     */
    public int getNbManches() {
        return nbManches;
    }

    /**
     *
     * @return Nombre de tours
     */
    public int getNbTours() {
        return nbTours;
    }

    /**
     *
     * @return Nombre de points perdus avec la ligne plancher par bot
     */
    public HashMap<Integer, Integer> getNbPointPerdus() {
        return nbPointPerdus;
    }

    /**
     *
     * @return Nombre de colonne complétées pour chaque bots
     */
    public HashMap<Integer, Integer> getNbColonneComplete() {
        return nbColonneComplete;
    }

    /**
     *
     * @return Nombre de lignes complétées pour chaque bots
     */
    public HashMap<Integer, Integer> getNbLignesComplete() {
        return nbLignesComplete;
    }

    /**
     *
     * @return Nombre de couleurs des murs des bots
     */
    public ArrayList<Mur> getNbCouleurs() {
        return nbCouleurs;
    }
}
