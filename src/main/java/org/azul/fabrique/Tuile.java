package org.azul.fabrique;

import java.util.Random;

/**
 * @author Arthur Dauphin
 */
public enum Tuile {
    BLEUE("\u001B[34m"), ROUGE("\u001B[31m"), NOIRE("\u001B[47m\u001B[30m"), BLANCHE("\u001B[40m\u001B[37m"), JAUNE("\u001B[33m");
    String s;

    /**
     * @param s Code couleur des tuiles
     */
    Tuile(String s) {
        this.s = s;
    }
    /**
     * @return le code couleur de la tuile
     */
    public String getCode() {
        return s;
    }
    /**
     * Permet de choisir la couleur d'une tuile aléatoirement
     * @return le code couleur de la tuile
     */
    public static Tuile getRandomColor() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public String toString() {
        return this.name();
    }
}
