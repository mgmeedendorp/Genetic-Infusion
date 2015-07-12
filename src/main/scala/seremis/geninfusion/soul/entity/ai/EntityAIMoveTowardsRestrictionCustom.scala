package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.util.Vec3
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.util.GIRandomPositionGenerator

class EntityAIMoveTowardsRestrictionCustom(var entity: IEntitySoulCustom, moveSpeed: Double) extends EntityAIBase {

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    private var target: Vec3 = null
    private val movementSpeed: Double = moveSpeed

    this.setMutexBits(1)

    override def shouldExecute(): Boolean = {
        if (!entity.isWithinHomeDistanceCurrentPosition_I) {
            val chunkcoordinates = this.entity.getHomePosition_I
            target = GIRandomPositionGenerator.findRandomTargetBlockTowards(entity, 16, 7, Vec3.createVectorHelper(chunkcoordinates.posX.toDouble, chunkcoordinates.posY.toDouble, chunkcoordinates.posZ.toDouble))
            if (target != null) {
                return true
            }
        }
        false
    }

    override def continueExecuting(): Boolean = !this.living.getNavigator.noPath()

    override def startExecuting() {
        this.living.getNavigator.tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, this.movementSpeed)
    }
}