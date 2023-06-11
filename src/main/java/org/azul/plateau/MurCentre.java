package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.util.Arrays;

public class MurCentre extends Mur {

    private static final Tuile[][] murReference = {
            {null, null, null, null, null},
            {null, Tuile.BLEUE, null, Tuile.ROUGE, null},
            {null, null, Tuile.BLANCHE, null, null},
            {null, Tuile.NOIRE, null, Tuile.JAUNE, null},
            {null, null, null, null, null}
    };

    public MurCentre() {
        super(murReference);
    }

    public MurCentre(MurCentre mur) {
        super(murReference);
        for (int i = 0; i < 5; i++) {
            this.mur[i] = Arrays.copyOf(mur.mur[i], mur.mur[i].length);
        }
    }

    /**
     * @param pos     positions sur lesquel placer la tuile
     * @param tuile couleur de la tuile
     * @return l'emplacement sur lequel poser la tuile (Si la couleur ne se trouve pas sur une colonne ou une ligne et si la couleur n'a pas de restriction)
     */
    public int ajouterAuMur(Tuile tuile, int... pos) {
        if (pos.length != 2) throw new IllegalArgumentException("erreur : length > 2");
        int l = pos[0];
        int c = pos[1];
        if (l > 5)
            throw new IllegalArgumentException("erreur : l>=5");
        for (; c < 5; c++) {
            if (mur[l][c] == tuile) break;
            if (mur[l][c] != null) continue;

            if (murReference[l][c] == tuile) {
                mur[l][c] = tuile; return c;
            }

            if (verifierColonne(c, tuile)) continue;

            if (murReference[l][c] == null) {
                mur[l][c] = tuile;
            }
            return c;

        }
        return -1;
    }

    private boolean verifierColonne(int c, Tuile tuile) {
        if (c >= 5)
            throw new IllegalArgumentException("erreur : c>=5");
        for (int l = 0; l < 5; l++) {

            if (mur[l][c] == tuile || murReference[l][c] == tuile)
                return true;
        }
        return false;
    }

    /**
     * Permet de savoir si on peut poser une tuile d'une couleur précise a un endroit précis dans le mur Centre
     * @param ligne La ligne
     * @param colonne La colonne
     * @param couleur La tuile
     * @return vrai ou faux
     */
    @Override
    public boolean peutPoserMur(int ligne, int colonne, Tuile couleur) {
        for (int i = 0; i < getTailleLigne(); i++) {
            if(murReference[i][colonne] == couleur) return false;
        }
        for (int i = 0; i < getTailleColonne(); i++) {
            if(murReference[ligne][i] == couleur) return false;
        }
        return true;
    }

    /**
     * Permet de connaitre si une tuile dans le mur Centre est égale à couleur ou à null
     *
     * @param ligne   La ligne
     * @param colonne La colonne
     * @param couleur La tuile
     * @return le nombre où il y a la tuile ou -1 si autre couleur ou -2 null
     */
    public int nbCouleurLigne(int ligne, int colonne, Tuile couleur) {

        if (murReference[ligne][colonne] == couleur) {
            return colonne;
        } else if (murReference[ligne][colonne] != null) {
            return -1;
        }

        return -2;
    }


    /**
     * Permet de connaitre si une tuile dans le mur Centre est égale à couleur ou à null
     *
     * @param ligne   La ligne
     * @param colonne La colonne
     * @param couleur La tuile
     * @return le nombre où il y a la tuile ou -1 si autre couleur ou -2 null
     */
    @Override
    public int nbCouleurLigneCentre(int ligne, int colonne, Tuile couleur) {

        if (murReference[ligne][colonne] == couleur) {
            return colonne;
        } else if (murReference[ligne][colonne] != null) {
            return -1;
        }

        return -2;
    }



}
