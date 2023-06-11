package org.azul.visuel;

import org.azul.fabrique.Tuile;

import java.text.DecimalFormat;
import java.util.*;

public class Stats {

    private ArrayList<StatsParties> statsDesParties = new ArrayList<>();

    /**
     * @param statsParties stats de chaque parties
     */
    public synchronized void ajouterStatsDesParties(StatsParties statsParties) {
        this.statsDesParties.add(statsParties);
    }

    /**
     * Afficher les parties
     */
    public void afficherParties(int nbParties) {

        if(nbParties > 1){
            for (int i = 0; i < statsDesParties.size(); i++) {
                for (int j = 0; j < statsDesParties.get(i).getBotGagnants().size(); j++) {
                    System.out.println("Partie " + (i + 1) + " : Le bot n°" + statsDesParties.get(i).getBotGagnants().get(j).get(0) +
                            " est le gagnant avec un score de " +
                            statsDesParties.get(i).getBotGagnants().get(j).get(1));
                }
            }
        }
    }




    /**
     * Calcul le nombre de victoires par bots
     * @return Retourne les moyennes des bots gagnants
     */
    public HashMap<Integer, Integer> calculVictoire(){

        HashMap<Integer, Integer> nbVictoires = new HashMap<>();

        for (StatsParties statsDesParties : statsDesParties) {
            for (int j = 0; j < statsDesParties.getBotGagnants().size(); j++) {

                if(!nbVictoires.containsKey(statsDesParties.getBotGagnants().get(j).get(0))){

                    nbVictoires.put(statsDesParties.getBotGagnants().get(j).get(0), 1);
                }else {

                    nbVictoires.put(statsDesParties.getBotGagnants().get(j).get(0), nbVictoires.get(statsDesParties.getBotGagnants().get(j).get(0))+1);
                }
            }

        }

        return nbVictoires;
    }

    /**
     * Affiche le nombre de victoires par bots
     * @param nbParties Nombre de parties lancées
     */
    public void afficherVictoiresBots(int nbParties) {

        if(nbParties > 1) {

            for (Map.Entry<Integer, Integer> entry : calculVictoire().entrySet()) {
                double id = entry.getKey();
                double nbVictoires = entry.getValue();

                DecimalFormat df = new DecimalFormat("#.##");
                double pourcentage = (nbVictoires / (nbParties) * 100);
                System.out.println("\nLe bot n°" + (int) id + " a gagné " + (int) nbVictoires + " parties ! (" + df.format(pourcentage) + "%)");
            }
        }
    }

    /**
     * Calcul le nombre de tours
     * @return Nombre de tours par parties
     */
    public float calculNbTours(){
        float nbTours = 0;

        for (StatsParties statsDesParties : statsDesParties) {

            nbTours += statsDesParties.getNbTours();
        }

        return nbTours;
    }


    /**
     * Affiche le nombre de tours par parties
     * @param nbParties Nombre de parties lancées
     */
    public void afficherNbTours(int nbParties){

        //Affiche la moyenne du nombre de tours
        if(nbParties > 1)System.out.println("\nMoyenne du nombre de tours par partie: " + calculNbTours()/nbParties);
    }

    /**
     * Calcul le nombre de points perdus avec la ligne plancher par bot
     * @return Nombre de points perdus avec la ligne plancher par bot
     */
    public HashMap<Integer, Float> calculPointsPerdus(){

        HashMap<Integer, Float> nbPointsPerdus = new HashMap<>();

        for (StatsParties statsDesParties : statsDesParties) {
            for (Map.Entry<Integer,Integer> entry : statsDesParties.getNbPointPerdus().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();

                if(nbPointsPerdus.containsKey(key)){
                    nbPointsPerdus.put(key, nbPointsPerdus.get(key) + value);
                }else{
                    nbPointsPerdus.put(key, value);
                }
            }
        }
        return nbPointsPerdus;
    }

    /**
     * Affiche la moyenne du nombre de points perdus avec la ligne plancher
     * @param nbParties Nombre de parties
     */
    public void afficherMoyennePointsPerdus(int nbParties){

        if(nbParties > 1) {
            System.out.println("");

            for (Map.Entry<Integer, Float> entry : calculPointsPerdus().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();
                //Affiche la moyenne du nombre de points perdus avec la ligne plancher par bots
                System.out.println("Moyenne du nombre de points perdus avec la ligne plancher par partie : " + value / nbParties + " (Bot n°" + key + ")");
            }
        }

    }

    /**
     * Calcul le nombre d'égalités
     * @return Nombre d'égalités
     */
    public int calculEgalite(){

        int egalite = 0;

        for (StatsParties statsDesParties : statsDesParties) {
            egalite += statsDesParties.getEgalite();
        }

        return egalite;

    }

    /**
     * Affiche le nombre d'égalités
     */
    public void afficherEgalite(int nbParties) {
        if(nbParties > 1) System.out.println("\nIl y a " + calculEgalite() + " égalités !");
    }


    /**
     * Calcul le nombre de manches
     * @param nbParties Nombre de parties lancées
     * @return Nombre de manches
     */
    public float calculManches(int nbParties){
        float nbManches = 0;

        for (StatsParties statsDesParties : statsDesParties) {

            nbManches += statsDesParties.getNbManches();
        }

        nbManches /= nbParties;

        return nbManches;
    }


    /**
     * Affiche le nombre de manches
     */
    public void afficherManches(int nbParties) {

        if(nbParties > 1) System.out.println("\nIl y a " + calculManches(nbParties) + " manches en moyenne par parties !");
    }

    /**
     *
     * @return Nombre de colonnes complétées
     */
    public HashMap<Integer, Float> calculColonneComplete(){

        HashMap<Integer, Float> nbColonneCompletees = new HashMap<>();

        for (StatsParties statsDesParties : statsDesParties) {
            for (Map.Entry<Integer,Integer> entry : statsDesParties.getNbColonneComplete().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();

                if(nbColonneCompletees.containsKey(key)){
                    nbColonneCompletees.put(key, nbColonneCompletees.get(key) + value);
                }else{
                    nbColonneCompletees.put(key, value);
                }
            }
        }
        return nbColonneCompletees;
    }

    /**
     *
     * @param nbParties Nombre de parties lancées
     */
    public void affichageColonneComplete(int nbParties){

        if(nbParties > 1) {
            System.out.println("");

            for (Map.Entry<Integer, Float> entry : calculColonneComplete().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();

                System.out.println("Le nombre de colonnes complétées par partie : " + value / nbParties + " (Bot n°" + key + ")");
            }
        }
    }


    /**
     *
     * @return Nombre de lignes complétées
     */
    public HashMap<Integer, Float> calculLignesComplete(){

        HashMap<Integer, Float> nbLignesCompletees = new HashMap<>();

        for (StatsParties statsDesParties : statsDesParties) {
            for (Map.Entry<Integer,Integer> entry : statsDesParties.getNbLignesComplete().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();

                if(nbLignesCompletees.containsKey(key)){
                    nbLignesCompletees.put(key, nbLignesCompletees.get(key) + value);
                }else{
                    nbLignesCompletees.put(key, value);
                }
            }
        }
        return nbLignesCompletees;
    }

    /**
     *
     * @param nbParties Nombre de parties lancées
     */
    public void affichageLigneComplete(int nbParties){

        if(nbParties > 1) {
            System.out.println("");

            for (Map.Entry<Integer, Float> entry : calculLignesComplete().entrySet()) {
                int key = entry.getKey();
                float value = entry.getValue();

                System.out.println("Le nombre de lignes complétées par partie : " + value / nbParties + " (Bot n°" + key + ")");
            }
        }
    }

    /**
     *
     * @return Nombre de couleurs des murs des bots
     */
    public HashMap<Tuile, Integer> calculCouleurs(){
        HashMap<Tuile, Integer> nbCouleurs = new HashMap<>();

        for (StatsParties statsDesParties : statsDesParties) {
            for (int i = 0; i < statsDesParties.getNbCouleurs().size(); i++) {
                for (int j = 0; j < statsDesParties.getNbCouleurs().get(i).getTailleLigne(); j++) {
                    for (int k = 0; k < statsDesParties.getNbCouleurs().get(i).getTailleColonne(); k++) {
                        Tuile tuile = statsDesParties.getNbCouleurs().get(i).getTuileMur(j,k);

                        if(nbCouleurs.containsKey(tuile)){
                            nbCouleurs.put(tuile, nbCouleurs.get(tuile) + 1);
                        }else{
                            nbCouleurs.put(tuile, 1);
                        }
                    }
                }
            }
        }

        nbCouleurs.remove(null);

        return nbCouleurs;
    }

    /**
     *
     * @param nbParties Nombre de parties lancées
     */
    public void affichageCouleur(int nbParties){

        if(nbParties > 1) {
            System.out.println("");

            for (Map.Entry<Tuile, Integer> entry :  calculCouleurs().entrySet()) {
                Tuile key = entry.getKey();
                float value = entry.getValue();

                System.out.println(key + " : " + value / nbParties);
            }
        }
    }


    /**
     *
     * @return Stats des parties
     */
    public ArrayList<StatsParties> getStats() {
        return statsDesParties;
    }
}
