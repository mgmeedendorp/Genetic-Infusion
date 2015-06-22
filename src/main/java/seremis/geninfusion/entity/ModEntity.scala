package seremis.geninfusion.entity

import cpw.mods.fml.common.registry.EntityRegistry
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.lib.Entities
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}

object ModEntity {

    def init() {
        EntityRegistry.registerModEntity(classOf[EntitySoulCustom], Entities.SoulEntityName, Entities.SoulEntityID, GeneticInfusion.instance, 80, 3, false)
        EntityRegistry.registerModEntity(classOf[EntitySoulCustomCreature], Entities.SoulEntityCreatureName, Entities.SoulEntityCreatureID, GeneticInfusion.instance, 80, 3, false)
    }
}
