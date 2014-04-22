package seremis.soulcraft.soul;

import seremis.soulcraft.soul.traits.TraitFire;
import seremis.soulcraft.soul.traits.TraitHandler;

public class ModSouls {

    public static void init() {
//        SoulHandler.registerHandler(new ChromosomeHandlerItemDrops());
//        SoulHandler.registerHandler(new ChromosomeHandlerFire());
//        SoulHandler.registerHandler(new ChromosomeHandlerEntityAttributes());
//        SoulHandler.registerHandler(new ChromosomeHandlerFluids());
//        SoulHandler.registerHandler(new ChromosomeHandlerSounds());
//        SoulHandler.registerHandler(new ChromosomeHandlerAttacked());
//        SoulHandler.registerHandler(new ChromosomeHandlerMovement());
        
        TraitHandler.registerEntityTrait(new TraitFire());
    }
}
