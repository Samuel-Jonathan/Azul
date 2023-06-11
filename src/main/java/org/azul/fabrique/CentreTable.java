package org.azul.fabrique;

import java.util.ArrayList;


/**
 * @author Samuel Jonathan
 */
public class CentreTable implements IConteneur {

    private ArrayList<Tuile> contenu;
    private boolean malusCentreTable;

    public CentreTable() {
        this.contenu = new ArrayList<>();
        this.malusCentreTable = false;
    }

    /**
     *
     * @return Retourne le contenu du centre de la table
     */
    public ArrayList<Tuile> getContenu() {
        return contenu;
    }

    /**
     *
     * @param tuiles Liste des tuiles qui vont être ajoutées dans le centre de la table
     */
    public void addContenu(ArrayList<Tuile> tuiles) {
        contenu.addAll(tuiles);
    }

    /**
     *
     * @return Retourne true si le malus dans le centre de la table existe pas ou false si il existe
     */
    public boolean getMalusCentreTable() {
        return this.malusCentreTable;
    }

    /**
     *
     * @param malusCentreTable Malus du centre de la table (true si il existe pas ou false si il existe)
     */
    public void setMalusCentreTable(boolean malusCentreTable) {
        this.malusCentreTable = malusCentreTable;
    }

    public void resetMalusCentreTable() {
        this.malusCentreTable = false;
    }

    /**
     *
     * @return Retourne l'affichage du centre de la table
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i<contenu.size();++i) {
            buf.append(contenu.get(i));
            if(i<contenu.size()-1) buf.append(", ");
        }
        return buf.append("]").toString();
    }
}