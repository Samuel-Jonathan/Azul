package org.azul.fabrique;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FabriqueTest {

    @Test
    public void testGetTuiles(){
        Fabrique fabrique = new Fabrique(Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE);
        ArrayList<Tuile> tuiles = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            tuiles.add(Tuile.BLEUE);
        }

        assertEquals(tuiles,fabrique.getContenu());
    }

}
