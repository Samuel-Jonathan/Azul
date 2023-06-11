package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.util.Arrays;

public class MurX2 extends Mur {

    public static final Tuile[][] murReference = {
            {null, null, null, Tuile.NOIRE, null},
            {Tuile.BLANCHE, null, null, null, null},
            {null, null, Tuile.JAUNE, null, null},
            {null, null, null, null, Tuile.BLEUE},
            {null, Tuile.ROUGE, null, null, null}
    };

    public MurX2() {
        super(murReference);
    }

    public MurX2(MurX2 mur) {
        super(murReference);
        for (int i = 0; i < 5; i++) {
            this.mur[i] = Arrays.copyOf(mur.mur[i], mur.mur[i].length);
        }
    }

  /*  /**
     * @param pos     position sur laquelle placer la tuile
     * @param tuile couleur de la tuile
     * @return l'emplacement sur lequel poser la tuile (Si la couleur ne se trouve pas sur une colonne ou une ligne et si la couleur n'a pas de restriction)

    public int ajouterAuMur(Tuile tuile, int ... pos) {
        if (pos.length != 2) throw new IllegalArgumentException("erreur : length > 2");
        int l = pos[0];
        int c = pos[1];
        if (l > 5) throw new IllegalArgumentException("erreur : l > 5");
        if (c > 5) throw new IllegalArgumentException("erreur : c > 5");
        //il doit check LA ligne et LA colonne
        for (int i = 0; i < 5; ++i) {
            if (mur[i][c] == tuile) return -1;
            if (murReference[i][c] == tuile && i != l) return -1;

            if (mur[l][i] == tuile) return -1;
            if (murReference[l][i] == tuile && i != c) return -1;
        }
        mur[l][c] = tuile;
        return c;
    }*/

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
            throw new IllegalArgumentException("erreur : l>=5");
        for (int l = 0; l < 5; l++) {
            if (mur[l][c] == tuile)
                return true;
        }
        return false;
    }

    @Override
    public boolean peutPoserMur(int ligne, int colonne, Tuile couleur) {
        for (int i = 0; i < getTailleLigne(); i++) {
            if (murReference[i][colonne] == couleur) return false;
        }
        for (int i = 0; i < getTailleColonne(); i++) {
            if (murReference[ligne][i] == couleur) return false;
        }
        return true;
    }

    public boolean verifierMurX2(int i, int k, Tuile couleur) {
        return murReference[i][k] == couleur;
    }

}
