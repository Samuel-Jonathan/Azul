package org.azul.plateau;

import org.azul.fabrique.Tuile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MurTest {

    @Test
    public void ajouterAuMurTest() {
        Mur mur = new MurClassique();
        int l = 1;
        mur.ajouterAuMur(Tuile.BLEUE,l);
        assertEquals(mur.getTuileMur(l, 1), Tuile.BLEUE);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == l && j == 1) {
                    assertEquals(-1, mur.ajouterAuMur(Tuile.BLEUE,l));
                    continue;
                }
                assertNull(mur.getTuileMur(i, j));
            }
        }
    }

    @Test
    public void ajouterAuMurGrisTest() {
        Mur mur = new MurGris();
        int l = 1;
        mur.ajouterAuMur(Tuile.BLEUE,l);
        assertEquals(mur.getTuileMur(l, 4), Tuile.BLEUE);
    }

    @Test
    public void ajouterAuMurX2Test() {
        Mur mur = new MurX2();
        int l = 1;
        int c= 0;
        assertEquals(mur.ajouterAuMur(Tuile.BLANCHE,l,c),0);
        mur.ajouterAuMur(Tuile.BLANCHE,l,c);
        for (int i = 0; i < 4; i++) {
            mur.ajouterAuMur(Tuile.getRandomColor(),l,c);
            if(mur.getTuileMur(l,i) != null) break;
            assertNotEquals(mur.getTuileMur(l, i), mur.getTuileMur(l, (i + 1)));
        }
    }

    @Test
    public void verifyCol() {
        Mur mur = new MurGris();
        int l = 1;
        mur.ajouterAuMur(Tuile.BLEUE,l);
        assertEquals(mur.getTuileMur(l, 4), Tuile.BLEUE);
    }

    @Test
    public void ajouterAuMurCentreTest() {
        Mur mur = new MurCentre();
        int l = 1;
        int c= 0;
        assertEquals(mur.ajouterAuMur(Tuile.BLANCHE,l,c),0);
        mur.ajouterAuMur(Tuile.BLANCHE,l,c);
        for (int i = 0; i < 4; i++) {
            mur.ajouterAuMur(Tuile.getRandomColor(),l,c);
            if(mur.getTuileMur(l,i) != null) break;
            assertNotEquals(mur.getTuileMur(l, i), mur.getTuileMur(l, (i + 1)));
        }
    }

    @Test
    public void ajouterAuMurLigneAuDelaMax() {
        Mur mur = new MurClassique();
        int l = 6;
        Exception exception = assertThrows(Exception.class, () -> mur.ajouterAuMur(Tuile.JAUNE,l));
        assertEquals("erreur : l > 5", exception.getMessage());
    }

}
