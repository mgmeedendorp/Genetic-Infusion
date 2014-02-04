package seremis.soulcraft.soul;

import seremis.soulcraft.soul.handler.ChromosomeHandlerEntityAttributes;
import seremis.soulcraft.soul.handler.ChromosomeHandlerFire;
import seremis.soulcraft.soul.handler.ChromosomeHandlerFluids;
import seremis.soulcraft.soul.handler.ChromosomeHandlerItemDrops;
import seremis.soulcraft.soul.handler.ChromosomeHandlerSounds;

public class ModSouls {

    public static void init() {
        SoulHandler.registerHandler(new ChromosomeHandlerItemDrops());
        SoulHandler.registerHandler(new ChromosomeHandlerFire());
        SoulHandler.registerHandler(new ChromosomeHandlerEntityAttributes());
        SoulHandler.registerHandler(new ChromosomeHandlerFluids());
        SoulHandler.registerHandler(new ChromosomeHandlerSounds());
    }
}
