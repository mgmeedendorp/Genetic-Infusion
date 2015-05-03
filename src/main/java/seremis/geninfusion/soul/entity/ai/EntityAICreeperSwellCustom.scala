package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.lib.VariableLib
import seremis.geninfusion.api.soul.util.DataWatcherHelper

import java.lang.Byte

class EntityAICreeperSwellCustom(entity: IEntitySoulCustom) extends EntityAIBase {

    val living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var attackTarget: EntityLivingBase = null

    setMutexBits(1)

    override def shouldExecute(): Boolean = getFuseState > 0 || living.getAttackTarget != null && living.getDistanceSqToEntity(living.getAttackTarget) < 9.0D

    override def startExecuting() {
        living.getNavigator.clearPathEntity()
        attackTarget = living.getAttackTarget
    }

    override def resetTask() {
        attackTarget = null
    }

    override def updateTask() {
        if(attackTarget == null) {
            setFuseState(-1)
        } else if(living.getDistanceSqToEntity(attackTarget) > 49.0D) {
            setFuseState(-1)
        } else if(!living.getEntitySenses.canSee(attackTarget)) {
            setFuseState(-1)
        } else {
            setFuseState(1)
        }
    }

    def setFuseState(state: Int) = DataWatcherHelper.updateObject(living.getDataWatcher, VariableLib.ENTITY_FUSE_STATE, state.toByte.asInstanceOf[Byte])

    def getFuseState: Int = DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, VariableLib.ENTITY_FUSE_STATE).asInstanceOf[Byte].toInt
}
