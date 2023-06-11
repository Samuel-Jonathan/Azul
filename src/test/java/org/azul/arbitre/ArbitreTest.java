package org.azul.arbitre;

import org.azul.bot.BotRandom;
import org.azul.bot.IBot;
import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.JeuFabrique;
import org.azul.fabrique.Tuile;
import org.azul.bot.Bot;
import org.azul.plateau.Plateau;
import org.azul.visuel.StatsParties;
import org.azul.visuel.Vue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ArbitreTest {

    @Test
    public void testAjouterAuPlancher() {
        StatsParties stats = new StatsParties();
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.add(Tuile.BLEUE);
        tuiles.add(Tuile.BLEUE);
        tuiles.add(Tuile.BLEUE);
        Plateau plateau = new Plateau(1);

        for (int i = 0; i < tuiles.size(); i++) {
            tuiles.remove(0);
            plateau.getPlancher().ajouterAuPlancher(1, stats, 0);
        }
        assertTrue((plateau.getPlancher().getMalus() == 2));


    }

    @Test
    public void testAjouterAuMur() {
        Bot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        StatsParties stats = new StatsParties();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Tuile[][] tuiles = {
                {null},
                {null, null},
                {null, null, null},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE},
                {null, null, null, null, null}
        };
        Plateau plateauBot = arbitre.getPlateauJoueurs(bot.getId());
        plateauBot.getMotif().setEmplacements(tuiles);
        arbitre.ajouterAuMur(stats, jeuFabrique);
        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < plateauBot.getMotif().getEmplacements()[i].length; ++j)
                assertNull(plateauBot.getMotif().getEmplacements(i,j));

        assertEquals(1, arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore());
        assertNotNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(3, 3));
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(0, 3));

        Tuile[][] tuiles2 = {
                {null},
                {null, null},
                {null, null, null},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE},
                {null, null, null, null, null}
        };
        plateauBot.getMotif().setEmplacements(tuiles2);
        arbitre.ajouterAuMur(stats, jeuFabrique);
        assertEquals(1, arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore());
        assertEquals(6, arbitre.getPlateauJoueurs(bot.getId()).getPlancher().getMalus());
    }

    @Test
    public void testAjouterAuMurGris() {
        Bot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        StatsParties stats = new StatsParties();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Tuile[][] tuiles = {
                {null},
                {null, null},
                {null, null, null},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE}
        };
        arbitre.getPlateauJoueurs(bot.getId()).getMotif().setEmplacements(tuiles);
        arbitre.ajouterAuMurGris(stats, jeuFabrique);
        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements()[i].length; ++j)
                assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(i,j));

        assertEquals(arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore(), 2);
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(3, 0));
        assertNotNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(3, 3));
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(4, 1));
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(4, 0));
    }

    @Test
    public void testAjouterAuMurX2() {
        Bot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        StatsParties stats = new StatsParties();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Tuile[][] tuiles = {
                {null},
                {Tuile.ROUGE, Tuile.ROUGE},
                {null, null, null},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE},
                {Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE}
        };
        arbitre.getPlateauJoueurs(bot.getId()).getMotif().setEmplacements(tuiles);
        arbitre.ajouterAuMurX2(stats, jeuFabrique);
        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements()[i].length; ++j)
                assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(i,j));

        assertEquals(arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore(), 5);
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(1, 0));
        assertNotNull(arbitre.getPlateauJoueurs(bot.getId()).getMur().getTuileMur(3, 3));
    }

    @Test
    public void tuilesAdjacentesTest() {
        Bot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        plateau.getMur().ajouterAuMur(Tuile.ROUGE,0);
        plateau.getMur().ajouterAuMur(Tuile.NOIRE,0);
        plateau.getMur().ajouterAuMur(Tuile.BLANCHE,0);
        plateau.getMur().ajouterAuMur(Tuile.BLEUE,0);

        //Tuiles adjacentes horizontales
        arbitre.tuilesAdjacentes(plateau.getMur(), plateau.getScore(), 0, 3);

        assertEquals(2, arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore());

        plateau.getMur().ajouterAuMur(Tuile.JAUNE,1);
        plateau.getMur().ajouterAuMur(Tuile.BLEUE,2);
        plateau.getMur().ajouterAuMur(Tuile.BLANCHE,3);
        plateau.getMur().ajouterAuMur(Tuile.NOIRE, 4);

        //Tuiles adjacentes verticales
        arbitre.tuilesAdjacentes(plateau.getMur(), plateau.getScore(), 3, 2);

        assertEquals(6, arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore());


    }

    @Test
    public void testRecupererPossiblesGagnants() {
        IBot[] bots = new IBot[2];
        bots[0] = new Bot(0, 0);
        bots[1] = new Bot(1, 0);
        Arbitre arbitre = new Arbitre(1,bots);
        StatsParties stats = new StatsParties();
        // 0 > 1
        arbitre.getPlateauJoueurs(bots[0].getId()).getScore().ajouterAuScore(5);
        arbitre.getPlateauJoueurs(bots[1].getId()).getScore().ajouterAuScore(2);
        assertEquals(1, arbitre.recupererPossiblesGagnants(stats).size());
        assertEquals(bots[0], arbitre.recupererPossiblesGagnants(stats).get(0));

        // 0 = 1
        arbitre.getPlateauJoueurs(bots[1].getId()).getScore().ajouterAuScore(3);
        assertEquals(2, arbitre.recupererPossiblesGagnants(stats).size());
        for(int i = 0;i<2;++i) assertEquals(bots[i], arbitre.recupererPossiblesGagnants(stats).get(i));

        // 0 < 1
        arbitre.getPlateauJoueurs(bots[1].getId()).getScore().ajouterAuScore(10);
        assertEquals(1, arbitre.recupererPossiblesGagnants(stats).size());
        assertEquals(bots[1], arbitre.recupererPossiblesGagnants(stats).get(0));

        // malus
        arbitre.getPlateauJoueurs(bots[1].getId()).getPlancher().ajouterAuPlancher(7,stats, 0);
        arbitre.getPlateauJoueurs(bots[1].getId()).getScore().ajouterAuScore(-arbitre.getPlateauJoueurs(bots[1].getId()).getPlancher().getMalus());
        assertEquals(bots[0], arbitre.recupererPossiblesGagnants(stats).get(0));
    }

    @Test
    public void testfabriquesPleines() {
        Fabrique[] fabriques = new Fabrique[7];
        CentreTable centreTable = new CentreTable();
        Arbitre arbitre = new Arbitre(1);
        for (int i = 0; i < 7; ++i)
            fabriques[i] = new Fabrique(Tuile.getRandomColor(), Tuile.getRandomColor(), Tuile.getRandomColor(), Tuile.getRandomColor());
        assertTrue(arbitre.fabriquesPleinesEtCentreTablePlein(fabriques, centreTable));

        centreTable.addContenu(fabriques[0].getContenu());
        assertTrue(arbitre.fabriquesPleinesEtCentreTablePlein(fabriques, centreTable));

        for (int i = 0; i < 7; ++i) fabriques[i].getContenu().clear();
        assertTrue(arbitre.fabriquesPleinesEtCentreTablePlein(fabriques, centreTable));

        centreTable.getContenu().clear();
        assertFalse(arbitre.fabriquesPleinesEtCentreTablePlein(fabriques, centreTable));
    }

    @Test
    public void testVerifierLigneMotif() {
        IBot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        Tuile[][] tuiles = {
                {null},
                {null, null},
                {null, null, null},
                {null, null, Tuile.BLEUE, Tuile.BLEUE},
                {null, null, null, null, null}
        };
        arbitre.getPlateauJoueurs(bot.getId()).getMotif().setEmplacements(tuiles);

        assertTrue(arbitre.verifierLigneMotif(arbitre.getPlateauJoueurs(bot.getId()), 3, Tuile.BLEUE));
        assertFalse(arbitre.verifierLigneMotif(arbitre.getPlateauJoueurs(bot.getId()), 3, null));
        assertFalse(arbitre.verifierLigneMotif(arbitre.getPlateauJoueurs(bot.getId()), 3, Tuile.ROUGE));
        assertTrue(arbitre.verifierLigneMotif(arbitre.getPlateauJoueurs(bot.getId()), 4, Tuile.ROUGE));
    }

    @Test
    public void testVerifierPoseTuiles() {
        StatsParties stats = new StatsParties();
        IBot bot = new Bot(0,0);
        Arbitre arbitre = new Arbitre(1,bot);
        Vue vue = new Vue(0);
        JeuFabrique jeuFabrique = new JeuFabrique();
        bot.getTuiles().add(Tuile.ROUGE);
        bot.getTuiles().add(Tuile.ROUGE);
        bot.getTuiles().add(Tuile.ROUGE);
        arbitre.verifierPoseTuiles(1, bot, arbitre.getPlateauJoueurs(0), vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(0).getMotif().getEmplacements(1, 0));
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(0).getMotif().getEmplacements(1, 1));
        assertEquals(1, arbitre.getPlateauJoueurs(0).getPlancher().getMalus());
        assertTrue(bot.getTuiles().isEmpty());

        bot.getTuiles().add(Tuile.BLEUE);
        bot.getTuiles().add(Tuile.BLEUE);
        arbitre.verifierPoseTuiles(2, bot, arbitre.getPlateauJoueurs()[0], vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(0).getMotif().getEmplacements(2, 0));
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(0).getMotif().getEmplacements(2, 1));
        bot.getTuiles().add(Tuile.ROUGE);
        arbitre.verifierPoseTuiles(2, bot, arbitre.getPlateauJoueurs()[0], vue, 1, stats, jeuFabrique);
        assertEquals(2, arbitre.getPlateauJoueurs(0).getPlancher().getMalus());
        assertTrue(bot.getTuiles().isEmpty());
    }

    @Test
    public void aucuneTuilesTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        Arbitre arbitre = new Arbitre(1,new BotRandom(0));
        assertFalse(arbitre.aucuneTuiles(jeuFabrique));
        for (int i = 0;i<20;++i) {
            assertFalse(arbitre.aucuneTuiles(jeuFabrique));
            jeuFabrique.setNbTuiles("bla");
            jeuFabrique.setNbTuiles("n");
            jeuFabrique.setNbTuiles("j");
            jeuFabrique.setNbTuiles("r");
            jeuFabrique.setNbTuiles("ble");
        }
        assertTrue(arbitre.aucuneTuiles(jeuFabrique));
    }

    @Test
    public void testRecupererGagnantFinal(){
        IBot[] bots = new IBot[3];
        bots[0] = new Bot(0, 0);
        bots[1] = new Bot(1, 0);
        bots[2] = new Bot(2, 0);
        Arbitre arbitre = new Arbitre(1,bots);


        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 1);

        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 3);

        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 2);

        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 4);

        ArrayList<IBot> possiblesGagnants = new ArrayList<>();

        possiblesGagnants.add(bots[0]);
        possiblesGagnants.add(bots[1]);
        possiblesGagnants.add(bots[2]);

        ArrayList<IBot> gagnantsFinal;
        gagnantsFinal = arbitre.recupererGagnantFinal(possiblesGagnants);

        assertEquals(2,gagnantsFinal.get(0).getId());

    }

    @Test
    public void testRecupererGagnantFinalEgalite(){
        IBot[] bots = new IBot[4];
        bots[0] = new Bot(0, 0);
        bots[1] = new Bot(1, 0);
        bots[2] = new Bot(2, 0);
        bots[3] = new Bot(3, 0);
        Arbitre arbitre = new Arbitre(1,bots);


        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 1);
        arbitre.getPlateauJoueurs(bots[0].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 1);

        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 3);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 3);

        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 1);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 1);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 1);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 1);
        arbitre.getPlateauJoueurs(bots[1].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 1);

        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 2);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 2);

        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 4);
        arbitre.getPlateauJoueurs(bots[2].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 4);

        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 4);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 4);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 4);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 4);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 4);

        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.ROUGE, 1);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.BLEUE, 1);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.BLANCHE, 1);
        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.JAUNE, 1);

        arbitre.getPlateauJoueurs(bots[3].getId()).getMur().ajouterAuMur(Tuile.NOIRE, 1);

        ArrayList<IBot> possiblesGagnants = new ArrayList<>();

        possiblesGagnants.add(bots[0]);
        possiblesGagnants.add(bots[1]);
        possiblesGagnants.add(bots[2]);
        possiblesGagnants.add(bots[3]);

        ArrayList<IBot> gagnantsFinal;
        gagnantsFinal = arbitre.recupererGagnantFinal(possiblesGagnants);

        assertEquals(3, gagnantsFinal.size());

    }
}
