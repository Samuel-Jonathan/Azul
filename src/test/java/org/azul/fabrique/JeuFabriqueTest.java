package org.azul.fabrique;

import org.azul.Jeu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JeuFabriqueTest {

    @Test
    public void generationFabriquesTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        assertEquals(5, jeuFabrique.generationFabriques(2).length);
        assertEquals(7, jeuFabrique.generationFabriques(3).length);
        assertEquals(9, jeuFabrique.generationFabriques(4).length);
    }

    @Test
    public void verificationCouleurEtNombreTuilesTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        assertTrue(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.BLANCHE));
        for (int i = 0; i < 19; i++)
            jeuFabrique.setNbTuiles("bla");
        assertFalse(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.BLANCHE));
        assertTrue(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.ROUGE));
        assertTrue(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.BLEUE));
        assertTrue(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.NOIRE));
        assertTrue(jeuFabrique.verificationCouleurEtNombreTuiles(Tuile.JAUNE));
    }

    @Test
    public void ajouterCouvercleTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        jeuFabrique.ajouterCouvercle(Tuile.BLANCHE, 3);
        jeuFabrique.ajouterCouvercle(Tuile.NOIRE, 2);
        jeuFabrique.ajouterCouvercle(Tuile.JAUNE, 4);
        jeuFabrique.ajouterCouvercle(Tuile.ROUGE, 1);
        jeuFabrique.ajouterCouvercle(Tuile.BLEUE, 5);
        assertEquals(3, jeuFabrique.getCouvercle()[0]);
        assertEquals(2, jeuFabrique.getCouvercle()[1]);
        assertEquals(4, jeuFabrique.getCouvercle()[2]);
        assertEquals(1, jeuFabrique.getCouvercle()[3]);
        assertEquals(5, jeuFabrique.getCouvercle()[4]);
    }

    @Test
    public void recyclerTuilesTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        jeuFabrique.ajouterCouvercle(Tuile.BLANCHE, 3);
        jeuFabrique.ajouterCouvercle(Tuile.NOIRE, 2);
        jeuFabrique.ajouterCouvercle(Tuile.JAUNE, 4);
        jeuFabrique.ajouterCouvercle(Tuile.ROUGE, 1);
        jeuFabrique.ajouterCouvercle(Tuile.BLEUE, 5);
        jeuFabrique.recyclerTuiles();
        assertEquals(23, jeuFabrique.getNbTuiles()[0]);
        assertEquals(22, jeuFabrique.getNbTuiles()[1]);
        assertEquals(24, jeuFabrique.getNbTuiles()[2]);
        assertEquals(21, jeuFabrique.getNbTuiles()[3]);
        assertEquals(25, jeuFabrique.getNbTuiles()[4]);
    }

    @Test
    public void reinitialiserCouvercleTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        jeuFabrique.ajouterCouvercle(Tuile.BLANCHE, 3);
        jeuFabrique.ajouterCouvercle(Tuile.NOIRE, 2);
        jeuFabrique.ajouterCouvercle(Tuile.JAUNE, 4);
        jeuFabrique.ajouterCouvercle(Tuile.ROUGE, 1);
        jeuFabrique.ajouterCouvercle(Tuile.BLEUE, 5);
        jeuFabrique.recyclerTuiles();
        assertEquals(0, jeuFabrique.getCouvercle()[0]);
        assertEquals(0, jeuFabrique.getCouvercle()[1]);
        assertEquals(0, jeuFabrique.getCouvercle()[2]);
        assertEquals(0, jeuFabrique.getCouvercle()[3]);
        assertEquals(0, jeuFabrique.getCouvercle()[4]);
    }

    @Test
    public void setNbTuilesTest() {
        JeuFabrique jeuFabrique = new JeuFabrique();
        assertEquals(20, jeuFabrique.getNbTuiles()[0]);
        assertEquals(20, jeuFabrique.getNbTuiles()[1]);
        assertEquals(20, jeuFabrique.getNbTuiles()[2]);
        assertEquals(20, jeuFabrique.getNbTuiles()[3]);
        assertEquals(20, jeuFabrique.getNbTuiles()[4]);
        jeuFabrique.setNbTuiles("bla");
        assertEquals(19, jeuFabrique.getNbTuiles()[0]);
        jeuFabrique.setNbTuiles("n");
        assertEquals(19, jeuFabrique.getNbTuiles()[1]);
        jeuFabrique.setNbTuiles("j");
        assertEquals(19, jeuFabrique.getNbTuiles()[2]);
        jeuFabrique.setNbTuiles("r");
        assertEquals(19, jeuFabrique.getNbTuiles()[3]);
        jeuFabrique.setNbTuiles("ble");
        assertEquals(19, jeuFabrique.getNbTuiles()[4]);
        jeuFabrique.setNbTuiles("ble");
        assertEquals(18, jeuFabrique.getNbTuiles()[4]);
    }
}
