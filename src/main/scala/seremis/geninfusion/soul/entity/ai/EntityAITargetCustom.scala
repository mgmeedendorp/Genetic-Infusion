package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{EntityLiving, EntityLivingBase, IEntityOwnable, SharedMonsterAttributes}
import net.minecraft.util.MathHelper
import org.apache.commons.lang3.StringUtils
import seremis.geninfusion.api.soul.IEntitySoulCustom

abstract class EntityAITargetCustom(var entity: IEntitySoulCustom, checkSight: Boolean, var nearbyOnly: Boolean) extends EntityAIBase {

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var targetSearchStatus: Int = 0
    var targetSearchDelay: Int = 0
    var targetUnseenTicks: Int = 0

    def this(entity: IEntitySoulCustom, targetVisible: Boolean) {
        this(entity, targetVisible, false)
    }

    override def continueExecuting(): Boolean = {
        val attackTarget = living.getAttackTarget

        if (attackTarget != null && attackTarget.isEntityAlive) {
            val distance = getFollowRange

            if (living.getDistanceSqToEntity(attackTarget) <= distance * distance) {
                if (checkSight) {
                    if (living.getEntitySenses.canSee(attackTarget)) {
                        targetUnseenTicks = 0
                    } else if (targetUnseenTicks > 60) {
                        targetUnseenTicks += 1
                        return false
                    }
                }
                return !attackTarget.isInstanceOf[EntityPlayerMP] || !attackTarget.asInstanceOf[EntityPlayerMP].theItemInWorldManager.isCreative
            }
        }
        false
    }

    def getFollowRange: Double = {
        val attributeInstance = living.getEntityAttribute(SharedMonsterAttributes.followRange)
        if (attributeInstance == null) 16.0D else attributeInstance.getAttributeValue
    }

    override def startExecuting() {
        targetSearchStatus = 0
        targetSearchDelay = 0
        targetUnseenTicks = 0
    }

    override def resetTask() {
        living.setAttackTarget(null)
    }

    protected def isSuitableTarget(target: EntityLivingBase, targetInvincible: Boolean): Boolean = {
        if (target != null && target != living && target.isEntityAlive && living.canAttackClass(target.getClass) && !isTargetOwnerOrPet(target)) {
            if (!(target.isInstanceOf[EntityPlayer] && targetInvincible && target.asInstanceOf[EntityPlayer].capabilities.disableDamage)) {
                if (entity.isWithinHomeDistance_I(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ))) {
                    if (checkSight && !living.getEntitySenses.canSee(target)) {
                        return false
                    } else {
                        if (nearbyOnly) {
                            targetSearchDelay -= 1
                            if (targetSearchDelay <= 0) {
                                targetSearchStatus = 0
                            }
                            if (targetSearchStatus == 0) {
                                targetSearchStatus = if (canAttack(target)) 1 else 2
                            }
                            if (targetSearchStatus == 2) {
                                return false
                            }
                        }
                        return true
                    }
                }
            }
        }
        false
    }

    def isTargetOwnerOrPet(target: EntityLivingBase): Boolean = {
        (living.isInstanceOf[IEntityOwnable] && StringUtils.isNotEmpty(living.asInstanceOf[IEntityOwnable].func_152113_b())) && (target == living.asInstanceOf[IEntityOwnable].getOwner || target.isInstanceOf[IEntityOwnable] && living.asInstanceOf[IEntityOwnable].func_152113_b() == target.asInstanceOf[IEntityOwnable].func_152113_b())
    }

    private def canAttack(target: EntityLivingBase): Boolean = {
        targetSearchDelay = 10 + living.getRNG.nextInt(5)

        val path = living.getNavigator.getPathToEntityLiving(target)

        if (path != null) {
            val goal = path.getFinalPathPoint

            if (goal != null) {
                val dX = goal.xCoord - MathHelper.floor_double(target.posX)
                val dZ = goal.xCoord - MathHelper.floor_double(target.posZ)
                return (dX * dX + dZ * dZ).toDouble <= 2.25D
            }
        }
        false
    }
}
