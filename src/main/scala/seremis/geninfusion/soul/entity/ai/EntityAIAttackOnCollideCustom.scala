package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.pathfinding.PathEntity
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import seremis.geninfusion.api.soul.IEntitySoulCustom

class EntityAIAttackOnCollideCustom(var entity: IEntitySoulCustom, moveSpeed: Double, var longMemory: Boolean) extends EntityAIBase {

    setMutexBits(3)

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var worldObj: World = entity.getWorld

    var attackTick = 0
    var speedTowardsTarget = moveSpeed
    var entityPathEntity: PathEntity = null

    var classTarget: Class[_] = null

    private var attackTimeout = 0

    private var targetX = 0.0D
    private var targetY = 0.0D
    private var targetZ = 0.0D

    private var failedPathFindingPenalty = 0

    def this(entity: IEntitySoulCustom, targetClass: Class[_], moveSpeed: Double, longMemory: Boolean) {
        this(entity, moveSpeed, longMemory)
        this.classTarget = targetClass
    }

    override def shouldExecute(): Boolean = {
        val attackTarget = this.living.getAttackTarget

        if(attackTarget != null && attackTarget.isEntityAlive && (classTarget == null || classTarget.isAssignableFrom(attackTarget.getClass))) {
            if(this.attackTimeout <= 0) {
                this.entityPathEntity = this.living.getNavigator.getPathToEntityLiving(attackTarget)
                this.attackTimeout = 4 + this.living.getRNG.nextInt(7)
                return this.entityPathEntity != null
            } else {
                return true
            }
        }
        false
    }

    override def continueExecuting(): Boolean = {
        val attackTarget = living.getAttackTarget
        attackTarget != null && (attackTarget.isEntityAlive && (if(!this.longMemory) !this.living.getNavigator.noPath() else this.entity.isWithinHomeDistance(MathHelper.floor_double(attackTarget.posX), MathHelper.floor_double(attackTarget.posY), MathHelper.floor_double(attackTarget.posZ))))
    }

    override def startExecuting() {
        living.getNavigator.setPath(this.entityPathEntity, this.speedTowardsTarget)
        attackTimeout = 0
    }

    override def resetTask() {
        living.getNavigator.clearPathEntity()
    }

    override def updateTask() {
        val attackTarget = this.living.getAttackTarget

        living.getLookHelper.setLookPositionWithEntity(attackTarget, 30.0F, 30.0F)

        val targetDistance = this.living.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ)
        val minAttackDistance = (this.living.width * 2.0F * this.living.width * 2.0F + attackTarget.width).toDouble

        this.attackTimeout -= 1

        if((this.longMemory || this.living.getEntitySenses.canSee(attackTarget)) && this.attackTimeout <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || attackTarget.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.living.getRNG.nextFloat() < 0.05F)) {
            this.targetX = attackTarget.posX
            this.targetY = attackTarget.boundingBox.minY
            this.targetZ = attackTarget.posZ
            this.attackTimeout = failedPathFindingPenalty + 4 + this.living.getRNG.nextInt(7)

            if(this.living.getNavigator.getPath != null) {
                val finalPathPoint = this.living.getNavigator.getPath.getFinalPathPoint
                if(finalPathPoint != null &&
                    attackTarget.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) <
                        1) {
                    failedPathFindingPenalty = 0
                } else {
                    failedPathFindingPenalty += 10
                }
            } else {
                failedPathFindingPenalty += 10
            }

            if(targetDistance > 1024.0D) {
                this.attackTimeout += 10
            } else if(targetDistance > 256.0D) {
                this.attackTimeout += 5
            }

            if(!this.living.getNavigator.tryMoveToEntityLiving(attackTarget, this.speedTowardsTarget)) {
                this.attackTimeout += 15
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0)

        if(targetDistance <= minAttackDistance && this.attackTick <= 20) {
            this.attackTick = 20

            if(this.living.getHeldItem != null) {
                this.living.swingItem()
            }

            this.living.attackEntityAsMob(attackTarget)
        }
    }
}