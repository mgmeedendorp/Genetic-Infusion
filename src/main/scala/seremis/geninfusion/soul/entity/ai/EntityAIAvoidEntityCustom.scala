package seremis.geninfusion.soul.entity.ai

import net.minecraft.command.IEntitySelector
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.pathfinding.PathEntity
import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.GIRandomPositionGenerator

class EntityAIAvoidEntityCustom(var entity: IEntitySoulCustom, avoidClass: Class[_], avoidDistance: Float,  var farSpeed: Double, var nearSpeed: Double) extends EntityAIBase {
    setMutexBits(1)

    val entitySelector = new IEntitySelector() {
        override def isEntityApplicable(entity: Entity): Boolean = entity.isEntityAlive && EntityAIAvoidEntityCustom.this.living.getEntitySenses.canSee(entity)
    }

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var closestAvoidEntity: Entity = null
    var distanceFromEntity = avoidDistance
    var avoidPath: PathEntity = _
    var entityNavigator = living.getNavigator

    override def shouldExecute(): Boolean = {
        if (this.avoidClass == classOf[EntityPlayer]) {
            if (SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneIsTameable) && entity.isTamed_I) {
                return false
            }

            closestAvoidEntity = entity.getWorld_I.getClosestPlayerToEntity(living, distanceFromEntity)

            if (closestAvoidEntity == null) {
                return false
            }
        } else {
            val entities = entity.getWorld_I.selectEntitiesWithinAABB(avoidClass, living.boundingBox.expand(distanceFromEntity, 3.0D, distanceFromEntity), entitySelector)

            if (entities.isEmpty) {
                return false
            }

            this.closestAvoidEntity = entities.get(0).asInstanceOf[Entity]
        }
        val moveTarget = GIRandomPositionGenerator.findRandomTargetBlockAwayFrom(entity, 16, 7, Vec3.createVectorHelper(this.closestAvoidEntity.posX, this.closestAvoidEntity.posY, this.closestAvoidEntity.posZ))

        if (moveTarget == null) {
            false
        } else if (closestAvoidEntity.getDistanceSq(moveTarget.xCoord, moveTarget.yCoord, moveTarget.zCoord) < closestAvoidEntity.getDistanceSqToEntity(living)) {
            false
        } else {
            avoidPath = entityNavigator.getPathToXYZ(moveTarget.xCoord, moveTarget.yCoord, moveTarget.zCoord)
            avoidPath != null && avoidPath.isDestinationSame(moveTarget)
        }
    }

    override def continueExecuting(): Boolean = !entityNavigator.noPath()

    override def startExecuting() {
        entityNavigator.setPath(avoidPath, farSpeed)
    }

    override def resetTask() {
        closestAvoidEntity = null
    }

    override def updateTask() {
        if (living.getDistanceSqToEntity(closestAvoidEntity) < 49.0D) {
            living.getNavigator.setSpeed(nearSpeed)
        } else {
            living.getNavigator.setSpeed(farSpeed)
        }
    }
}