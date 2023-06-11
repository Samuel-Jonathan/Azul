package org.azul.visuel;

import org.azul.fabrique.Fabrique;
import org.azul.fabrique.Tuile;
import org.azul.plateau.Mur;

import java.util.ArrayList;

/**
 * Classe Vue dans laquelle sont disposés les affichages du déroulement d'une partie.
 *
 * @author PREAUX Nicolas, SAMUEL Jonathan
 */
public class Vue {

    private static final String resetColor = "\u001B[0m";
    private boolean modeDeJeuxMurGris;
    private boolean modeDeJeuxPlateau1;
    private boolean modeDeJeuxPlateau2;

    public Vue(int typeDePartie) {
        this.modeDeJeuxMurGris = false;
        this.modeDeJeuxPlateau1 = false;
        this.modeDeJeuxPlateau2 = false;
        switch (typeDePartie) {
            case 0, 1 -> {}
            case 2 -> modeDeJeuxMurGris = true;
            case 3 -> modeDeJeuxPlateau1 = true;
            case 4 -> modeDeJeuxPlateau2 = true;
            default -> throw new IllegalArgumentException("Mode de jeu non pris en compte !");
        }
    }

    /**
     * Affiche une grande séparation dans l'interface textuelle.
     */
    public void afficherGrandeSeparation(int nbParties) {
        if(nbParties == 1) System.out.println("\n ------- \n");
    }

    /**
     * Affiche une petite séparation dans l'interface textuelle.
     */
    public void afficherPetiteSeparation(int nbParties) {
        if (nbParties == 1) System.out.println("\n --- \n");
    }

    /**
     * Affiche le gagnant d'une partie.
     *
     * @param numeroBot Numéro du bot gagnant
     * @param score     Score du bot gagnant
     */
    public synchronized void afficherGagnant(int nbParties, int numeroBot, int score) {

        if(nbParties == 1) System.out.println("Le gagnant est le bot n°" + numeroBot + " avec un score de " + score + ". Bravo à lui !");
    }

    /**
     * Affiche le nombre de manches d'une partie.
     *
     * @param nbManches Nombre de manches de la partie
     */
    public void afficherNbManches(int nbParties, int nbManches) {
        if(nbParties == 1) System.out.println("Nombre de manches actuelles : " + nbManches);
    }

    public void afficherProchaineManche(int nbParties) {
        if(nbParties == 1) System.out.println("Début d'une nouvelle manche !");
    }

    /**
     * Affiche le score du bot passé en paramètre, avec son numéro.
     *
     * @param numeroBot Numéro du bot
     * @param score     Score du bot
     */
    public synchronized void afficherScore(int nbParties, int numeroBot, int score) {
        if(nbParties == 1) System.out.println("Score du bot n°" + numeroBot + " : " + score);
    }

    /**
     * Affiche la couleur des tuiles posées.
     *
     * @param couleur Couleur des tuiles
     */
    public void afficherCouleur(int nbParties, String couleur) {
        if(nbParties == 1) System.out.println("Pose des tuiles de couleur : " + couleur);
    }

    /**
     * Affiche la pioche des tuiles d'un bot.
     *
     * @param numeroBot      Numéro du bot
     * @param couleur        Couleur des tuiles
     * @param nbTuilesPioche Nombre de tuiles piochées
     */
    public void afficherPiocher(int nbParties, int numeroBot, Tuile couleur, byte nbTuilesPioche, boolean provenanceCentreTable) {
        if(nbParties == 1) {
            if (provenanceCentreTable) {
                System.out.println("Le bot n°" + numeroBot + " a pioché " + nbTuilesPioche + " tuiles " + couleur.getCode() + couleur + resetColor + " depuis le centre de la table.");
            } else {
                System.out.println("Le bot n°" + numeroBot + " a pioché " + nbTuilesPioche + " tuiles " + couleur.getCode() + couleur + resetColor + " depuis une fabrique.");
            }
        }

    }

    /**
     * Affiche la pose des tuiles dans le mur du bot.
     *
     * @param numeroBot Numéro du bot
     * @param couleur   Couleur des tuiles
     * @param ligne     Ligne où les tuiles sont posées
     * @param position  Position des tuiles posées
     */
    public void afficherDeposer(int nbParties, int numeroBot, Tuile couleur, int ligne, ArrayList<Integer> position) {
        if(nbParties == 1) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (int pos : position) {
                if (i != position.size() - 1) {
                    sb.append(pos + 1).append(", ");
                } else {
                    sb.append(pos + 1);
                }
                i++;
            }
            System.out.println("Le bot n°" + numeroBot + " a déposé une tuile " + couleur.getCode() + couleur + resetColor +
                    " sur la ligne n°" + (ligne + 1) + ", sur la ou les colonnes n°" + sb + ".\n");
        }
    }

    /**
     * Affiche les points de malus du bot.
     *
     * @param numeroBot Numéro du bot
     * @param malus     Points de malus
     */
    public void afficherMalus(int nbParties, int numeroBot, int malus) {
        if(nbParties == 1) System.out.println("Le bot n°" + numeroBot + " a " + malus + " points de malus.");
    }

    /**
     * Affiche les lignes motifs du bot.
     *
     * @param numeroBot    Numéro du bot
     * @param emplacements Emplacements des lignes motifs
     */
    public void afficherLignesMotifs(int nbParties, int numeroBot, Tuile[][] emplacements) {
        if(nbParties == 1) {
            System.out.println("Les lignes motifs du bot n°" + numeroBot + " :");
            for (Tuile[] ligne : emplacements) {
                StringBuilder motif = new StringBuilder();
                motif.append("|");
                for (Tuile tuile : ligne) {
                    if (tuile == null) {
                        motif.append("    \u001B[40m___\u001B[0m    ");
                    } else if (tuile == Tuile.BLEUE) {
                        motif.append("   ").append(tuile.getCode()).append(tuile).append(resetColor).append("   ");
                    } else if (tuile == Tuile.ROUGE) {
                        motif.append("   ").append(tuile.getCode()).append(tuile).append(resetColor).append("   ");
                    } else if (tuile == Tuile.NOIRE) {
                        motif.append("   ").append(tuile.getCode()).append(tuile).append(resetColor).append("   ");
                    } else if (tuile == Tuile.BLANCHE) {
                        motif.append("  ").append(tuile.getCode()).append(tuile).append(resetColor).append("  ");
                    } else if (tuile == Tuile.JAUNE) {
                        motif.append("   ").append(tuile.getCode()).append(tuile).append(resetColor).append("   ");
                    }
                    motif.append("|");
                }
                System.out.println(motif);
            }
            System.out.println();
        }
    }

    /**
     * Affiche le mur du bot.
     *
     * @param numeroBot Numéro du bot
     * @param mur Mur du bot
     */
    public void afficherMurBot(int nbParties, int numeroBot, Mur mur) {
        if(nbParties == 1) {
            System.out.println("Mur du bot n°" + numeroBot + " :\n");
            StringBuilder s = new StringBuilder();
            for (int l = 0; l < 5; l++) {
                s.append("           |");
                for (int c = 0; c < 5; c++) {
                    if (mur.getTuileMur(l, c) == null) {
                        if (this.modeDeJeuxPlateau1) {
                            s.append("     \u001B[40m___\u001B[0m ");
                            if (l == 3 && c == 4)
                                s.append("\u001B[44mx2\u001B[0m    ");
                            else if (l == 4 && c == 1)
                                s.append("\u001B[41mx2\u001B[0m    ");
                            else if (l == 0 && c == 3)
                                s.append("\u001B[40mx2\u001B[0m    ");
                            else if (l == 1 && c == 0)
                                s.append("\u001B[47mx2\u001B[0m    ");
                            else if (l == 2 && c == 2)
                                s.append("\u001B[43mx2\u001B[0m    ");
                            else
                                s.append("      ");
                        }
                        else if (this.modeDeJeuxPlateau2) {
                            if (l == 1 && c == 1)
                                s.append("     \u001B[44m___\u001B[0m ");
                            else if (l == 1 && c == 3)
                                s.append("     \u001B[41m___\u001B[0m ");
                            else if (l == 2 && c == 2)
                                s.append("     \u001B[47m___\u001B[0m ");
                            else if (l == 3 && c == 1)
                                s.append("     \u001B[40m___\u001B[0m ");
                            else if (l == 3 && c == 3)
                                s.append("     \u001B[43m___\u001B[0m ");
                            else
                                s.append("     \u001B[40m___\u001B[0m ");
                        }
                        else
                            s.append("     \u001B[40m___\u001B[0m ");
                            s.append("      ");
                    } else if (mur.getTuileMur(l, c) == Tuile.BLEUE) {
                        s.append("    ").append(mur.getTuileMur(l, c).getCode()).append(mur.getTuileMur(l, c)).append(resetColor);
                        if (this.modeDeJeuxPlateau1 && l == 3 && c == 4)
                            s.append(" \u001B[40mx2\u001B[0m   ");
                        else
                            s.append("      ");
                    } else if (mur.getTuileMur(l, c) == Tuile.ROUGE) {
                        s.append("    ").append(mur.getTuileMur(l, c).getCode()).append(mur.getTuileMur(l, c)).append(resetColor);
                        if (this.modeDeJeuxPlateau1 && l == 4 && c == 1)
                            s.append(" \u001B[40mx2\u001B[0m   ");
                        else
                            s.append("      ");
                    } else if (mur.getTuileMur(l, c) == Tuile.NOIRE) {
                        s.append("    ").append(mur.getTuileMur(l, c).getCode()).append(mur.getTuileMur(l, c)).append(resetColor);
                        if (this.modeDeJeuxPlateau1 && l == 0 && c == 3)
                            s.append(" \u001B[40mx2\u001B[0m   ");
                        else
                            s.append("      ");
                    } else if (mur.getTuileMur(l, c) == Tuile.BLANCHE) {
                        s.append("   ").append(mur.getTuileMur(l, c).getCode()).append(mur.getTuileMur(l, c)).append(resetColor);
                        if (this.modeDeJeuxPlateau1 && l == 1 && c == 0)
                            s.append(" \u001B[40mx2\u001B[0m  ");
                        else
                            s.append("     ");
                    } else if (mur.getTuileMur(l, c) == Tuile.JAUNE) {
                        s.append("    ").append(mur.getTuileMur(l, c).getCode()).append(mur.getTuileMur(l, c)).append(resetColor);
                        if (this.modeDeJeuxPlateau1 && l == 2 && c == 2)
                            s.append(" \u001B[40mx2\u001B[0m   ");
                        else
                            s.append("      ");
                    }
                    s.append("|");
                }
                s.append("\n");
            }
            System.out.println(s);
        }
    }

    /**
     * Affiche les différentes fabriques de la partie.
     *
     * @param fabriques Liste des fabriques
     */
    public void afficherFabriques(int nbParties, Fabrique[] fabriques) {
        if (nbParties == 1){
            System.out.println("Liste des fabriques :");
            for (Fabrique fabrique : fabriques) {
                StringBuilder buf = new StringBuilder();
                buf.append("[");
                for (int j = 0; j < fabrique.getContenu().size(); j++) {
                    buf.append(fabrique.getContenu().get(j).getCode()).append(fabrique.getContenu().get(j)).append(resetColor);
                    if (j < fabrique.getContenu().size() - 1)
                        buf.append(", ");
                }
                buf.append("]");
                System.out.println(buf);
            }
        }
    }

    public void afficherEgalite(int nbParties) {
        if(nbParties == 1) {
            System.out.println("Égalité !");
        }
    }
}
