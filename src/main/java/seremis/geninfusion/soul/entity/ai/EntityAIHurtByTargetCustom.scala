package seremis.geninfusion.soul.entity.ai

import java.util

import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.util.AxisAlignedBB
import seremis.geninfusion.api.soul.IEntitySoulCustom
import scala.collection.JavaConversions._

class EntityAIHurtByTargetCustom(entity: IEntitySoulCustom, callForHelp: Boolean) extends EntityAITargetCustom(entity, false) {
    setMutexBits(1)

    var entityCallsForHelp: Boolean = callForHelp
    var lastRevengeTimer: Int = 0

    override def shouldExecute(): Boolean = {
        val revengeTimer = living.getRevengeTimer
        revengeTimer != lastRevengeTimer && isSuitableTarget(living.getAITarget, false)
    }

    override def startExecuting() {
        living.setAttackTarget(living.getAITarget)

        lastRevengeTimer = living.getRevengeTimer

        if (this.entityCallsForHelp) {
            val followRange = getFollowRange
            val list = collectionAsScalaIterable(entity.getWorld.getEntitiesWithinAABB(classOf[EntityLiving], AxisAlignedBB.getBoundingBox(living.posX, living.posY, living.posZ, living.posX + 1.0D, living.posY + 1.0D, living.posZ + 1.0D).expand(followRange, 10.0D, followRange)).asInstanceOf[util.ArrayList[Entity]])

            for (ent <- list if living != ent && living.getAttackTarget == null && !living.isOnSameTeam(living.getAITarget)) {
                living.setAttackTarget(living.getAITarget)
            }
        }
        super.startExecuting()
    }
}
