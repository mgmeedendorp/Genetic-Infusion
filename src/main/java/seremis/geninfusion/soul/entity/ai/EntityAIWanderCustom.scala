package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.util.Vec3
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator

class EntityAIWanderCustom(var entity: IEntitySoulCustom, var moveSpeed: Double) extends EntityAIBase {
    setMutexBits(1)

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var targetPosition: Vec3 = null

    override def shouldExecute(): Boolean = {
        if (living.getAge < 100 && living.getRNG.nextInt(120) == 0) {
            targetPosition = GIRandomPositionGenerator.findRandomTarget(entity, 10, 7)
        }
        targetPosition != null
    }

    override def continueExecuting(): Boolean = !living.getNavigator.noPath()

    override def startExecuting() {
        living.getNavigator.tryMoveToXYZ(targetPosition.xCoord, targetPosition.yCoord, targetPosition.zCoord, this.moveSpeed)
    }
}