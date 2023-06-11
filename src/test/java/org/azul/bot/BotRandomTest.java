package org.azul.bot;

import org.azul.arbitre.Arbitre;
import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.Tuile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BotRandomTest {

    @Mock
    SecureRandom secureRandomMock;
    @Test
    public void piocherFabriqueTest() {
        CentreTable centreTable = new CentreTable();
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE);
        when(secureRandomMock.Random(anyInt())).thenReturn(0);

        BotRandom botRandom = new BotRandom(0, secureRandomMock);
        Object[] coup = botRandom.jouer(fabriques, centreTable);

        assertEquals(fabriques[0],((Object[]) coup[0])[0]);
        assertEquals(0,((Object[]) coup[0])[1]);
        assertEquals(0,((Object[]) coup[0])[2]);
        assertFalse((boolean)coup[1]);
    }

    @Test
    public void piocherCentreTableTest() {
        CentreTable centreTable = new CentreTable();
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(null,null,null,null);
        ArrayList<Tuile> tuiles = new Fabrique(Tuile.ROUGE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE).getContenu();
        centreTable.addContenu(tuiles);
        when(secureRandomMock.Random(anyInt())).thenReturn(0);
        BotRandom botRandom = new BotRandom(0, secureRandomMock);
        Object[] coup = botRandom.jouer(fabriques, centreTable);
        assertEquals(centreTable,((Object[]) coup[0])[0]);
        assertEquals(0,((Object[]) coup[0])[1]);
        assertEquals(0,((Object[]) coup[0])[2]);
        assertTrue((boolean)coup[1]);

    }

    @Test
    public void deposerTuilesTest() {
        CentreTable centreTable = new CentreTable();
        BotRandom botRandom = new BotRandom(0);
        Arbitre arbitre = new Arbitre(1,botRandom);
        Fabrique[] fabriques = new Fabrique[1];
        fabriques[0] = new Fabrique(Tuile.ROUGE, Tuile.BLEUE, Tuile.BLEUE, Tuile.BLEUE);
        botRandom.jouer(fabriques, centreTable);
        Tuile[][] tuiles = arbitre.getPlateauJoueurs(botRandom.getId()).getMotif().getEmplacements();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i; j++) {
                if(tuiles[i][j] == Tuile.BLEUE){
                    if (i > 2) {
                        assertNotEquals(Tuile.BLEUE, tuiles[i][i]);
                    } else {
                        assertEquals(Tuile.BLEUE, tuiles[i][i]);
                    }
                }
            }
        }
    }



}
