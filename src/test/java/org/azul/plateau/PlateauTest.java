package org.azul.plateau;

import org.azul.fabrique.Tuile;
import org.azul.visuel.StatsParties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlateauTest {
    @Test
    public void cloneableTest() {
        StatsParties stats = new StatsParties();
        Plateau plateau = new Plateau(1);
        plateau.getScore().ajouterAuScore(2);
        plateau.getPlancher().ajouterAuPlancher(1,stats,0);
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.BLEUE},
                {Tuile.ROUGE, null},
                {Tuile.NOIRE, null, Tuile.NOIRE},
                {null, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE},
                {Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, null, null}
        };
        plateau.getMotif().setEmplacements(emplacements);
        for (int i = 0; i < 5; i++) {
            plateau.getMur().ajouterAuMur(Tuile.BLEUE,i);
        }

        Plateau plateauClone = new Plateau(plateau,1);
        assertNotEquals(plateau, plateauClone);
        assertNotEquals(plateau.getScore(), plateauClone.getScore());
        assertNotEquals(plateau.getPlancher(), plateauClone.getPlancher());
        assertNotEquals(plateau.getMotif(), plateauClone.getMotif());
        assertNotEquals(plateau.getMur(), plateauClone.getMur());

        plateau.getScore().ajouterAuScore(4);
        assertNotEquals(plateau.getScore().getScore(), plateauClone.getScore().getScore());
        
        plateau.getPlancher().ajouterAuPlancher(2,stats,0);
        assertNotEquals(plateau.getPlancher().getMalus(), plateauClone.getPlancher().getMalus());

        Tuile[][] emplacements2 = new Tuile[][]{
                {Tuile.BLEUE},
                {Tuile.ROUGE, null},
                {Tuile.NOIRE, Tuile.NOIRE, Tuile.NOIRE},
                {null, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE},
                {Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, null, null}
        };
        plateau.getMotif().setEmplacements(emplacements2);
        assertNotEquals(plateau.getMotif().getEmplacements(), plateauClone.getMotif().getEmplacements());
        
        int colonne = plateau.getMur().ajouterAuMur(Tuile.JAUNE,2);
        assertNotEquals(plateau.getMur().getTuileMur(2,colonne), plateauClone.getMur().getTuileMur(2,colonne));
    }
}
