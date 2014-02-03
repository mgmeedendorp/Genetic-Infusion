package seremis.soulcraft.soul;

import seremis.soulcraft.soul.actions.ChromosomeHandlerFire;
import seremis.soulcraft.soul.actions.ChromosomeItemDrops;
import seremis.soulcraft.soul.actions.ChromosomeMaxHealth;

public class ModSouls {

    public static void init() {
        SoulHandler.registerHandler(new ChromosomeItemDrops());
        SoulHandler.registerHandler(new ChromosomeHandlerFire());
        SoulHandler.registerHandler(new ChromosomeMaxHealth());
    }
}
