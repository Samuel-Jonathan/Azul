package org.azul.bot;

import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.Tuile;
import org.azul.plateau.Plateau;

import java.util.ArrayList;

/**
 * @author Lucas Puissant--Garambois
 */
public class BotRandom implements IBot {
    private int id;

    private ArrayList<Tuile> tuiles;

    private SecureRandom rand;

    /**
     * Constructeur du botRandom
     * @param id Le numéro du bot
     */
    public BotRandom(int id) {
        this.tuiles = new ArrayList<>();
        this.rand = new SecureRandom();
        this.id = id;
    }
    BotRandom(int id, SecureRandom secureRandom) {
        this.tuiles = new ArrayList<>();
        this.rand = secureRandom;
        this.id = id;
    }

    /**
     * Retourne l'id du bot
     * @return l'id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Récupère aléatoirement toutes les tuiles de la même couleur d'une fabrique de la liste de fabriques choisie aléatoirement
     *
     * @param fabriques La liste des fabriques
     */
    public int[] piocherFabrique(Fabrique[] fabriques) {

        //Choisie une fabrique aléatoirement en fonction du nombre de fabriques
        int choixFabrique = rand.Random(fabriques.length);
        //Récupère l'adresse mémoire des tuiles de la fabrique choisie dans un Arraylist
        ArrayList<Tuile> tuilesFabrique = fabriques[choixFabrique].getContenu();
        //Si une fabrique récupérer est vide alors on va récupérer la fabrique + 1 jusqu'à trouvé une fabrique qui n'est pas vide.
        //Si on arrive au bout de fabriques.length on recommence à partir de 0.
        int choixF = choixFabrique;
        for (int i = 0; i < fabriques.length; i++) {
            if (!tuilesFabrique.isEmpty()) {

                break;
            } else if (choixFabrique + i > fabriques.length - 1) {
                i = 0;
                choixFabrique = 0;
            }

            tuilesFabrique = fabriques[choixFabrique + i].getContenu();
            choixF = choixFabrique + i;
        }
        //Choisie une tuile aléatoirement en fonction du nombre de tuiles dans une fabrique.
        int choixTuile = rand.Random(tuilesFabrique.size());
        return new int[]{choixF, choixTuile};
    }

    /**
     * Dépose les tuiles récupérée par piocherFabrique et les ajoute au motif du plateau
     */
    public int deposerTuiles() {

        return rand.Random(5);
    }

    /**
     * Permet de faire jouer le bot pour qu'il puisse récupérer les tuiles dans des fabriques ou dans le centre table et le pose ensuite dans
     * une ligne motif
     * @param fabriques Liste des fabriques
     * @param centreTable le centre de la table
     */
    @Override
    public Object[] jouer(Fabrique[] fabriques, CentreTable centreTable, Plateau... plateaux) {
        boolean provenanceCentreTable;
        Object[] choix = new Object[3];
        if ((rand.Random(2) == 1 || centreTable.getContenu().isEmpty()) && fabriquesPleines(fabriques)) {
            int[] choixRand = piocherFabrique(fabriques);
            choix[0] = fabriques[choixRand[0]];
            choix[2] = choixRand[1];
            provenanceCentreTable = false;
        } else {

            choix[0] = centreTable;
            choix[2] = piocherCentreTable(centreTable);
            provenanceCentreTable = true;
        }
        choix[1] = deposerTuiles();

        return new Object[]{choix, provenanceCentreTable};
    }

    private boolean fabriquesPleines(Fabrique[] lesFabriques) {
        for (Fabrique laFabrique : lesFabriques) {
            if (!laFabrique.getContenu().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère aléatoirement toutes les tuiles de la même couleur d'une fabrique du centre de la table
     * @param centreTable le centre de la table
     */
    public int piocherCentreTable(CentreTable centreTable) {
        //Choix de la fabrique
        return rand.Random(centreTable.getContenu().size());

    }

    /**
     * @return les tuiles
     */
    @Override
    public ArrayList<Tuile> getTuiles() {
        return tuiles;
    }

}