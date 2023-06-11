package org.azul.bot;

import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.IConteneur;
import org.azul.fabrique.Tuile;
import org.azul.plateau.Mur;
import org.azul.plateau.Plateau;
import org.azul.plateau.Score;

import java.util.ArrayList;

/**
 * @author Lucas Puissant--Garambois
 */
public class Bot implements IBot {

    private final int id;

    private ArrayList<Tuile> tuiles;

    private boolean provenanceCentreTable;

    private int colonneMurGris;

    private boolean modeDeJeuxMurGris;
    private boolean modeDeJeuxPlateau1;
    private boolean modeDeJeuxPlateau2;

    /**
     * Constructeur de bot
     *
     * @param id Numéro du bot
     */
    public Bot(int id, int modeDeJeux) {
        this.tuiles = new ArrayList<>();
        this.id = id;
        modeDeJeuxMurGris = false;
        modeDeJeuxPlateau1 = false;
        modeDeJeuxPlateau2 = false;
        switch (modeDeJeux) {
            case 0, 1 -> {}
            case 2 -> modeDeJeuxMurGris = true;
            case 3 -> modeDeJeuxPlateau1 = true;
            case 4 -> modeDeJeuxPlateau2 = true;
            default -> throw new IllegalArgumentException("Mode de jeux non prix en compte !");
        }
        this.colonneMurGris = 0;
    }

    /**
     * @return le numéro de colonne choisi par le bot dans le mode de jeux mur gris
     */
    public int getColonneMurGris() {
        return colonneMurGris;
    }

    /**
     * @return l'id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Permet de faire jouer le bot pour qu'il puisse récupérer les tuiles dans des fabriques ou dans le centre table et le pose ensuite dans
     * une ligne motif
     *
     * @param fabriques   Liste des fabriques
     * @param centreTable Le centre de la table
     */
    @Override
    public Object[] jouer(Fabrique[] fabriques, CentreTable centreTable, Plateau... plateaux) {
        /*
         * Ne pas prendre sur un tableau vide
         * Si choix prendre le plus de tuile
         * Priorité a la finalisation d'une ligne
         * Si plus de place prendre le moin possible
         * Eviler au maximum prendre au milieu s'il y a le malus
         * Prendre au milieu si c'est plus avantageux que prendre dans une fabrique
         * Si plus de place alors prendre le minimum de tuile à mettre dans les malus
         *
         * Ne pas prendre de tuile s'il y a deja la tuile dans le mur
         * C'est mieux de prendre une tuile adjacente et mieux de prendre ligne verticale
         * Fin de parti dans le cas où le bot a le plus de point
         *
         * Essayer d'avoir le plus de point sans finir
         * Faire le plus de ligne horizontale possible avant que gagner
         * Ne pas hésiter à prendre au centre
         *
         * Pour tout ce qui est de verifier les autres joueurs :
         * https://www.youtube.com/watch?v=YL40XxcN4OI
         * https://www.youtube.com/watch?v=8kWo7cmlpDM
         * */

        // Créer un tableau avec les autres joueurs pour pouvoir prévoir leurs coups et ainsi mieux jouer
        ArrayList<Plateau> autreJoueurs = new ArrayList<>();
        for (int i = 0; i < plateaux.length; ++i) {
            if (i != this.id) {
                autreJoueurs.add(plateaux[i]);
            }
        }

        //(nbrPoint, la fabrique à utiliser ou le centreTable, ligne motif où poser, l'emplacement de la tuile à utiliser)
        ArrayList<Object[]> listPoint = new ArrayList<>();

        //Attribue un certain nombre de points en fonction du coup avec la fabrique que le bot peut faire
        //Attribue un certain nombre de points en fonction du coup avec le centre de la table que le bot peut faire
        for (int i = 0; i < 5; i++) {
            for (Fabrique uneFabrique : fabriques) {
                calculCoupAJouer(listPoint, i, uneFabrique, plateaux[getId()], autreJoueurs);
            }
            calculCoupAJouer(listPoint, i, centreTable, plateaux[getId()], autreJoueurs);
        }

        //Attribue un certain nombre de points en fonction du coup avec la fabrique et le CT que les autres joueurs peuvent faire
        //pour les empêcher de faire leurs coups
        if (!autreJoueurs.isEmpty()) {
            ArrayList<Object[]> listPointAJ = new ArrayList<>();
            for (Plateau autreJoueur : autreJoueurs) {
                for (int i = 0; i < 5; i++) {
                    for (Fabrique uneFabrique : fabriques) {
                        calculCoupAJouer(listPointAJ, i, uneFabrique, autreJoueur, null);
                    }
                    calculCoupAJouer(listPointAJ, i, centreTable, autreJoueur, null);
                }
            }
            int nbrMax = -100;
            Object[] coupAJ = new Object[1];
            for (Object[] coup : listPointAJ) {
                if (nbrMax < (int) coup[0]) {
                    nbrMax = (int) coup[0];
                    coupAJ = coup;
                    coupAJ[0] = (int) coup[0] - 10;
                }
            }
            if (coupAJ.length > 3) {
                listPoint.add(coupAJ);
            }
        }

        //Doit prendre le nombre minimum de tuile disponible à mettre dans les malus
        if (listPoint.isEmpty()) {
            for (Fabrique fabrique : fabriques) {
                prendreMinimumTuile(fabrique, listPoint);
            }
            prendreMinimumTuile(centreTable, listPoint);
        }
        //Joue le coup qui a le plus de point
        int nbrMax = -10000;
        //(la fabrique à utiliser ou le centreTable, ligne motif où poser, l'emplacement de la tuile à utiliser)
        Object[] coupAJouer = new Object[3];
        for (Object[] coup : listPoint) {
            if (nbrMax < (int) coup[0]) {
                coupAJouer[0] = coup[1];
                coupAJouer[1] = coup[2];
                coupAJouer[2] = coup[3];
                if ((this.modeDeJeuxMurGris || this.modeDeJeuxPlateau1 || this.modeDeJeuxPlateau2) && coup.length == 5) {
                    this.colonneMurGris = (int) coup[4];
                }
                nbrMax = (int) coup[0];
            }
        }
        this.provenanceCentreTable = coupAJouer[0] instanceof CentreTable;

        /*
            coupAJouer ⇒
                0 → la fabrique dans laquelle le bot veux piocher ;
                1 → ligne motif ciblée ;
                2 → tuile à prendre dans fabrique / centre table ;
            provenanceCentreTable ⇒ qui indique si la fabrique vient du centre de la table
         */
        return new Object[]{coupAJouer, provenanceCentreTable};

    }

    /**
     * Prend le minimum de tuile d'une fabrique ou du centreTable quand le bot ne peut pas jouer
     *
     * @param contenue  une fabrique ou le centreTable
     * @param listPoint La liste des coups possible
     */
    private void prendreMinimumTuile(IConteneur contenue, ArrayList<Object[]> listPoint) {
        int[] listeDesTuileParCouleur = calculNbrTuile(contenue.getContenu());
        int point;
        for (int i = 0; i < listeDesTuileParCouleur.length; i++) {
            int tuileCouleurActuelle = listeDesTuileParCouleur[i];
            if (tuileCouleurActuelle != 0) {
                point = 100 - tuileCouleurActuelle;
                listPoint.add(new Object[]{point, contenue, 0, chercheTuile(contenue.getContenu(), couleur(i))});
            }
        }
    }

    /**
     * Permet de vérifier si un coup est possible
     *
     * @param listPoint La liste des coups possible
     * @param i         La ligne de la ligne motif
     * @param listTuile La liste des tuiles soit de fabrique soit de centreTable
     */
    private void calculCoupAJouer(ArrayList<Object[]> listPoint, int i, IConteneur listTuile, Plateau plateau, ArrayList<Plateau> autreJoueur) {
        int[] listeDesTuileParCouleur;
        listeDesTuileParCouleur = calculNbrTuile(listTuile.getContenu());
        for (int j = 0; j < listeDesTuileParCouleur.length; j++) {
            int tuileCouleurActuelle = listeDesTuileParCouleur[j];
            if (tuileCouleurActuelle != 0 && !plateau.getMur().verifierCouleurLigne(i, couleur(j))) {
                Tuile couleur = plateau.getMotif().getEmplacements(i, 0);
                if (couleur == couleur(j)) {
                    for (int k = 0; k < i + 1; k++) {
                        if (plateau.getMotif().getEmplacements(i, k) == null) {
                            coupAJouer(tuileCouleurActuelle, (i + 1) - k, listTuile, i, j, listPoint, autreJoueur, plateau);
                            break;
                        }
                    }
                } else if (plateau.getMotif().getEmplacements(i, 0) == null) {
                    coupAJouer(tuileCouleurActuelle, (i + 1), listTuile, i, j, listPoint, autreJoueur, plateau);
                }

            }
        }
    }

    /**
     * Attribue un nombre de points different en fonction de si le coup est bien ou pas
     * (plus il y a de point et plus le coup est intéressant à jouer pour le bot)
     *
     * @param nbrTuile  Le nombre de tuiles d'une même couleur
     * @param nbr       Le nombre à ne pas dépasser
     * @param listTuile Liste des tuiles (Fabrique ou CT)
     * @param i         La ligne
     * @param j         Le numéro couleur
     * @param listPoint La liste des coups possible
     */
    private void coupAJouer(int nbrTuile, int nbr, IConteneur listTuile, int i, int j, ArrayList<Object[]> listPoint, ArrayList<Plateau> autreJoueur, Plateau monPlateau) {

        int point = 0;
        if(nbrTuile < nbr){
            point = nbrTuile;
        } else if(nbrTuile > nbr){
            point = calculMalus(monPlateau, nbrTuile);
        } else {
            point = 5 + i;
        }
        if (this.modeDeJeuxMurGris ||this.modeDeJeuxPlateau2) {
            for (int k = 0; k < 5; k++) {
                int points = coupAJouerMur(i, couleur(j), monPlateau, autreJoueur, k);
                if (listTuile instanceof CentreTable && !((CentreTable) listTuile).getMalusCentreTable()) {
                    point -= 1; // car il y a le malus si le bot récupère dans le centre
                }
                listPoint.add(new Object[]{point + points, listTuile, i, chercheTuile(listTuile.getContenu(), couleur(j)), k});
            }
        } else if (this.modeDeJeuxPlateau1) {
            for (int k = 0; k < 5; k++) {
                int points = coupAJouerMurPlateau1(i, couleur(j), monPlateau, k, autreJoueur);
                if (listTuile instanceof CentreTable && !((CentreTable) listTuile).getMalusCentreTable()) {
                    point -= 1; // car il y a le malus si le bot récupère dans le centre
                }
                listPoint.add(new Object[]{point + points, listTuile, i, chercheTuile(listTuile.getContenu(), couleur(j)), k});
            }
        } else {
            point += coupAJouerMur(i, couleur(j), monPlateau, autreJoueur);
            if (listTuile instanceof CentreTable && !((CentreTable) listTuile).getMalusCentreTable()) {
                point -= 1; // car il y a le malus si le bot récupère dans le centre
            }
            listPoint.add(new Object[]{point, listTuile, i, chercheTuile(listTuile.getContenu(), couleur(j))});
        }
    }

    private int calculMalus(Plateau plateau, int nbrTuile) {
        int malus = plateau.getPlancher().getMalus();
        int malus2 = 0;
        for (int i = 0; i < nbrTuile; i++) {
            if(malus < 2){
                malus+=1;
                malus2-=1;
            } else if(malus < 8){
                malus+=2;
                malus2-=2;
            } else if(malus < 14){
                malus+=3;
                malus2-=3;
            }
        }
        return malus2;
    }

    /**
     * Permet de jouer sur le mode de jeu du Plateau 1
     *
     * @param i           Ligne
     * @param couleur     Couleur
     * @param monPlateau  Mon plateau
     * @param k           Colonne
     * @param autreJoueur Plateau autre joueur
     * @return un nbr de point plus ou moin grand si c'est bien ou pas
     */
    private int coupAJouerMurPlateau1(int i, Tuile couleur, Plateau monPlateau, int k, ArrayList<Plateau> autreJoueur) {
        int point = coupAJouerMur(i, couleur, monPlateau, autreJoueur, k);
        if (monPlateau.getMur().verifierMurX2(i, k, couleur)) {
            if (point > 5) {
                point = point * 2;
            } else {
                point = point/2;
            }
        } else {
            point = -10;
        }
        return point;
    }


    /**
     * Retourne un nombre de point plus ou moin grand s'il y a des tuiles à côté de l'emplacement de la tuile à poser dans le mur
     *
     * @param i           Ligne
     * @param couleur     La tuiles
     * @param autreJoueur La liste des autres autre joueur
     * @param k           La colonne
     * @return le nombre de points s'il y a d'autre tuile à côté
     */
    private int coupAJouerMur(int i, Tuile couleur, Plateau plateau, ArrayList<Plateau> autreJoueur, int... k) {
        Mur mur = plateau.getMur();
        Score score = new Score();
        if (this.modeDeJeuxMurGris) {
            tuilesAdjacentes(mur, score, i, k[0]);
            if (mur.verifierCouleurLigne(i, couleur) || mur.verifierCouleurColonne(k[0], couleur)) {
                return -100;
            }
        } else if(this.modeDeJeuxPlateau2) {
            int nbr = mur.nbCouleurLigneCentre(i, k[0], couleur);
            if(mur.peutPoserMur(i,k[0],couleur)) {
                if (nbr == -2) {
                    tuilesAdjacentes(mur, score, i, k[0]);
                } else if (nbr == -1) {
                    return -100;
                } else {
                    tuilesAdjacentes(mur, score, i, nbr);
                }
            } else {
                return -100;
            }
        } else {
            int colonne = mur.nbCouleurLigne(i, couleur);
            if(colonne != -1) {
                tuilesAdjacentes(mur, score, i, colonne);
            }
        }
        if (mur.nbTuileLigne(i) == 4) {
            if (mur.ligneCompleteAvecQuoi(i) == couleur && autreJoueur != null) {
                for (Plateau plateauAutre : autreJoueur) {
                    if (plateau.getScore().getScore() + score.getScore() < plateauAutre.getScore().getScore()) {
                        return -100;
                    }
                }
            }
        }
        return score.getScore();
    }

    /**
     * Permet de voir s'il y a des tuiles adjacentes pour que le bot mette les tuiles au bon endroit
     *
     * @param mur   Mur du bot
     * @param score Score du bot
     * @param l     Ligne où la tuile est posée
     * @param c     Colonne où la tuile est posée
     */
    public void tuilesAdjacentes(Mur mur, Score score, int l, int c) {

        int tailleLigne = mur.getTailleLigne();
        int tailleColonne = mur.getTailleColonne();

        for (int i = c + 1; i < tailleLigne; i++) {
            if (mur.getTuileMur(l, i) != null) {
                score.ajouterAuScore(1);
            } else {
                break;
            }
        }

        for (int i = c - 1; i > -1; i--) {
            if (mur.getTuileMur(l, i) != null) {
                score.ajouterAuScore(1);
            } else {
                break;
            }
        }

        for (int i = l + 1; i < tailleColonne; i++) {
            if (mur.getTuileMur(i, c) != null) {
                if(this.modeDeJeuxMurGris){
                    score.ajouterAuScore(3);
                }
                score.ajouterAuScore(2);
            } else {
                break;
            }
        }

        for (int i = l - 1; i > -1; i--) {
            if (mur.getTuileMur(i, c) != null) {
                if(this.modeDeJeuxMurGris){
                    score.ajouterAuScore(3);
                }
                score.ajouterAuScore(2);
            } else {
                break;
            }
        }
    }

    /**
     * Permet de savoir à quel emplacement dans une arraylist est une tuile d'une couleur précise
     *
     * @param fabrique ArrayList de Tuile
     * @param couleur  Une couleur
     * @return Integer
     */
    private int chercheTuile(ArrayList<Tuile> fabrique, Tuile couleur) {
        for (int i = 0; i < fabrique.size(); i++) {
            if (fabrique.get(i).equals(couleur)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Retourne une couleur en fonction d'un nombre entré
     *
     * @param i numéro de la couleur
     * @return Tuile
     */
    private Tuile couleur(int i) {
        return switch (i) {
            case 0 -> Tuile.BLEUE;
            case 1 -> Tuile.BLANCHE;
            case 2 -> Tuile.JAUNE;
            case 3 -> Tuile.NOIRE;
            case 4 -> Tuile.ROUGE;
            default -> null;
        };
    }

    /**
     * Retourne le nombre de tuiles pour chaque couleur
     *
     * @param tuile une ArrayList de Tuiles
     * @return Liste de Integer (Bleu,Blanche,Jaune,Noir,Rouge)
     */
    private int[] calculNbrTuile(ArrayList<Tuile> tuile) {
        int[] list = new int[5];
        for (Tuile value : tuile) {
            switch (value) {
                case BLEUE -> list[0] += 1;
                case BLANCHE -> list[1] += 1;
                case JAUNE -> list[2] += 1;
                case NOIRE -> list[3] += 1;
                case ROUGE -> list[4] += 1;
            }
        }
        return list;
    }

    /**
     * @return les tuiles du bot
     */
    @Override
    public ArrayList<Tuile> getTuiles() {
        return tuiles;
    }
}