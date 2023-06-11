package org.azul.arbitre;

import org.azul.Main;
import org.azul.bot.IBot;
import org.azul.fabrique.*;
import org.azul.plateau.*;
import org.azul.visuel.StatsParties;
import org.azul.visuel.Vue;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Berges Jeremy, Arthur Dauphin
 * @version 1.0
 */
public class Arbitre {
    private IBot[] joueurs;
    private Plateau[] plateauJoueurs;
    private int numBot;
    private final int typeDePartie;

    public Plateau[] getPlateauJoueurs() {
        return plateauJoueurs;
    }
    public Plateau getPlateauJoueurs(int botId) {
        return plateauJoueurs[botId];
    }

    /**
     * permet d'obtenir une copie des joueurs sans pouvoir les modifier
     *
     * @return une copie de joueurs
     */
    public final IBot[] getJoueurs() {
        return Arrays.copyOf(joueurs, joueurs.length);
    }

    public Arbitre(int typeDePartie, IBot... bots) {
        joueurs = bots;
        this.typeDePartie = typeDePartie;
        plateauJoueurs = new Plateau[bots.length];
        for (int i = 0; i < bots.length; ++i) plateauJoueurs[i] = new Plateau(typeDePartie);
    }

    /**
     * @param fabriques   Les fabriques en jeu
     * @param centreTable Le centre de la table ou tombe les tuiles non choisit
     * @param vue         Instance de l'affichage
     */
    public void verifierCoupBot(Fabrique[] fabriques, CentreTable centreTable, Vue vue, int nbParties, StatsParties stats, JeuFabrique jeuFabrique) {
        int j = 0;
        if (!centreTable.getMalusCentreTable()) j = numBot;
        for (;j < joueurs.length; j++) {
            if (!fabriquesPleinesEtCentreTablePlein(fabriques, centreTable)) break;
            ArrayList<Tuile> tuilesBot = new ArrayList<>();
            boolean provenanceCentreTable;
            int ligneMotifChoisi;
            int tuileAPrendre;
            IConteneur conteneur;
            Plateau[] plateauJoueursCopie = new Plateau[plateauJoueurs.length];
            for (int i = 0; i < plateauJoueurs.length; i++) {
                plateauJoueursCopie[i] = new Plateau(plateauJoueurs[i], typeDePartie);
            }
        /*  choixBot contient :
                coupAJouer ⇒
                    0 → la fabrique dans laquelle le bot veux piocher ;
                    1 → ligne motif ciblée ;
                    2 → tuile à prendre dans fabrique / centre table ;
                provenanceCentreTable ⇒ qui indique si la fabrique vient du centre de la table
            return new Object[]{coupAJouer, provenanceCentreTable};*/
            Object[] choixBot = joueurs[j].jouer(fabriques, centreTable, plateauJoueursCopie);
            provenanceCentreTable = (boolean) choixBot[1];
            ligneMotifChoisi = (int) ((Object[]) choixBot[0])[1];
            tuileAPrendre = (int) ((Object[]) choixBot[0])[2];
            conteneur = (IConteneur) ((Object[]) choixBot[0])[0];


            tuilesBot.add(conteneur.getContenu().get(tuileAPrendre));
            conteneur.getContenu().remove(tuileAPrendre);

            byte nbTuilesPioche = 1;

            //Récupère les tuiles de la même couleur
            for (int i = 0; i < conteneur.getContenu().size(); i++) {
                if (conteneur.getContenu().get(i).name().contains(tuilesBot.get(0).name())) {
                    nbTuilesPioche++;
                    tuilesBot.add(conteneur.getContenu().get(i));
                    conteneur.getContenu().remove(i);
                    i--;
                }
            }

            if (!provenanceCentreTable) {
                centreTable.addContenu(conteneur.getContenu());
                conteneur.getContenu().clear();
            }
            joueurs[j].getTuiles().addAll(tuilesBot);

            if(!centreTable.getMalusCentreTable()){
                numBot = 0;
            }

            if (provenanceCentreTable && !centreTable.getMalusCentreTable()) {
                plateauJoueurs[joueurs[j].getId()].getPlancher().ajouterAuPlancher(1, stats, joueurs[j].getId());
                centreTable.setMalusCentreTable(true);
                numBot = j;
            }
            vue.afficherPiocher(nbParties, joueurs[j].getId(), tuilesBot.get(tuilesBot.size() - 1), nbTuilesPioche, provenanceCentreTable);


            verifierPoseTuiles(ligneMotifChoisi, joueurs[j], plateauJoueurs[joueurs[j].getId()],vue, nbParties, stats, jeuFabrique);
            vue.afficherLignesMotifs(nbParties, joueurs[j].getId(), plateauJoueurs[joueurs[j].getId()].getMotif().getEmplacements());
        }
    }

    /**
     * @param plateau        Le plateau du bot qui joue
     * @param ligneSouhaiter La ligne motif que l'utilisateur veut vérifier
     * @param tuileVoulu     La couleur des tuiles
     * @return true s'il est possible de poser la tuile voulue sur la ligne sinon false
     */
    boolean verifierLigneMotif(Plateau plateau, int ligneSouhaiter, Tuile tuileVoulu) {
        int j = 0;
        for (int i = 0; i < plateau.getMotif().getEmplacements()[ligneSouhaiter].length; ++i) {
            if (plateau.getMotif().getEmplacements(ligneSouhaiter, i) == null || plateau.getMotif().getEmplacements(ligneSouhaiter, i) == tuileVoulu)
                j++;
        }
        return j == plateau.getMotif().getEmplacements()[ligneSouhaiter].length;
    }

    /**
     * @param choixEmplacements L'emplacement sur lequel placer les tuiles
     * @param joueur            Les bots qui jouent
     * @param vue               Instance de l'affichage
     */
    void verifierPoseTuiles(int choixEmplacements, IBot joueur, Plateau plateau, Vue vue, int nbParties, StatsParties stats, JeuFabrique jeuFabrique) {
        int nbrTuiles = joueur.getTuiles().size();
        Tuile[][] motif = plateau.getMotif().getEmplacements();
        if (choixEmplacements < (nbrTuiles - 1)) {
            for (int i = 0; i < (nbrTuiles - 1) - choixEmplacements; i++) {
                jeuFabrique.ajouterCouvercle(joueur.getTuiles().remove(0), 1);
                plateau.getPlancher().ajouterAuPlancher(1, stats, joueur.getId());
            }
        }
        ArrayList<Integer> emplacements = new ArrayList<>();

        if (!verifierLigneMotif(plateau, choixEmplacements, joueur.getTuiles().get(0))) {
            jeuFabrique.ajouterCouvercle(joueur.getTuiles().get(0), joueur.getTuiles().size());
            plateau.getPlancher().ajouterAuPlancher(joueur.getTuiles().size(), stats, joueur.getId());
        } else {
            //Place les tuiles dans la ligne motif.
            for (Tuile tuile : joueur.getTuiles()) {
                for (int i = 0; i < choixEmplacements + 1; i++) {
                    if (motif[choixEmplacements][i] == null) {
                        motif[choixEmplacements][i] = tuile;
                        emplacements.add(i);
                        break;
                    } else if (motif[choixEmplacements][choixEmplacements] != null) {
                        jeuFabrique.ajouterCouvercle(tuile, 1);
                        plateau.getPlancher().ajouterAuPlancher(1, stats, joueur.getId());
                        break;
                    }
                }
            }
        }
        plateau.getMotif().setEmplacements(motif);

        vue.afficherDeposer(nbParties, joueur.getId(), joueur.getTuiles().get(joueur.getTuiles().size() - 1), choixEmplacements, emplacements);


        joueur.getTuiles().clear();
    }

    /**
     * Ajoute une tuile au mur si la ligne motif est complète et vide la ligne motif
     */
    void ajouterAuMurNormal(StatsParties stats, JeuFabrique jeuFabrique) {
        for (IBot joueur : joueurs) {
            Motif motifJoueur = plateauJoueurs[joueur.getId()].getMotif();
            Mur murJoueur = plateauJoueurs[joueur.getId()].getMur();
            Score scoreJoueur = plateauJoueurs[joueur.getId()].getScore();
            for (int i = 0; i < 5; ++i) {
                if (!motifJoueur.estComplete(i)) continue;
                int c = murJoueur.ajouterAuMur(motifJoueur.getEmplacements(i, 0), i);
                if (c == -1) {
                    plateauJoueurs[joueur.getId()].getPlancher().ajouterAuPlancher(plateauJoueurs[joueur.getId()].getMotif().getEmplacements()[i].length, stats, joueur.getId());
                    jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(i, 0), motifJoueur.getEmplacements()[i].length - 1);
                    motifJoueur.viderLigne(i);
                } else {
                    jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(i, 0), motifJoueur.getEmplacements()[i].length - 1);
                    motifJoueur.viderLigne(i);
                    scoreJoueur.ajouterAuScore(1);
                    tuilesAdjacentes(murJoueur, scoreJoueur, i, c);
                }
            }
        }
    }

    public void ajouterAuMur(StatsParties stats, JeuFabrique jeuFabrique) {
        switch (typeDePartie) {
            case 1 -> ajouterAuMurNormal(stats, jeuFabrique);
            case 2 -> ajouterAuMurGris(stats, jeuFabrique);
            case 3 -> ajouterAuMurX2(stats, jeuFabrique);
            case 4 -> ajouterAuMurCentre(stats,jeuFabrique);
        }
        jeuFabrique.recyclerTuiles();
    }

    /**
     * Ajoute une tuile au mur si la ligne motif est complète et vide la ligne motif (Mur gris)
     */
    void ajouterAuMurGris(StatsParties stats, JeuFabrique jeuFabrique) {
        for (IBot joueur : joueurs) {
            Motif motifJoueur = plateauJoueurs[joueur.getId()].getMotif();
            Mur murJoueur = plateauJoueurs[joueur.getId()].getMur();
            Score scoreJoueur = plateauJoueurs[joueur.getId()].getScore();
            for (int i = 0; i < 5; ++i) {
                if (!motifJoueur.estComplete(i)) continue;
                int c = murJoueur.ajouterAuMur(motifJoueur.getEmplacements(i, 0), i);
                if (c == -1) {
                    plateauJoueurs[joueur.getId()].getPlancher().ajouterAuPlancher(plateauJoueurs[joueur.getId()].getMotif().getEmplacements()[i].length, stats, joueur.getId());
                    jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(i, 0), motifJoueur.getEmplacements()[i].length - 1);
                    motifJoueur.viderLigne(i);
                } else {
                    jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(i, 0), motifJoueur.getEmplacements()[i].length - 1);
                    motifJoueur.viderLigne(i);
                    scoreJoueur.ajouterAuScore(1);
                    tuilesAdjacentes(murJoueur, scoreJoueur, i, c);
                }
            }
        }
    }
    /**
     * Ajoute une tuile au mur si la ligne motif est complète et vide la ligne motif (Mur X2)
     */
    void ajouterAuMurX2(StatsParties stats, JeuFabrique jeuFabrique) {
        for (IBot joueur : joueurs) {
            Motif motifJoueur = plateauJoueurs[joueur.getId()].getMotif();
            Mur murJoueur = plateauJoueurs[joueur.getId()].getMur();
            Score scoreJoueur = plateauJoueurs[joueur.getId()].getScore();
            for (int l = 0; l < 5; ++l) {
                for(int c = 0; c < 5; ++c) {
                    if (!motifJoueur.estComplete(l)) continue;
                    int a = murJoueur.ajouterAuMur(motifJoueur.getEmplacements(l, c), l, c);
                    if (a == -1) {
                        plateauJoueurs[joueur.getId()].getPlancher().ajouterAuPlancher(plateauJoueurs[joueur.getId()].getMotif().getEmplacements()[l].length, stats, joueur.getId());
                        jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(l, 0), motifJoueur.getEmplacements()[l].length - 1);
                        motifJoueur.viderLigne(l);
                    } else {
                        jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(l, 0), motifJoueur.getEmplacements()[l].length - 1);
                        motifJoueur.viderLigne(l);
                        if(l==0 && c==3 || l==1 && c==0 || l==2 && c==2 || l==3 && c==4 || l==1 && c==4)  {
                            scoreJoueur.ajouterAuScore(2);
                            tuilesAdjacentesX2(murJoueur, scoreJoueur, l, c);
                        }
                        scoreJoueur.ajouterAuScore(1);
                        tuilesAdjacentes(murJoueur, scoreJoueur, l, c);
                    }
                }
            }
        }
    }

    /**
     * Ajoute une tuile au mur si la ligne motif est complète et vide la ligne motif (Mur X2)
     */
    public void ajouterAuMurCentre(StatsParties stats, JeuFabrique jeuFabrique) {
        for (IBot joueur : joueurs) {
            Motif motifJoueur = plateauJoueurs[joueur.getId()].getMotif();
            Mur murJoueur = plateauJoueurs[joueur.getId()].getMur();
            Score scoreJoueur = plateauJoueurs[joueur.getId()].getScore();
            for (int l = 0; l < 5; ++l) {
                for(int c = 0; c < 5; ++c) {
                    if (!motifJoueur.estComplete(l)) continue;
                    int a = murJoueur.ajouterAuMur(motifJoueur.getEmplacements(l, c), l, c);
                    if (a == -1) {
                        plateauJoueurs[joueur.getId()].getPlancher().ajouterAuPlancher(plateauJoueurs[joueur.getId()].getMotif().getEmplacements()[l].length, stats, joueur.getId());
                        jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(l, 0), motifJoueur.getEmplacements()[l].length - 1);
                        motifJoueur.viderLigne(l);
                    } else {
                        jeuFabrique.ajouterCouvercle(motifJoueur.getEmplacements(l, 0), motifJoueur.getEmplacements()[l].length - 1);
                        motifJoueur.viderLigne(l);
                        scoreJoueur.ajouterAuScore(1);
                        tuilesAdjacentes(murJoueur, scoreJoueur, l, c);
                    }
                }
            }
        }
    }

    /**
     * @param mur   Mur du bot
     * @param score Score du bot
     * @param l     Ligne où la tuile est posée
     * @param c     colonne où la tuile est posée
     */
    public void tuilesAdjacentes(Mur mur, Score score, int l, int c) {

        int tailleLigne = mur.getTailleLigne();
        int tailleColonne = mur.getTailleColonne();

        //Tuile adjacente horizontale à droite
        if (c < 4) {

            for (int i = c + 1; i < tailleLigne; i++) {
                if (mur.getTuileMur(l, i) != null) {
                    score.ajouterAuScore(1);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente horizontale à gauche
        if (c > 0) {
            for (int i = c - 1; i > -1; i--) {
                if (mur.getTuileMur(l, i) != null) {
                    score.ajouterAuScore(1);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente verticale en bas
        if (l < 4) {
            for (int i = l + 1; i < tailleColonne; i++) {
                if (mur.getTuileMur(i, c) != null) {
                    score.ajouterAuScore(1);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente verticale en haut
        if (l > 0) {
            for (int i = l - 1; i > -1; i--) {
                if (mur.getTuileMur(i, c) != null) {
                    score.ajouterAuScore(1);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * @param mur   Mur du bot
     * @param score Score du bot
     * @param l     Ligne où la tuile est posée
     * @param c     colonne où la tuile est posée
     */
    public void tuilesAdjacentesX2(Mur mur, Score score, int l, int c) {

        int tailleLigne = mur.getTailleLigne();
        int tailleColonne = mur.getTailleColonne();

        //Tuile adjacente horizontale à droite
        if (c < 4) {

            for (int i = c + 1; i < tailleLigne; i++) {
                if (mur.getTuileMur(l, i) != null) {
                    score.ajouterAuScore(2);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente horizontale à gauche
        if (c > 0) {
            for (int i = c - 1; i > -1; i--) {
                if (mur.getTuileMur(l, i) != null) {
                    score.ajouterAuScore(2);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente verticale en bas
        if (l < 4) {
            for (int i = l + 1; i < tailleColonne; i++) {
                if (mur.getTuileMur(i, c) != null) {
                    score.ajouterAuScore(2);
                } else {
                    break;
                }
            }
        }

        //Tuile adjacente verticale en haut
        if (l > 0) {
            for (int i = l - 1; i > -1; i--) {
                if (mur.getTuileMur(i, c) != null) {
                    score.ajouterAuScore(2);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Vérifie si une ligne du mur est complète.
     *
     * @param lesbots Liste des bots
     * @return Vrai ou faux
     */
    public boolean estUneLigneComplete(IBot[] lesbots) {
        int tuiles = 0;
        for (IBot lebot : lesbots) {
            Mur murBot = this.getPlateauJoueurs(lebot.getId()).getMur();
            for (int i = 0; i < murBot.getTailleLigne(); i++) {
                for (int j = 0; j < murBot.getTailleColonne(); j++) {
                    if (murBot.getTuileMur(i, j) != null) {
                        tuiles++;
                    }
                }
                if (tuiles == murBot.getTailleLigne()) {
                    return true;
                } else {
                    tuiles = 0;
                }
            }
        }
        return false;
    }

    /**
     * @return le bot qui a gagné la partie
     */
    public synchronized ArrayList<IBot> recupererPossiblesGagnants(StatsParties statsParties) {


        for (IBot iBot : joueurs) {
            statsParties.ajouterNbColonneComplete(iBot.getId(), this.recupererNbColonneComplete(iBot));
            statsParties.ajouterNbLignesComplete(iBot.getId(), this.recupererNbLigneComplete(iBot));
            statsParties.ajouterNbCouleurs(plateauJoueurs[iBot.getId()].getMur());
        }

        ArrayList<IBot> possiblesGagnants = new ArrayList<>();
        int scoreRef = plateauJoueurs[joueurs[0].getId()].getScore().getScore();
        possiblesGagnants.add(0, joueurs[0]);
        for (int i = 1; i < joueurs.length; i++) {
            if (scoreRef < plateauJoueurs[joueurs[i].getId()].getScore().getScore()) {
                scoreRef = plateauJoueurs[joueurs[i].getId()].getScore().getScore();
                possiblesGagnants.set(0, joueurs[i]);
            }
        }
        for (IBot joueur : joueurs) {
            if (plateauJoueurs[joueur.getId()].getScore().getScore() == scoreRef && joueur.getId() != possiblesGagnants.get(0).getId()){
                possiblesGagnants.add(joueur);
            }
        }
        return possiblesGagnants;
    }

    /**
     * Récupère le(s) véritable(s) gagnant(s) de la partie.
     *
     * @param possiblesGagnants La liste des bots (les gagnants possibles)
     * @return Liste des bots gagnants
     */
    public ArrayList<IBot> recupererGagnantFinal(ArrayList<IBot> possiblesGagnants) {

        ArrayList<IBot> botsGagnants = new ArrayList<>();
        botsGagnants.add(possiblesGagnants.get(0));
        int lignesCompletesRef = this.recupererNbLigneComplete(possiblesGagnants.get(0));

        int lignesCompletesTemp;
        for (int i = 1; i < possiblesGagnants.size(); i++) {
            lignesCompletesTemp = this.recupererNbLigneComplete(possiblesGagnants.get(i));

            if (lignesCompletesTemp > lignesCompletesRef) {
                lignesCompletesRef = lignesCompletesTemp;
                botsGagnants.clear();
                botsGagnants.add(possiblesGagnants.get(i));
                possiblesGagnants.remove(possiblesGagnants.get(i));
            }
        }

        for (IBot possiblesGagnant : possiblesGagnants) {
            lignesCompletesTemp = this.recupererNbLigneComplete(possiblesGagnant);

            if (botsGagnants.get(0).getId() != possiblesGagnant.getId() && lignesCompletesTemp == lignesCompletesRef) {
                botsGagnants.add(possiblesGagnant);

            }
        }
        return botsGagnants;
    }

    /**
     * Récupère le nombre de lignes complétées par un bot donné grâce à une méthode de la classe Mur.
     *
     * @param bot Un bot
     * @return Nombre de lignes complétées
     */
    public int recupererNbLigneComplete(IBot bot) {
        return this.getPlateauJoueurs(bot.getId()).getMur().nbLigneComplete();
    }

    /**
     * Récupère le nombre de colonnes complétées par un bot donné grâce à une méthode de la classe Mur.
     *
     * @param bot Un bot
     * @return Nombre de colonnes complétées
     */
    public int recupererNbColonneComplete(IBot bot) {
        return this.getPlateauJoueurs(bot.getId()).getMur().nbColonneComplete();
    }

    /**
     * Récupère le nombre de couleurs complétées par un bot donné grâce à une méthode de la classe Mur.
     *
     * @param bot Un bot
     * @return Nombre de couleurs complétées
     */
    public int recupererNbCouleurComplete(IBot bot) {
        return this.getPlateauJoueurs(bot.getId()).getMur().nbCouleurComplete();
    }

    /**
     * @param lesFabriques La totalité des fabriques
     * @return Vrai si les fabriques ou le centre de la table ne sont pas vides, faux s'ils le sont
     */
    public boolean fabriquesPleinesEtCentreTablePlein(Fabrique[] lesFabriques, CentreTable centreTable) {
        for (Fabrique laFabrique : lesFabriques) {
            if (!laFabrique.getContenu().isEmpty() || !centreTable.getContenu().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie s'il reste des tuiles sur les cent maximums.
     *
     * @return Vrai si le nombre de tuiles total est de 0, faux dans le cas contraire
     */
    public boolean aucuneTuiles(JeuFabrique jeuFabrique) {
        return Arrays.stream(jeuFabrique.getNbTuiles()).sum() == 0;
    }

}
