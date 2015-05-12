package seremis.geninfusion.entity

import cpw.mods.fml.common.registry.EntityRegistry
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.lib.Entities
import seremis.geninfusion.soul.entity.{EntitySoulCustomCreature, EntitySoulCustom}

object ModEntity {

    def init() {
        EntityRegistry.registerModEntity(classOf[EntitySoulCustom], Entities.soulEntityName, Entities.soulEntityID, GeneticInfusion.instance, 80, 3, false)
        EntityRegistry.registerModEntity(classOf[EntitySoulCustomCreature], Entities.soulEntityCreatureName, Entities.soulEntityCreatureID, GeneticInfusion.instance, 80, 3, false)
    }
}
