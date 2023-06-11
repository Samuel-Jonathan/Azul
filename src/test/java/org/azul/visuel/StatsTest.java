package org.azul.visuel;

import org.azul.bot.Bot;
import org.azul.bot.BotRandom;
import org.azul.bot.IBot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsTest {


    @Test
    public void testCalculVictoire(){

        StatsParties statsParties = new StatsParties();
        StatsParties statsParties2 = new StatsParties();

        Stats stats = new Stats();

        IBot bot1 = new Bot(1,1);
        IBot bot2 = new BotRandom(2);
        IBot bot3 = new BotRandom(2);
        IBot bot4 = new BotRandom(3);

        ArrayList<Integer> info1 = new ArrayList<>();
        ArrayList<Integer> info2 = new ArrayList<>();
        ArrayList<Integer> info3 = new ArrayList<>();
        ArrayList<Integer> info4 = new ArrayList<>();

        info1.add(bot1.getId());
        info1.add(5);

        info2.add(bot2.getId());
        info2.add(15);

        info3.add(bot3.getId());
        info3.add(3);

        info4.add(bot4.getId());
        info4.add(6);

        statsParties.getBotGagnants().add(info1);
        statsParties.getBotGagnants().add(info2);
        statsParties2.getBotGagnants().add(info3);
        statsParties2.getBotGagnants().add(info4);


        HashMap<Integer, Integer> compareMoyenneBots = new HashMap<>();

        compareMoyenneBots.put(1,1);
        compareMoyenneBots.put(2,2);
        compareMoyenneBots.put(3,1);

        stats.getStats().add(statsParties);

        stats.getStats().add(statsParties2);

        assertEquals(compareMoyenneBots, stats.calculVictoire());
    }
}
