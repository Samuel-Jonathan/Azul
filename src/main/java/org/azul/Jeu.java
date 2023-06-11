package org.azul;

import org.azul.arbitre.Arbitre;
import org.azul.bot.Bot;
import org.azul.bot.BotRandom;
import org.azul.bot.IBot;
import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.JeuFabrique;
import org.azul.visuel.StatsParties;
import org.azul.visuel.Vue;

import java.util.ArrayList;

/**
 * Classe Jeu dans laquelle se déroule le lancement d'une partie.
 *
 * @author PREAUX Nicolas, SAMUEL Jonathan, DAUPHIN Arthur
 */
public class Jeu {

    /**
     * Permet la création des bots pour une partie suivant le nombre que l'on souhaite, de préférence entre 2 et 4.
     *
     * @param nb Nombre de bots qu'on veut créer
     * @return Renvoie une liste des bots créés
     */
    public IBot[] creationBots(int nb, int typeDeJeu) {
        IBot[] bots = new IBot[nb];
        bots[0] = new BotRandom( 0);
        for (int i = 1; i < nb; i++) {
            bots[i] = new Bot(i, typeDeJeu);
        }
        return bots;
    }

    /**
     * Permet le lancement d'une partie.
     *
     * @param lesBots   Liste des différents bots qui vont jouer
     * @param statsParties     Stats des parties
     * @param nbParties Nombre de parties qu'on souhaite
     */
    public void lancementPartie(IBot[] lesBots, StatsParties statsParties, int nbParties, int typeDePartie) {
        int nbManches = 1;
        Fabrique[] lesFabriques;
        JeuFabrique jeuFabrique = new JeuFabrique();
        Arbitre arbitre = new Arbitre(typeDePartie, lesBots);
        CentreTable centreTable = new CentreTable();
        Vue vue = new Vue(typeDePartie);
        do {
            lesFabriques = jeuFabrique.generationFabriques(lesBots.length);
            while (arbitre.fabriquesPleinesEtCentreTablePlein(lesFabriques, centreTable)) {
                this.affichageFabriques(vue, lesFabriques, nbParties);
                arbitre.verifierCoupBot(lesFabriques, centreTable, vue, nbParties, statsParties, jeuFabrique);
                statsParties.ajouterNbTours();
            }
            arbitre.ajouterAuMur(statsParties, jeuFabrique);
            for (IBot bot : lesBots) {
                arbitre.getPlateauJoueurs(bot.getId()).getPlancher().resetMalus();
                this.affichageMurMalusScore(vue, arbitre, bot, nbParties);
                arbitre.getPlateauJoueurs(bot.getId()).getPlancher().resetMalus();
                centreTable.resetMalusCentreTable();
            }
            this.affichageNbManches(vue, nbParties, nbManches);
            nbManches++;
            if (!arbitre.estUneLigneComplete(lesBots)) {
                vue.afficherProchaineManche(nbParties);
            }

            if(arbitre.estUneLigneComplete(lesBots) && arbitre.aucuneTuiles(jeuFabrique)){
                for (IBot bot : lesBots) {
                    arbitre.getPlateauJoueurs(bot.getId()).getScore().ajouterAuScoreFinDePartie(arbitre.recupererNbLigneComplete(bot), arbitre.recupererNbColonneComplete(bot), arbitre.recupererNbCouleurComplete(bot));
                }
            }

            statsParties.ajouterNbManches();

        } while ((!arbitre.estUneLigneComplete(lesBots) && !arbitre.aucuneTuiles(jeuFabrique))  && nbManches < 100);
        ArrayList<IBot> botsGagnants;
        ArrayList<IBot> possiblesGagnants;
        possiblesGagnants = arbitre.recupererPossiblesGagnants(statsParties);
        botsGagnants = possiblesGagnants;
        if (possiblesGagnants.size() != 1) {
            botsGagnants = arbitre.recupererGagnantFinal(possiblesGagnants);
        }

        if(botsGagnants.size() > 1){
            /* Plusieurs parties*/
            statsParties.ajouterEgalite();
            /*-------------------*/

            /* Une partie */
            vue.afficherEgalite(nbParties);
            /* -----------*/
        }else {


            for (IBot botsGagnant : botsGagnants) {
                /* Plusieurs parties*/
                ArrayList<Integer> infosBotGagnants = new ArrayList<>();
                infosBotGagnants.add(botsGagnant.getId());
                infosBotGagnants.add(arbitre.getPlateauJoueurs(botsGagnant.getId()).getScore().getScore());
                statsParties.ajouterBotGagnants(infosBotGagnants);
                /*-------------------*/

                /* Une partie */
                vue.afficherGagnant(nbParties, botsGagnant.getId(), arbitre.getPlateauJoueurs(botsGagnant.getId()).getScore().getScore());
                /* -----------*/
            }
        }


    }

    /**
     * Fait appel à différentes méthodes de la classe Vue, affichant les différentes fabriques d'une partie.
     *
     * @param vue Vue qui contient les méthodes
     * @param lesFabriques Fabriques d'une partie
     * @param nbParties Nombre de parties
     */
    public void affichageFabriques(Vue vue, Fabrique[] lesFabriques, int nbParties) {
        vue.afficherPetiteSeparation(nbParties);
        vue.afficherFabriques(nbParties, lesFabriques);
        vue.afficherPetiteSeparation(nbParties);
    }

    /**
     * Fait appel à différentes méthodes de la classe Vue, affichant le mur, les malus et le score d'un bot.
     *
     * @param vue Vue qui contient les méthodes
     * @param arbitre Arbitre de la partie
     * @param bot Bot dont on veut afficher les éléments
     * @param nbParties Nombre de parties
     */
    public void affichageMurMalusScore(Vue vue, Arbitre arbitre, IBot bot, int nbParties) {
        vue.afficherPetiteSeparation(nbParties);
        vue.afficherMurBot(nbParties, bot.getId(), arbitre.getPlateauJoueurs(bot.getId()).getMur());
        vue.afficherMalus(nbParties, bot.getId(), arbitre.getPlateauJoueurs(bot.getId()).getPlancher().getMalus());
        vue.afficherScore(nbParties, bot.getId(), arbitre.getPlateauJoueurs()[bot.getId()].getScore().getScore());
    }

    /**
     * Fait appel à différentes méthodes de la classe Vue, affichant le nombre de manches.
     *
     * @param vue Vue qui contient les méthodes
     * @param nbParties Nombre de parties
     * @param nbManches Nombre de manches
     */
    public void affichageNbManches(Vue vue, int nbParties, int nbManches) {
        vue.afficherPetiteSeparation(nbParties);
        vue.afficherNbManches(nbParties, nbManches);
        vue.afficherGrandeSeparation(nbParties);
    }

}
