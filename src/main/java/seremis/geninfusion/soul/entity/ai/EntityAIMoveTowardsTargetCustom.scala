package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import net.minecraft.util.Vec3
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator

class EntityAIMoveTowardsTargetCustom(var entity: IEntitySoulCustom, var moveSpeed: Double, var maxDistance: Float) extends EntityAIBase {
    setMutexBits(1)

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var targetEntity: EntityLivingBase = null
    var target: Vec3 = null

    override def shouldExecute(): Boolean = {
        targetEntity = this.living.getAttackTarget
        if (targetEntity != null && targetEntity.getDistanceSqToEntity(living) <= (maxDistance * maxDistance).toDouble) {
            target = GIRandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 16, 7, Vec3.createVectorHelper(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ))
            if (target != null) {
                return true
            }
        }
        false
    }

    override def continueExecuting(): Boolean = {
        !this.living.getNavigator.noPath() && this.targetEntity.isEntityAlive && this.targetEntity.getDistanceSqToEntity(this.living) < (this.maxDistance * this.maxDistance).toDouble
    }

    override def resetTask() {
        this.targetEntity = null
    }

    override def startExecuting() {
        this.living.getNavigator.tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, this.moveSpeed)
    }
}