package seremis.geninfusion.soul.traits

import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.init.Blocks
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitNavigate extends Trait {

    override def updateWanderPath(entity: IEntitySoulCustom) {
        val posX = entity.getDouble(EntityPosX)
        val posY = entity.getDouble(EntityPosY)
        val posZ = entity.getDouble(EntityPosZ)

        entity.getWorld_I.theProfiler.startSection("stroll")

        var pathFound = false
        var destX = -1
        var destY = -1
        var destZ = -1
        var blockWeight = -99999.0F

        for(l <- 0 until 10) {
            val randX = MathHelper.floor_double(posX + entity.getRandom_I.nextInt(13).toDouble - 6.0D)
            val randY = MathHelper.floor_double(posY + entity.getRandom_I.nextInt(7).toDouble - 3.0D)
            val randZ = MathHelper.floor_double(posZ + entity.getRandom_I.nextInt(13).toDouble - 6.0D)
            val randBlockWeight = entity.getBlockPathWeight_I(randX, randY, randZ)

            if(randBlockWeight > blockWeight) {
                blockWeight = randBlockWeight
                destX = randX
                destY = randY
                destZ = randZ
                pathFound = true
            }
        }

        if(pathFound) {
            entity.setObject(EntityPathToEntity, entity.getWorld_I.getEntityPathToXYZ(entity.asInstanceOf[Entity], destX, destY, destZ, 10.0F, true, false, false, true))
        }
        entity.getWorld_I.theProfiler.endSection()
    }

    override def getBlockPathWeight(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Float = {
        val burnsInDaylight = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneBurnsInDaylight)
        val eatsGrass = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAIEatGrass)

        if(burnsInDaylight) {
            return 0.5F - entity.getWorld_I.getLightBrightness(x, y, z)
        }

        if(eatsGrass) {
            return if(entity.getWorld_I.getBlock(x, y - 1, z) == Blocks.grass) 10.0F else entity.getWorld_I.getLightBrightness(x, y, z) - 0.5F
        }
        0.0F
    }

    override def findPlayerToAttack(entity: IEntitySoulCustom): Entity = {
        val living = entity.asInstanceOf[EntityLiving]
        val player = entity.getWorld_I.getClosestVulnerablePlayerToEntity(entity.asInstanceOf[Entity], 16)

        val attackTargetVisible = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAttackTargetVisible)

        val minAttackBrightness = SoulHelper.geneRegistry.getValueFromAllele[Float](entity, Genes.GeneMinAttackBrightness)
        val maxAttackBrightness = SoulHelper.geneRegistry.getValueFromAllele[Float](entity, Genes.GeneMaxAttackBrightness)

        val brightness = living.getBrightness(1.0F)

        if(player != null && (living.canEntityBeSeen(player) || !attackTargetVisible) && brightness >= minAttackBrightness && brightness <= maxAttackBrightness) {
            player
        } else {
            null
        }
    }
}