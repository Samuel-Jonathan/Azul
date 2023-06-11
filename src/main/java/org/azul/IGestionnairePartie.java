package org.azul;

import org.azul.visuel.StatsParties;

public interface IGestionnairePartie {

    /**
     *
     * @return Vérifie si il reste des parties à lancées
     */
    boolean encoreDesParties();

    /**
     *
     * @return Nombre de bots
     */
    int getNbBots();

    /**
     *
     * @return Nombre de parties
     */
    int getNbPartiesTotal();

    /**
     *
     * @return Type de partie qu'il faut lancer
     */
    int getTypeDePartie();


    void addStats(StatsParties statsParties);
}
