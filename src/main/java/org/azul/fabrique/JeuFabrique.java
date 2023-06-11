package org.azul.fabrique;

import org.azul.Jeu;

import java.util.Arrays;

public class JeuFabrique {

    /*
    Index 0 : Blanche
    Index 1 : Noire
    Index 2 : Jaune
    Index 3 : Rouge
    Index 4 : Bleue
    */
    private int[] nbTuiles;

    /*
    Index 0 : Blanche
    Index 1 : Noire
    Index 2 : Jaune
    Index 3 : Rouge
    Index 4 : Bleue
    */
    private int[] couvercle;

    /**
     * Constructeur de la classe Main qui instancie le nombre de tuiles total à 100, 20 par couleur.
     */
    public JeuFabrique() {
        this.nbTuiles = new int[5];
        Arrays.fill(nbTuiles, 20);
        this.couvercle = new int[5];
    }

    /**
     * Permet la génération des fabriques suivant un nombre de bots.
     *
     * @param nbR Nombre de bots
     * @return Renvoie les fabriques générées, ou null le cas échéant
     */
    public Fabrique[] generationFabriques(int nbR) {

        Fabrique [] fabriques = (nbR == 2) ?
                new Fabrique[5] : (nbR == 3) ?
                new Fabrique[7] : (nbR == 4) ?
                new Fabrique[9] : null;

        assert fabriques != null;
        ajouterTuilesFabriques(fabriques);

        return fabriques;
    }

    /**
     * Permet d'ajouter des tuiles dans des fabriques.
     *
     * @param fabriques Fabriques dans lesquelles on ajoute les tuiles
     */
    public void ajouterTuilesFabriques(Fabrique[] fabriques) {
        Tuile[] tuiles = new Tuile[4];
        int nbTuilesTotal = Arrays.stream(this.getNbTuiles()).sum();
        if (nbTuilesTotal >= fabriques.length * 4) {
            for (int i = 0; i < fabriques.length; i++) {
                for (int j = 0; j < tuiles.length; j++) {
                    tuiles[j] = Tuile.getRandomColor();
                    while (!verificationCouleurEtNombreTuiles(tuiles[j])) {
                        tuiles[j] = Tuile.getRandomColor();
                    }
                }
                fabriques[i] = new Fabrique(tuiles);
            }
        } else {
            int i = 0;
            int nbTuilesEnMoins = 0;
            while (nbTuilesTotal > 0) {
                if (nbTuilesTotal >= 4) {
                    for (int j = 0; j < tuiles.length; j++) {
                        tuiles[j] = Tuile.getRandomColor();
                        while (!verificationCouleurEtNombreTuiles(tuiles[j])) {
                            tuiles[j] = Tuile.getRandomColor();
                        }
                        nbTuilesTotal--;
                    }
                } else {
                    for (int j = 0; j < nbTuilesTotal; j++) {
                        tuiles[j] = Tuile.getRandomColor();
                        while (!verificationCouleurEtNombreTuiles(tuiles[j])) {
                            tuiles[j] = Tuile.getRandomColor();
                        }
                        nbTuilesEnMoins++;
                    }
                    nbTuilesTotal -= nbTuilesEnMoins;
                }
                fabriques[i] = new Fabrique(tuiles);
                i++;
            }
            for (int k = i; k < fabriques.length; k++) {
                fabriques[k] = new Fabrique();
            }
        }
    }

    /**
     * Vérifie si une tuile possède une certaine couleur, et s'il reste des tuiles de cette même couleur dans le nombre de tuiles maximum.
     *
     * @param tuile Tuile à vérifier
     * @return Vrai ou faux suivant les conditions
     */
    public boolean verificationCouleurEtNombreTuiles(Tuile tuile) {

        String couleur = (tuile == Tuile.BLANCHE && this.getNbTuiles()[0] > 0) ?
                "bla" : (tuile == Tuile.NOIRE && this.getNbTuiles()[1] > 0) ?
                "n" : (tuile == Tuile.JAUNE && this.getNbTuiles()[2] > 0) ?
                "j" : (tuile == Tuile.ROUGE && this.getNbTuiles()[3] > 0) ?
                "r" : (tuile == Tuile.BLEUE && this.getNbTuiles()[4] > 0) ?
                "ble" : null;

        if (couleur == null) return false;

        this.setNbTuiles(couleur);

        return true;

    }

    /**
     * Ajoute un nombre de tuiles défini dans le couvercle d'une partie.
     *
     * @param tuile Tuile dont la couleur est à recycler
     * @param nb Nombre de tuiles à ajouter
     */
    public void ajouterCouvercle(Tuile tuile, int nb) {
        switch (tuile) {
            //Cas où la tuile est blanche
            case BLANCHE -> this.couvercle[0] += nb;
            //Cas où la tuile est noire
            case NOIRE -> this.couvercle[1] += nb;
            //Cas où la tuile est jaune
            case JAUNE -> this.couvercle[2] += nb;
            //Cas où la tuile est rouge
            case ROUGE -> this.couvercle[3] += nb;
            //Cas où la tuile est bleue
            case BLEUE -> this.couvercle[4] += nb;
        }
    }

    /**
     * Recycle les tuiles du couvercle dans la réserve max des tuiles.
     */
    public void recyclerTuiles() {
        this.nbTuiles[0] += this.couvercle[0];
        this.nbTuiles[1] += this.couvercle[1];
        this.nbTuiles[2] += this.couvercle[2];
        this.nbTuiles[3] += this.couvercle[3];
        this.nbTuiles[4] += this.couvercle[4];
        reinitialiserCouvercle();
    }

    /**
     * Réinitialise le nombre de tuiles présentes dans le couvercle.
     */
    private void reinitialiserCouvercle() {
        this.couvercle[0] = 0;
        this.couvercle[1] = 0;
        this.couvercle[2] = 0;
        this.couvercle[3] = 0;
        this.couvercle[4] = 0;
    }

    public int[] getNbTuiles() {
        return nbTuiles;
    }

    public int[] getCouvercle() {
        return couvercle;
    }

    /**
     * Décrémente les tuiles d'une couleur donnée.
     *
     * @param c Couleur à changer
     */
    public void setNbTuiles(String c) {
        switch (c) {
            //Cas où la tuile est blanche
            case "bla" -> this.nbTuiles[0]--;
            //Cas où la tuile est noire
            case "n" -> this.nbTuiles[1]--;
            //Cas où la tuile est jaune
            case "j" -> this.nbTuiles[2]--;
            //Cas où la tuile est rouge
            case "r" -> this.nbTuiles[3]--;
            //Cas où la tuile est bleue
            case "ble" -> this.nbTuiles[4]--;
        }
    }

}
