package org.azul;

import org.azul.visuel.Stats;
import org.azul.visuel.StatsParties;

public class Main implements IGestionnairePartie {

    private final int nbBots;
    private int nbParties;
    private final int nbPartiesTotals;
    private final int typeDePartie;
    private Stats stats;

    /**
     * Lance une partie ou non
     * @return Vérifie s'il reste encore des parties à lancées
     */
    synchronized boolean diminueNbParties(){

        if(nbParties > 0){
            nbParties -= 1;
            return true;
        }
        return false;
    }

    public Main(int nbBots, int nbPartiesTotals, int typeDePartie, Stats stats) {
        this.nbPartiesTotals = nbPartiesTotals;
        this.nbParties = nbPartiesTotals;
        this.nbBots = nbBots;
        this.typeDePartie = typeDePartie;
        this.stats = stats;
    }

    public static void main(String[] args) {

        int nbPartiesTotals = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
        int nbBots = (args.length > 1) ? Integer.parseInt(args[1]) : 3;
        int typeDePartie = (args.length > 2) ? Integer.parseInt(args[2]) : 1;
        Stats stats = new Stats();
        Main lanceur = new Main(nbBots, nbPartiesTotals, typeDePartie, stats);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Partie(lanceur);
            threads[i].start();
        }

        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        lanceur.affichageStats(lanceur.nbPartiesTotals);

    }

    /**
     * Fait appel à différentes méthodes de la classe Stats, gérant les statistiques.
     * @param nbParties Nombre de parties
     */
    public void affichageStats(int nbParties) {
        stats.afficherParties(nbParties);
        stats.afficherEgalite(nbParties);
        stats.afficherVictoiresBots(nbParties);
        stats.afficherManches(nbParties);
        stats.afficherNbTours(nbParties);
        stats.afficherMoyennePointsPerdus(nbParties);
        stats.affichageColonneComplete(nbParties);
        stats.affichageLigneComplete(nbParties);
        stats.affichageCouleur(nbParties);
    }

    /**
     * Lance une partie ou non
     * @return Vérifie s'il reste des parties à lancées
     */
    @Override
    public synchronized boolean encoreDesParties() {
        return diminueNbParties();
    }

    /**
     *
     * @return Nombre de bots
     */
    @Override
    public int getNbBots() {
        return nbBots;
    }

    /**
     *
     * @return Nombre de parties qu'il faut lancer
     */
    @Override
    public int getNbPartiesTotal() {
        return nbPartiesTotals;
    }

    /**
     * @return Type de partie à lancées
     */
    @Override
    public int getTypeDePartie() {
        return typeDePartie;
    }

    /**
     * @param statsParties Stats d'une partie
     */
    @Override
    public synchronized void addStats(StatsParties statsParties) {
        stats.ajouterStatsDesParties(statsParties);
    }

}


