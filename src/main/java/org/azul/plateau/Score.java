package org.azul.plateau;

/**
 * Classe (Score) dans laquelle est géré le score de chaque joueur (en l'occurrence, des bots).
 *
 * @author BERGES Jeremy
 */
public class Score {
    private int score;

    /**
     * Constructeur de la classe Score, avec une initialisation de l'attribut à 0.
     */
    public Score() {
        score = 0;
    }

    public Score(Score score) {
        this.score = score.score;
    }

    /**
     * Ajoute une valeur au score.
     *
     * @param v Valeur à ajouter au score
     */
    public void ajouterAuScore(int v) {
        score = Math.max(score + v, 0);
    }
    /**
     * @param l     Ligne complète
     * @param c     Colonne complète
     * @param color 5 tuiles de la même couleur sur le mur
     */
    public void ajouterAuScoreFinDePartie(int l, int c, int color) {
        score = Math.max(score + (2 * l), 0);
        score = Math.max(score + 7 * c, 0);
        score = Math.max(score + 10 * color, 0);
    }

    /**
     * @param l     Ligne complète
     * @param c     Colonne complète
     * @param color 5 tuiles de la même couleur sur le mur
     */
    public void ajouterAuScoreCentreFinDePartie(int l, int c, int color) {
        score = Math.max(score + (3 * l), 0);
        score = Math.max(score + 10 * c, 0);
        score = Math.max(score + 12 * color, 0);
    }

    /**
     * Donne le score du bot.
     *
     * @return Valeur du score
     */
    public int getScore() {
        return score;
    }

}
