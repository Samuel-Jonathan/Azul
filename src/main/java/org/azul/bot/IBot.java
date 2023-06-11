package org.azul.bot;

import org.azul.fabrique.CentreTable;
import org.azul.fabrique.Fabrique;
import org.azul.fabrique.Tuile;
import org.azul.plateau.Plateau;

import java.util.ArrayList;

public interface IBot {
    Object[] jouer(Fabrique[] fabriques, CentreTable centreTable,Plateau... joueurs);
    int getId();

    ArrayList<Tuile> getTuiles();
}
