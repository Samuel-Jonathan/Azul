package org.azul.plateau;

import org.azul.fabrique.Tuile;
import org.azul.visuel.StatsParties;

import java.util.ArrayList;

/**
 * @author Berges Jeremy, Dauphin Arthur
 */
public class Plancher {
    private int malus;
    ArrayList plancher = new ArrayList(7);

    public Plancher() {
        malus = 0;
    }

    public Plancher(Plancher plancher) {
        this.malus = plancher.malus;
    }

    /**
     * @param nbDeMalusAAjouter Nombre de malus que l'on va donner à un bot
     * @param joueur            Bot
     */
    public void ajouterAuPlancher(int nbDeMalusAAjouter, StatsParties stats, int joueur) {


        Tuile[] tuile = new Tuile[nbDeMalusAAjouter];
        for (Tuile t : tuile) {
            plancher.add(tuile);

            switch (plancher.lastIndexOf(tuile)) {
                case 0, 1 -> {
                    malus += 1;
                    stats.ajouterPointsPerdus(joueur, malus);
                }
                case 2, 3, 4 -> {
                    malus += 2;
                    stats.ajouterPointsPerdus(joueur, malus);
                }
                case 5, 6 -> {
                    malus += 3;
                    stats.ajouterPointsPerdus(joueur, malus);
                }
            }
        }

    }

    /**
     * @param nbDeMalusAAjouter Nombre de malus que l'on va donner à un bot
     * @param joueur            Bot
     */
    public void ajouterAuPlancherX2Centre(int nbDeMalusAAjouter, StatsParties stats, int joueur) {

        Tuile tuile[] = new Tuile[nbDeMalusAAjouter];
        for (Tuile t : tuile) {
            plancher.add(tuile);

            switch (plancher.lastIndexOf(tuile)) {
                case 0, 2, 4 -> {
                    malus += 1;
                   // stats.ajouterPointsPerdus(joueur, malus);
                }
                case 3, 5 -> {
                    malus += 2;
                 //   stats.ajouterPointsPerdus(joueur, malus);
                }
                case 6 -> {
                    malus += 3;
                  //  stats.ajouterPointsPerdus(joueur, malus);
                }
            }
        }

    }
    /**
     * @return Retourne le malus
     */
    public int getMalus() {
        return malus;
    }

    public void resetMalus() {
        malus = 0;
        plancher.clear();
    }
}
