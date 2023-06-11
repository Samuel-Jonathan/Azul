package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.util.Arrays;

/**
 * @author Samuel Jonathan, Préaux Nicolas, Berges Jeremy
 * @version 1.3
 */
public class Motif {

    private Tuile[][] motif;

    public Motif() {
        motif = new Tuile[][]{
                {null},
                {null, null},
                {null, null, null},
                {null, null, null, null},
                {null, null, null, null, null}
        };
    }
    public Motif(Motif motif) {
        this.motif = new Tuile[][]{
                {null},
                {null, null},
                {null, null, null},
                {null, null, null, null},
                {null, null, null, null, null}
        };
        for (int i = 0; i < 5; i++) {
            this.motif[i] = Arrays.copyOf(motif.motif[i],motif.motif[i].length);
        }
    }

    public Tuile[][] getEmplacements() {
        return motif;
    }

    /**
     *
     * @param l Ligne du motif
     * @param c Colonne du motif
     * @return Retourne l'emplacement sélectionné
     */
    public Tuile getEmplacements(int l, int c) {
        return motif[l][c];
    }

    /**
     *
     * @param motif Motif d'un bot
     */
    public void setEmplacements(Tuile[][] motif) {
        this.motif = motif;
    }

    /**
     *
     * @param ligne Ligne du motif
     * @return Retourne true quand la ligne du motif est complète
     */
    public boolean estComplete(int ligne) {
        //Récupère la taille de chaque ligne du motif
        byte tailleLigne = (byte) Arrays.stream(motif[ligne]).count();
        byte notEmpty = 0;
        for (int i = 0; i < tailleLigne; i++) {
            //Vérifie si l'emplacement de la ligne est vide ou pas
            if (motif[ligne][i] != null) {
                notEmpty++;
            }
        }
        return notEmpty == tailleLigne;
    }

    /**
     * vider une ligne
     *
     * @param ligne la ligne à vider
     * @author Berges Jeremy
     */
    public void viderLigne(int ligne) {
        short tailleLigne = (short) Arrays.stream(motif[ligne]).count();
        for (int i = 0; i < tailleLigne; ++i) motif[ligne][i] = null;
    }
}
