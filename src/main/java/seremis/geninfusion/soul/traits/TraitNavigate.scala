package seremis.geninfusion.soul.traits

import net.minecraft.entity.Entity
import net.minecraft.init.Blocks
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitNavigate extends Trait {

    override def updateWanderPath(entity: IEntitySoulCustom) {
        val posX = entity.getDouble(ENTITY_POS_X)
        val posY = entity.getDouble(ENTITY_POS_Y)
        val posZ = entity.getDouble(ENTITY_POS_Z)

        entity.getWorld.theProfiler.startSection("stroll")

        var pathFound = false
        var destX = -1
        var destY = -1
        var destZ = -1
        var blockWeight = -99999.0F

        for(l <- 0 until 10) {
            val randX = MathHelper.floor_double(posX + entity.getRandom.nextInt(13).toDouble - 6.0D)
            val randY = MathHelper.floor_double(posY + entity.getRandom.nextInt(7).toDouble - 3.0D)
            val randZ = MathHelper.floor_double(posZ + entity.getRandom.nextInt(13).toDouble - 6.0D)
            val randBlockWeight = entity.getBlockPathWeight(randX, randY, randZ)

            if(randBlockWeight > blockWeight) {
                blockWeight = randBlockWeight
                destX = randX
                destY = randY
                destZ = randZ
                pathFound = true
            }
        }

        if(pathFound) {
            entity.setObject(ENTITY_PATH_TO_ENTITY, entity.getWorld.getEntityPathToXYZ(entity.asInstanceOf[Entity], destX, destY, destZ, 10.0F, true, false, false, true))
        }
        entity.getWorld.theProfiler.endSection()
    }

    override def getBlockPathWeight(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Float = {
        val burnsInDaylight = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_BURNS_IN_DAYLIGHT)
        val eatsGrass = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_EAT_GRASS)

        if(burnsInDaylight) {
            return 0.5F - entity.getWorld.getLightBrightness(x, y, z)
        }

        if(eatsGrass) {
            return if(entity.getWorld.getBlock(x, y - 1, z) == Blocks.grass) 10.0F else entity.getWorld.getLightBrightness(x, y, z) - 0.5F
        }
        0.0F
    }

    override def findPlayerToAttack(entity: IEntitySoulCustom): Entity = {
        entity.getWorld.getClosestPlayerToEntity(entity.asInstanceOf[Entity], 50)
    }
}