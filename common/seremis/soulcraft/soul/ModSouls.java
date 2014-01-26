package seremis.soulcraft.soul;

import seremis.soulcraft.soul.actions.ChromosomeBurnsInDaylight;
import seremis.soulcraft.soul.actions.ChromosomeItemDrops;
import seremis.soulcraft.soul.actions.ChromosomeMaxHealth;

public class ModSouls {

    public static void init() {
        SoulHandler.registerHandler(new ChromosomeItemDrops());
        SoulHandler.registerHandler(new ChromosomeBurnsInDaylight());
        SoulHandler.registerHandler(new ChromosomeMaxHealth());
    }
}
