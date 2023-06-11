package org.azul.plateau;

import org.azul.fabrique.Tuile;

import java.util.concurrent.ExecutionException;

public abstract class Mur {
    protected Tuile[][] mur;
    public final Tuile[][] murReference;

    protected Mur(Tuile[][] murReference) {
        mur = new Tuile[5][5];
        this.murReference = murReference;
    }

    /**
     * @return Retourne la taille de la ligne du mur
     */
    public int getTailleLigne() {
        return mur.length;
    }

    /**
     * @return Retourne la taille des colonnes du mur
     */
    public int getTailleColonne() {
        return mur[0].length;
    }

    /**
     * obtenir la tuile présente à une position donnée
     *
     * @param l la ligne
     * @param c la colonne
     * @return la tuile présente à la position fournie
     */
    public Tuile getTuileMur(int l, int c) {
        return mur[l][c];
    }

    /**
     * Renvoie le nombre de colonnes complétées sur le mur d'un bot donné.
     *
     * @return Nombre de couleurs complétées
     */
    public int nbColonneComplete() {
        int tuiles = 0;
        int nbColonneComplete = 0;
        for (int i = 0; i < this.getTailleLigne(); i++) {
            for (int j = 0; j < this.getTailleColonne(); j++) {
                if (this.getTuileMur(j, i) != null) {
                    tuiles++;
                }
            }
            if (tuiles == this.getTailleColonne()) {
                nbColonneComplete++;
            } else {
                tuiles = 0;
            }
        }
        return nbColonneComplete;
    }

    /**
     * Renvoie le nombre de lignes complétées sur le mur d'un bot donné.
     *
     * @return Nombre de lignes complétées
     */
    public int nbLigneComplete() {
        int tuiles = 0;
        int nbLigneComplete = 0;
        for (int i = 0; i < this.getTailleLigne(); i++) {
            for (int j = 0; j < this.getTailleColonne(); j++) {
                if (this.getTuileMur(i, j) != null) {
                    tuiles++;
                }else{
                    break;
                }
            }
            if (tuiles == this.getTailleLigne()) {
                nbLigneComplete++;
            }
            tuiles = 0;
        }
        return nbLigneComplete;
    }

    /**
     * Renvoie le nombre de couleurs complétées sur le mur d'un bot donné.
     *
     * @return Nombre de couleurs complétées
     */
    public int nbCouleurComplete() {
        int cmp = 0;
        int nbCouleurComplete = 0;
        for (Tuile tuiles : Tuile.values()) {
            for (int i = 0; i < this.getTailleLigne(); i++) {
                cmp += this.nbCouleurLigne(i, tuiles);
            }
            if (cmp == 4) {
                nbCouleurComplete++;
            } else {
                cmp = 0;
            }
        }
        return nbCouleurComplete;
    }

    /**
     * Permet de savoir à quelle position se trouve une couleur sur une ligne
     *
     * @param ligne   la position de la ligne
     * @param couleur la couleur cherchée
     * @return la position dans la ligne
     */
    public int nbCouleurLigne(int ligne, Tuile couleur) {
        for (int i = 0; i < getTailleLigne(); i++) {
            if (murReference[ligne][i] == couleur) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Permet de savoir quelle tuile est nécessaire pour terminer la ligne
     *
     * @param ligne le numéros de la ligne
     * @return vrai ou faux
     */
    public Tuile ligneCompleteAvecQuoi(int ligne) {
        int ouPrendre = -1;
        for (int i = 0; i < this.getTailleColonne(); i++) {
            if (mur[ligne][i] == null) {
                ouPrendre = i;
            }
        }
        return murReference[ligne][ouPrendre];
    }

    /**
     * Permet de savoir s'il y a déjà la tuile dans une ligne
     *
     * @param ligne   La ligne
     * @param couleur la couleur
     * @return un boolean
     */
    public boolean verifierCouleurLigne(int ligne, Tuile couleur) {
        for (int i = 0; i < getTailleLigne(); i++) {
            if (mur[ligne][i] == couleur) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de savoir s'il y a déjà la tuile dans une colonne
     *
     * @param colonne La colonne
     * @param couleur la couleur
     * @return un boolean
     */
    public boolean verifierCouleurColonne(int colonne, Tuile couleur) {
        for (int i = 0; i < getTailleLigne(); i++) {
            if (mur[i][colonne] == couleur) {
                return true;
            }
        }
        return false;
    }

    /**
     * Connaitre le nombre de tuiles vide dans une ligne du mur
     *
     * @param ligne Ligne
     * @return le nombre de tuiles vide
     */
    public int nbTuileLigne(int ligne) {
        int i = 0;
        for (Tuile tuile : mur[ligne]) {
            if (tuile != null) {
                i++;
            }
        }
        return i;
    }

    public abstract int ajouterAuMur(Tuile tuile, int ...pos);

    public boolean peutPoserMur(int i, int i1, Tuile couleur) {
        throw new RuntimeException("not implemented");
    }

    public boolean verifierMurX2(int i, int k, Tuile couleur) {
        throw new RuntimeException("not implemented");
    }

    public int nbCouleurLigneCentre(int ligne, int colonne, Tuile couleur) {
        throw new RuntimeException("not implemented");
    }
}
