package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.util.{MathHelper, Vec3}
import net.minecraft.world.World
import seremis.geninfusion.api.soul.IEntitySoulCustom

class EntityAIFleeSunCustom(var entity: IEntitySoulCustom, var moveSpeed: Double) extends EntityAIBase {
    setMutexBits(1)

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var shelterX: Double = 0.0D
    var shelterY: Double = 0.0D
    var shelterZ: Double = 0.0D

    var world: World = entity.getWorld

    override def shouldExecute(): Boolean = {
        if (!this.world.isDaytime) {
            false
        } else if (!this.living.isBurning) {
            false
        } else if (!this.world.canBlockSeeTheSky(MathHelper.floor_double(living.posX), living.boundingBox.minY.toInt,
            MathHelper.floor_double(living.posZ))) {
            false
        } else {
            val vec3 = this.findPossibleShelter()
            if (vec3 == null) {
                false
            } else {
                this.shelterX = vec3.xCoord
                this.shelterY = vec3.yCoord
                this.shelterZ = vec3.zCoord
                true
            }
        }
    }

    override def continueExecuting(): Boolean = !this.living.getNavigator.noPath()

    override def startExecuting() {
        this.living.getNavigator.tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.moveSpeed)
    }

    private def findPossibleShelter(): Vec3 = {
        val random = this.entity.getRandom

        for (i <- 0 until 10) {
            val x = MathHelper.floor_double(living.posX + random.nextInt(20).toDouble - 10.0D)
            val y = MathHelper.floor_double(living.boundingBox.minY + random.nextInt(6).toDouble - 3.0D)
            val z = MathHelper.floor_double(living.posZ + random.nextInt(20).toDouble - 10.0D)

            if (!world.canBlockSeeTheSky(x, y, z) && entity.getBlockPathWeight(x, y, z) < 0.0F) {
                return Vec3.createVectorHelper(x.toDouble, y.toDouble, z.toDouble)
            }
        }
        null
    }
}