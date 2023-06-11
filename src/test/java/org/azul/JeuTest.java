package org.azul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JeuTest {

    @Test
    public void creationBotsTest() {
        Jeu main = new Jeu();
        assertEquals(2, main.creationBots(2, 0).length);
        assertEquals(3, main.creationBots(3, 0).length);
        assertEquals(4, main.creationBots(4, 0).length);
    }

}
