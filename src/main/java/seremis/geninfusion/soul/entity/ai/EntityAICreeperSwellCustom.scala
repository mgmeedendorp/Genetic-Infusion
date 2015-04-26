package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import seremis.geninfusion.api.soul.IEntitySoulCustom

class EntityAICreeperSwellCustom(entity: IEntitySoulCustom) extends EntityAIBase {

    val living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var attackTarget: EntityLivingBase = null

    setMutexBits(1)

    override def shouldExecute(): Boolean = entity.getFuseState > 0 || living.getAttackTarget != null && living.getDistanceSqToEntity(attackTarget) < 9.0D

    override def startExecuting() {
        living.getNavigator.clearPathEntity()
        attackTarget = living.getAttackTarget
    }

    override def resetTask() {
        attackTarget = null
    }

    override def updateTask() {
        if(attackTarget == null) {
            entity.setFuseState(-1)
        } else if(living.getDistanceSqToEntity(attackTarget) > 49.0D) {
            entity.setFuseState(-1)
        } else if(!living.getEntitySenses.canSee(attackTarget)) {
            entity.setFuseState(-1)
        } else {
            entity.setFuseState(1)
        }
    }
}
