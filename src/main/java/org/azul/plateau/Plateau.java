package org.azul.plateau;

public class Plateau {

    private Motif motif;
    private Mur mur;
    private Score score;
    private Plancher plancher;


    public Plateau(int typeDeJeu) {
        this.motif = new Motif();
        this.score = new Score();
        this.plancher = new Plancher();
        switch (typeDeJeu) {
            case 1 -> this.mur = new MurClassique();
            case 2 -> this.mur = new MurGris();
            case 3 -> this.mur = new MurX2();
            case 4 -> this.mur = new MurCentre();
            default -> throw new IllegalStateException("Unexpected value: " + mur);
        }
    }

    public Plateau(Plateau plateau, int typeDeJeu) {
        this.motif = new Motif(plateau.motif);
        this.score = new Score(plateau.score);
        this.plancher = new Plancher(plateau.plancher);
        switch (typeDeJeu) {
            case 1 -> this.mur = new MurClassique((MurClassique) plateau.mur);
            case 2 -> this.mur = new MurGris((MurGris) plateau.mur);
            case 3 -> this.mur = new MurX2((MurX2) plateau.mur);
            case 4 -> this.mur = new MurCentre((MurCentre) plateau.mur);
            default -> throw new IllegalStateException("Unexpected value: " + plateau.mur);
        }
    }


    /**
     *
     * @return Retourne le motif d'un bot
     */
    public Motif getMotif() {
        return this.motif;
    }

    /**
     * @return Retourne le mur d'un bot
     */
    public Mur getMur() {
        return this.mur;
    }

    /**
     *
     * @return Retourne le score d'un bot
     */
    public Score getScore() {
        return this.score;
    }

    /**
     *
     * @return Retourne le plancher d'un bot
     */
    public Plancher getPlancher() {
        return plancher;
    }

}
