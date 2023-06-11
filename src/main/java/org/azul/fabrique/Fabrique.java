package org.azul.fabrique;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Samuel Jonathan
 */
public class Fabrique implements IConteneur {

    //Tuiles des fabriques
    private ArrayList<Tuile> tuiles;

    /**
     *
     * @param values Les tuiles
     */
    public Fabrique(Tuile ...values) {
        this.tuiles = new ArrayList<>(4);
        //Ajoute les tuiles dans une fabrique
        Collections.addAll(this.tuiles, values);
    }

    /**
     * @return Retourne les tuiles dans la fabrique
     */
    public ArrayList<Tuile> getContenu() {
        return tuiles;
    }

}
