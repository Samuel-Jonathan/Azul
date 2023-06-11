package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.lang.IllegalArgumentException;
import java.util.Arrays;

/**
 * @author Berges Jeremy
 * @version 1.1
 */
public class MurClassique extends Mur {

    private static final Tuile[][] murReference = {
            {Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE},
            {Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE},
            {Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE, Tuile.ROUGE},
            {Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE, Tuile.JAUNE},
            {Tuile.JAUNE, Tuile.ROUGE, Tuile.NOIRE, Tuile.BLANCHE, Tuile.BLEUE}
    };
    public MurClassique() {
        super(murReference);
    }

    public MurClassique(MurClassique mur) {
        super(murReference);
        for (int i = 0; i < 5; i++) {
            this.mur[i] = Arrays.copyOf(mur.mur[i], mur.mur[i].length);
        }
    }

    /**
     * ajoute une tuile au mur à une position donnée
     *
     * @param pos
     * @param tuile la tuile à ajouter au mur
     * @return true s'il a pu ajouter la tuile sinon false
     */
    public int ajouterAuMur(Tuile tuile, int... pos) {
        int l = pos[0];
        if (l > 5) throw new IllegalArgumentException("erreur : l > 5");
        for (int c = 0; c < 5; ++c) {
            if (murReference[l][c] != tuile) continue;
            if (mur[l][c] != null) continue;
            mur[l][c] = tuile;
            return c;
        }
        return -1;
    }

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

}
