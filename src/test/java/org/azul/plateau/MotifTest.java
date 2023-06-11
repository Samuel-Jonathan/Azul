package org.azul.plateau;


import org.azul.fabrique.Tuile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MotifTest {

    @Test
    public void estCompleteTest() {

        Motif motif = new Motif();

        assertFalse(motif.estComplete(0));
        assertFalse(motif.estComplete(1));
        assertFalse(motif.estComplete(2));
        assertFalse(motif.estComplete(3));
        assertFalse(motif.estComplete(4));

    }

    @Test
    public void estCompleteLignePasEntierementVideTest() {
        Motif motif = new Motif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.BLEUE},
                {Tuile.ROUGE, null},
                {Tuile.NOIRE, null, Tuile.NOIRE},
                {null, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE},
                {Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, null, null}
        };
        motif.setEmplacements(emplacements);
        assertTrue(motif.estComplete(0));
        assertFalse(motif.estComplete(1));
        assertFalse(motif.estComplete(2));
        assertFalse(motif.estComplete(3));
        assertFalse(motif.estComplete(4));
    }

    @Test
    public void estCompleteLignePleineTest() {
        Motif motif = new Motif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.BLEUE},
                {Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.NOIRE, Tuile.NOIRE, Tuile.NOIRE},
                {Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE},
                {Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE}
        };
        motif.setEmplacements(emplacements);
        for (int i = 0; i < 4; i++)
            assertTrue(motif.estComplete(i));
    }

    @Test
    public void viderLigneTest() {
        Motif motif = new Motif();
        Tuile[][] emplacements = new Tuile[][]{
                {Tuile.BLEUE},
                {Tuile.ROUGE, Tuile.ROUGE},
                {Tuile.NOIRE, Tuile.NOIRE, Tuile.NOIRE},
                {Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE, Tuile.BLANCHE},
                {Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE, Tuile.JAUNE}
        };
        motif.setEmplacements(emplacements);
        motif.viderLigne(4);
        for (int c=0;c<5;++c){
            assertNull(motif.getEmplacements(4,c));
        }
    }
}
