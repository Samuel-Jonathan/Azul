package org.azul.bot;

import java.util.Random;

public class SecureRandom {
    private Random rand;
    /**
     * Crée une nouvelle instance de Random
     */
    public SecureRandom() {
        this.rand = new Random();
    }
    /**
     * @return un nombre int aléatoire
     */
    public int Random(int nbr) {

        return rand.nextInt(nbr);
    }
}
