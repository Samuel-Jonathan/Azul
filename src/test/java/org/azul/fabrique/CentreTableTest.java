package org.azul.fabrique;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CentreTableTest {
    @Test
    public void addContenuTest() {
        CentreTable centreTable = new CentreTable();
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.add(Tuile.ROUGE);
        tuiles.add(Tuile.BLANCHE);
        tuiles.add(Tuile.BLEUE);
        centreTable.addContenu(tuiles);

        assertEquals(Tuile.ROUGE,centreTable.getContenu().get(0));
        assertEquals(Tuile.BLANCHE,centreTable.getContenu().get(1));
        assertEquals(Tuile.BLEUE,centreTable.getContenu().get(2));
    }

    @Test
    public void toStringTest() {
        CentreTable centreTable = new CentreTable();
        ArrayList<Tuile> tuiles = new ArrayList<>();
        tuiles.add(Tuile.ROUGE);
        tuiles.add(Tuile.BLANCHE);
        tuiles.add(Tuile.BLEUE);
        centreTable.addContenu(tuiles);
        assertEquals("[ROUGE, BLANCHE, BLEUE]",centreTable.toString());
    }

    @Test
    public void resetMalusTest() {
        CentreTable centreTable = new CentreTable();
        assertFalse(centreTable.getMalusCentreTable());
        centreTable.setMalusCentreTable(true);
        assertTrue(centreTable.getMalusCentreTable());
        centreTable.resetMalusCentreTable();
        assertFalse(centreTable.getMalusCentreTable());
    }
}
