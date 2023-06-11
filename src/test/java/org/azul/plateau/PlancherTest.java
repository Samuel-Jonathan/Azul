package org.azul.plateau;


import org.azul.visuel.StatsParties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlancherTest {

    private Plancher plancher = new Plancher();

    @Test
    public void testMalus() {
        StatsParties stats = new StatsParties();
        assertEquals(0, plancher.getMalus());

        plancher.ajouterAuPlancher(1,stats, 0);
        assertEquals(1, plancher.getMalus());
        plancher.ajouterAuPlancher(3,stats, 0);
        assertEquals(6, plancher.getMalus());
        plancher.ajouterAuPlancher(3,stats, 0);
        assertEquals(14, plancher.getMalus());
        plancher.ajouterAuPlancher(11,stats, 0);
        assertEquals(14,plancher.getMalus());
        plancher.resetMalus();
        assertEquals(0,plancher.getMalus());
    }
}