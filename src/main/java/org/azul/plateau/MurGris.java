package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.util.Arrays;

public class MurGris extends Mur {

    public MurGris() {
        super(new Tuile[5][5]);
    }

    public MurGris(MurGris mur) {
        super(new Tuile[5][5]);
        for (int i = 0; i < 5; i++) {
            this.mur[i] = Arrays.copyOf(mur.mur[i], mur.mur[i].length);
        }
    }

    /**
     * @param pos   position sur laquelle placer la tuile
     * @param tuile couleur de la tuile
     * @return l'emplacement sur lequel poser la tuile (Si la couleur ne se trouve pas sur une colonne ou une ligne et si la couleur n'a pas de restriction)
     */
//    public int ajouterAuMur(Tuile tuile, int ... pos) {
//        if (pos.length != 1) throw new IllegalArgumentException("erreur : length > 1");
//        int l = pos[0];
//        int c;
//        //int c = pos[1];
//        if (l > 5) throw new IllegalArgumentException("erreur : l > 5");
//        //if (c > 5) throw new IllegalArgumentException("erreur : c > 5");
//        //il doit check LA ligne et LA colonne
//        for (int i = 0; i < 5; ++i) {
//           // if (mur[i][c] == tuile) return -1;
//            if (mur[l][i] == tuile) return -1;
//        }
//        mur[l][c] = tuile;
//        return c;
//    }
    public int ajouterAuMur(Tuile tuile, int... pos) {
        if (pos.length != 1) throw new IllegalArgumentException("erreur : length > 1");
        int l = pos[0];
        int c = -1;
        if (l > 5) throw new IllegalArgumentException("erreur : l > 5");
        for (int i = 0; i < 5; ++i) {
            if (mur[l][i] == tuile) return -1;
            if (mur[l][i] == null) c = i;
        }
        for (int i = 0;i<5;++i) {
            if (mur[i][c] == tuile) return -1;
        }
        mur[l][c] = tuile;
        return c >= 0 && c < 5 ? c : -1;
    }
    /**
     * @param c     Colonne du mur
     * @param tuile Couleur de la tuile
     * @return true s'il n'y a pas de tuile de la même couleur dans la colonne
     */
    private boolean verifierColonne(int c, Tuile tuile) {
        if (c >= 5)
            throw new IllegalArgumentException("erreur : l>=5");
        for (int l = 0; l < 5; l++) {
            if (mur[l][c] == tuile)
                return true;
        }
        return false;
    }

}
