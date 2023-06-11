package org.azul.plateau;

import org.azul.arbitre.Arbitre;
import org.azul.bot.Bot;
import org.azul.fabrique.Tuile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {
    @Test
    public void testadd() {
        Score score = new Score();
        for (int v = 0; v < 10; ++v) {
            score.ajouterAuScore(v);
            assertEquals(score.getScore(), v);
            score.ajouterAuScore(-v);
            assertEquals(score.getScore(), 0);
        }
    }

    @Test
    public void testAddCumul() {
        Score score = new Score();
        for (int v = 0; v < 10; ++v) {
            score.ajouterAuScore(v);
            assertEquals(score.getScore(), v * (v + 1) / 2);
        }
    }

    @Test
    public void testAddBelowZero() {
        Score score = new Score();
        score.ajouterAuScore(25);
        score.ajouterAuScore(-75);
        assertEquals(score.getScore(), 0);
    }

    @Test
    public void testBonus(){
        Bot bot = new Bot(0, 0);
        Arbitre arbitre = new Arbitre(1,bot);
        Mur murJoueur = arbitre.getPlateauJoueurs(bot.getId()).getMur();
        Score scoreJoueur = arbitre.getPlateauJoueurs(bot.getId()).getScore();
        Tuile[][] murReference = {
                {Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE},
                {Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE},
                {Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE},
                {Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE},
                {Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE}
        };
    for(int i = 0; i < 5 ; i++){
        for(int j = 0; j < 5; j++){
            murJoueur.ajouterAuMur(murReference[i][j], i);
            arbitre.tuilesAdjacentes(murJoueur, scoreJoueur, i, j);
        }
    }
        assertEquals(arbitre.getPlateauJoueurs(bot.getId()).getScore().getScore() ,100);
    }

    @Test
    public void testAjouterAuScoreFinDePartie() {
        Score score = new Score();
        score.ajouterAuScoreFinDePartie(0,0,0);
        assertEquals(0,score.getScore());

        score.ajouterAuScoreFinDePartie(2,0,0);
        assertEquals(4,score.getScore());

        score.ajouterAuScoreFinDePartie(0,3,0);
        assertEquals(25,score.getScore());

        score.ajouterAuScoreFinDePartie(0,0,4);
        assertEquals(65,score.getScore());

        score.ajouterAuScoreFinDePartie(6,7,8);
        assertEquals(206,score.getScore());
    }

    @Test
    public void testAjouterAuScoreCentreFinDePartie() {
        Score score = new Score();
        score.ajouterAuScoreCentreFinDePartie(0,0,0);
        assertEquals(0,score.getScore());

        score.ajouterAuScoreCentreFinDePartie(2,0,0);
        assertEquals(6,score.getScore());

        score.ajouterAuScoreCentreFinDePartie(0,3,0);
        assertEquals(36,score.getScore());

        score.ajouterAuScoreCentreFinDePartie(0,0,4);
        assertEquals(84,score.getScore());

        score.ajouterAuScoreCentreFinDePartie(6,7,8);
        assertEquals(268,score.getScore());
    }

}
