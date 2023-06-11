package org.azul.bot;

import org.azul.arbitre.Arbitre;
import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.JeuFabrique;
import org.azul.fabrique.Tuile;
import org.azul.plateau.*;
import org.azul.visuel.StatsParties;
import org.azul.visuel.Vue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lucas Puissant--Garambois
 */
public class BotTest {

    //Test pour voir qu'il ne prend pas en compte les tableaux vides
    @Test
    public void TestTableauVide() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();

        Fabrique[] fabriques = new Fabrique[7];
        for (int i = 0; i < fabriques.length; i++) {
            fabriques[i] = new Fabrique(Tuile.getRandomColor(), Tuile.getRandomColor(), Tuile.getRandomColor(), Tuile.getRandomColor());
        }
        for (int i = 0; i < fabriques.length-1; i++) {
            fabriques[i].getContenu().clear();
        }
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertTrue(fabriques[6].getContenu().isEmpty());
    }

    //Test s'il prend le plus de tuile
    @Test
    public void prendreMaxTuileTest() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Fabrique[] fabriques = new Fabrique[2];
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.JAUNE);
        fabriques[1] = new Fabrique(Tuile.ROUGE,Tuile.BLEUE,Tuile.BLEUE,Tuile.BLEUE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        Tuile[][] tuile = arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements();
        assertEquals(Tuile.BLEUE, tuile[2][2]);
    }

    //Test s'il a bien fini une ligne plutôt qu'il en commence une autre
    @Test
    public void finalisationLigneTest() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {null},
                {null, null},
                {null, null, null},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, null},
                {null, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
    }

    //Test s'il prend bien le moin possible dans une fabrique pour ne pas avoir de malus
    @Test
    public void prendreMoinPossibleTest() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.ROUGE, null, null},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, null},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE}
        };
        motif.setEmplacements(emplacements);
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(2, 2));
    }

    //Test s'il pioche bien au centreTable et avoir un plus petit malus que s'il pioche dans la fabrique
    @Test
    public void testCentreTable(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, null},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE}
        };
        motif.setEmplacements(emplacements);
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.ROUGE,Tuile.JAUNE);
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.add(Tuile.ROUGE);
        centreTable.addContenu(tuiles);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(fabriques[0].getContenu().get(0), Tuile.ROUGE);
    }

    //Test s'il met bien une tuile rouge au bon endroit et pas une autre tuile ou alors rien
    @Test
    public void testSuisBienLesRegles(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, null},
                {Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE, Tuile.ROUGE}
        };
        motif.setEmplacements(emplacements);
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
    }

    //Test la partie fabrique en null
    @Test
    public void testMetToutAuBonneEndroit(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {null},
                {null, null},
                {null, null, null},
                {null, null, null, null},
                {null, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(0, 0));
        fabriques[0] = new Fabrique(Tuile.JAUNE,Tuile.JAUNE,Tuile.NOIRE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.JAUNE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(1, 1));
        fabriques[0] = new Fabrique(Tuile.BLEUE,Tuile.BLEUE,Tuile.BLEUE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(2, 2));
        fabriques[0] = new Fabrique(Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLANCHE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
        ArrayList<Tuile> tuile = new ArrayList<>();
        tuile.add(Tuile.NOIRE);
        centreTable.addContenu(tuile);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.NOIRE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(4, 4));
    }

    //Test la partie fabrique avec couleur
    @Test
    public void testMetToutAuBonneEndroitAvecCouleur(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {null},
                {Tuile.JAUNE, null},
                {Tuile.BLEUE, null, null},
                {Tuile.BLANCHE, null, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.JAUNE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.JAUNE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(1, 1));
        fabriques[0] = new Fabrique(Tuile.BLEUE,Tuile.BLEUE,Tuile.NOIRE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(2, 2));
        fabriques[0] = new Fabrique(Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.NOIRE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(4, 4));
        fabriques[0] = new Fabrique(Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLEUE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLANCHE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.BLANCHE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(0, 0));
    }

    //Test la partie CentreTable en null
    @Test
    public void testMetToutAuBonneEndroitCT(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {null},
                {null, null},
                {null, null, null},
                {null, null, null, null},
                {null, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        fabriques[0].getContenu().clear();
        List<Tuile> list = Arrays.asList(Tuile.ROUGE,Tuile.JAUNE,Tuile.JAUNE,Tuile.BLEUE,Tuile.BLEUE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE);
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.addAll(list);
        centreTable.addContenu(tuiles);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(Tuile.ROUGE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(0, 0));
        assertEquals(Tuile.JAUNE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(1, 1));
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(2, 2));
        assertEquals(Tuile.BLANCHE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
        assertEquals(Tuile.NOIRE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(4, 4));
    }

    //Test la partie CentreTable avec couleur
    @Test
    public void testMetToutAuBonneEndroitCTAvecCouleur(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {null},
                {Tuile.JAUNE, null},
                {Tuile.BLEUE, null, null},
                {Tuile.BLANCHE, null, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        fabriques[0].getContenu().clear();
        List<Tuile> list = Arrays.asList(Tuile.JAUNE,Tuile.BLEUE,Tuile.BLEUE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.BLANCHE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE);
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.addAll(list);
        centreTable.addContenu(tuiles);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertNull(arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(0, 0));
        assertEquals(Tuile.JAUNE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(1, 1));
        assertEquals(Tuile.BLEUE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(2, 2));
        assertEquals(Tuile.BLANCHE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(3, 3));
        assertEquals(Tuile.NOIRE, arbitre.getPlateauJoueurs(bot.getId()).getMotif().getEmplacements(4, 4));
    }

    //Test si le bot prend bien le minimum de malus possible dans la fabrique lorsque la ligne motif est pleine
    @Test
    public void TestLigneMotifPleinPrendreMinimum(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {Tuile.JAUNE},
                {Tuile.JAUNE, null},
                {Tuile.JAUNE, null, null},
                {Tuile.JAUNE, null, null, null},
                {Tuile.JAUNE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue,1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 1);
        plateau.getPlancher().resetMalus();
        centreTable.getContenu().clear();
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.NOIRE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 2);
        plateau.getPlancher().resetMalus();
        centreTable.getContenu().clear();
        fabriques[0] = new Fabrique(Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE,Tuile.NOIRE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 6);
    }

    //Test si le bot prend bien le minimum de malus possible dans centreTable lorsque la ligne motif est pleine
    @Test
    public void TestLigneMotifPleinPrendreMinimumCT(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();Tuile[][] emplacements = new Tuile[][]{
                {Tuile.JAUNE},
                {Tuile.JAUNE, null},
                {Tuile.JAUNE, null, null},
                {Tuile.JAUNE, null, null, null},
                {Tuile.JAUNE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        fabriques[0].getContenu().clear();
        List<Tuile> list = Arrays.asList(Tuile.NOIRE,Tuile.ROUGE,Tuile.ROUGE,Tuile.BLEUE,Tuile.BLEUE,Tuile.BLEUE);
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.addAll(list);
        centreTable.addContenu(tuiles);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 2);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 6);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getPlancher().getMalus(), 14);

    }

    //Test si le bot ne prend pas au centre si il y a le malus
    @Test
    public void TestMalusCentreTable(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.ROUGE},
                {Tuile.ROUGE, null},
                {Tuile.ROUGE, null, null},
                {Tuile.JAUNE, null, null, null},
                {Tuile.JAUNE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        List<Tuile> list = Arrays.asList(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.addAll(list);
        centreTable.addContenu(tuiles);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getMotif().getEmplacements(2,2), Tuile.ROUGE);

    }

    //Test si le bot pioche bien au bon endroit en utilisant le mur
    @Test
    public void TestPrendreMur(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.NOIRE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.ROUGE},
                {Tuile.ROUGE, null},
                {Tuile.ROUGE, null, null},
                {Tuile.ROUGE, Tuile.ROUGE, null, null},
                {Tuile.ROUGE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.ROUGE,Tuile.ROUGE,Tuile.BLANCHE,Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getMotif().getEmplacements(3,3), Tuile.ROUGE);

    }

    //Test si le bot pioche bien à l'endroit qui lui rapporte le plus de point en utilisant le mur
    @Test
    public void TestPrendreAvecPlusieursMur(){
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.BLANCHE, 2);
        mur.ajouterAuMur(Tuile.BLANCHE, 3);
        mur.ajouterAuMur(Tuile.ROUGE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.NOIRE, null},
                {Tuile.NOIRE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getMotif().getEmplacements(3,3), Tuile.NOIRE);
    }

    //Test si le bot prioritise bien l'ajout dans le mur pour faire une ligne vertical
    @Test
    public void TestPrioritiseLigneVertical() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 0);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(1,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.ROUGE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.NOIRE, null},
                {Tuile.NOIRE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getMotif().getEmplacements(2,2), Tuile.NOIRE);
    }

    //Test si le bot ne termine pas la partie si les autres joueurs ont un score plus élever que lui
    @Test
    public void TestPasFinirPartiSiAutreJoueurAPlusDePoint() {
        StatsParties stats = new StatsParties();

        Bot bot = new Bot(0, 0);
        Bot bot2 = new Bot(1, 0);
        Bot[] bots = new Bot[2];
        bots[0] = bot;
        bots[1] = bot2;
        Arbitre arbitre = new Arbitre(1,bots);
        arbitre.getPlateauJoueurs(bot2.getId()).getScore().ajouterAuScore(100);
        Vue vue = new Vue(0);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.ROUGE, 3);
        mur.ajouterAuMur(Tuile.BLANCHE, 3);
        mur.ajouterAuMur(Tuile.BLEUE, 3);
        mur.ajouterAuMur(Tuile.JAUNE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.NOIRE, null},
                {Tuile.NOIRE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(plateau.getMotif().getEmplacements(2,2), Tuile.NOIRE);
    }

    //Test si le bot joue correctement dans le mur gris
    @Test
    public void TestMurGris() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 2);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(2,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.ROUGE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.NOIRE, null},
                {Tuile.NOIRE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.NOIRE, plateau.getMotif().getEmplacements(2,2));
        assertEquals(4, bot.getColonneMurGris());
    }

    //Test si le bot pose bien au bon endroit dans le mur gris
    @Test
    public void TestPoseBienMurGris() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 2);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(2,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur(Tuile.NOIRE, 3);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.NOIRE, null},
                {Tuile.NOIRE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.NOIRE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.JAUNE);
        
        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.NOIRE, plateau.getMotif().getEmplacements(2,2));
        assertEquals(0, bot.getColonneMurGris());
    }

    //Test si le bot joue correctement dans le Plateau Centre
    @Test
    public void TestPlateauCentre() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 4);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(4,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur( Tuile.BLANCHE,2,1);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.BLANCHE, null},
                {Tuile.BLANCHE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.ROUGE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.JAUNE);

        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.BLANCHE, plateau.getMotif().getEmplacements(1,1));
    }

    //Test si le bot joue correctement dans le Plateau x2
    @Test
    public void TestPlateauX2() {
        StatsParties stats = new StatsParties();
        Bot bot = new Bot(0, 3);
        Vue vue = new Vue(0);
        Arbitre arbitre = new Arbitre(3,bot);
        Fabrique[] fabriques = new Fabrique[1];
        CentreTable centreTable = new CentreTable();
        JeuFabrique jeuFabrique = new JeuFabrique();
        Plateau plateau = arbitre.getPlateauJoueurs(bot.getId());
        Mur mur = plateau.getMur();
        mur.ajouterAuMur( Tuile.ROUGE,0,0);
        mur.ajouterAuMur( Tuile.NOIRE,2,0);
        Motif motif = plateau.getMotif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.NOIRE},
                {Tuile.BLANCHE, null},
                {Tuile.BLANCHE, null, null},
                {Tuile.NOIRE, Tuile.NOIRE, null, null},
                {Tuile.ROUGE, null, null, null, null}
        };
        motif.setEmplacements(emplacements);
        fabriques[0] = new Fabrique(Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.JAUNE);

        arbitre.verifierCoupBot(fabriques, centreTable, vue, 1, stats, jeuFabrique);
        assertEquals(Tuile.BLANCHE, plateau.getMotif().getEmplacements(1,1));
    }
}