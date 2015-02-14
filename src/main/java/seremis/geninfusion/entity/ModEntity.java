package seremis.geninfusion.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.lib.Entities;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;

public class ModEntity {

    public static void init() {
        register();
    }

    public static void register() {
        EntityRegistry.registerModEntity(EntitySoulCustom.class, Entities.soulEntityName, Entities.soulEntityID, GeneticInfusion.instance(), 80, 1, true);
        EntityRegistry.registerModEntity(EntitySoulCustomCreature.class, Entities.soulEntityCreatureName, Entities.soulEntityCreatureID, GeneticInfusion.instance(), 80, 1, true);
    }
}
