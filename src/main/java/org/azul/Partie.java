package org.azul;

import org.azul.bot.IBot;
import org.azul.visuel.StatsParties;

public class Partie extends Thread {

    private IGestionnairePartie gestionnairePartie;


    /**
     *
     * @param gestionnairePartie Éléments nécessaires à l'exécution d'une partie
     */
    public Partie(IGestionnairePartie gestionnairePartie){
        this.setGestionnairePartie(gestionnairePartie);
    }

    /**
     * Lance une partie
     */
    public void run(){
        while(gestionnairePartie.encoreDesParties()){
            Jeu sousMoteur = new Jeu();
            IBot[] lesBots = sousMoteur.creationBots(gestionnairePartie.getNbBots(), gestionnairePartie.getTypeDePartie());
            StatsParties statsParties = new StatsParties();
            sousMoteur.lancementPartie(lesBots, statsParties, gestionnairePartie.getNbPartiesTotal(), gestionnairePartie.getTypeDePartie());
            gestionnairePartie.addStats(statsParties);

        }
    }

    /**
     *
     * @param gestionnairePartie Modifie le gestionnaire de partie
     */
    private void setGestionnairePartie(IGestionnairePartie gestionnairePartie) {
        this.gestionnairePartie = gestionnairePartie;
    }

}