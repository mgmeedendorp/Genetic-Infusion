package seremis.geninfusion.api.util

import net.minecraft.entity.EntityLiving
import net.minecraft.util.{MathHelper, Vec3}
import seremis.geninfusion.api.soul.IEntitySoulCustom

object GIRandomPositionGenerator {
    val staticVector = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D)

    def findRandomTarget(entity: IEntitySoulCustom, areaWidth: Int, areaHeight: Int): Vec3 = {
        findRandomTargetBlock(entity, areaWidth, areaHeight, null)
    }

    def findRandomTargetBlockTowards(entity: IEntitySoulCustom, areaWidth: Int, areaY: Int, targetDirection: Vec3): Vec3 = {
        staticVector.xCoord = targetDirection.xCoord - entity.asInstanceOf[EntityLiving].posX
        staticVector.yCoord = targetDirection.yCoord - entity.asInstanceOf[EntityLiving].posY
        staticVector.zCoord = targetDirection.zCoord - entity.asInstanceOf[EntityLiving].posZ
        findRandomTargetBlock(entity, areaWidth, areaY, staticVector)
    }

    def findRandomTargetBlock(entity: IEntitySoulCustom, areaWidth: Int, areaHeight: Int, targetDirection: Vec3): Vec3 = {
        val living = entity.asInstanceOf[EntityLiving]

        val random = living.getRNG
        var flag = false
        var k = 0
        var l = 0
        var i1 = 0
        var f = -99999.0F
        var flag1: Boolean = false
        if(entity.hasHome_I) {
            val d0 = (entity.getHomePosition_I.getDistanceSquared(MathHelper.floor_double(living.posX), MathHelper.floor_double(living.posY), MathHelper.floor_double(living.posZ)) + 4.0F).toDouble
            val d1 = (entity.getMaxHomeDistance_I + areaWidth.toFloat).toDouble
            flag1 = d0 < d1 * d1
        } else {
            flag1 = false
        }
        for(l1 <- 0 until 10) {
            var j1 = random.nextInt(2 * areaWidth) - areaWidth
            var i2 = random.nextInt(2 * areaHeight) - areaHeight
            var k1 = random.nextInt(2 * areaWidth) - areaWidth
            if(targetDirection == null || j1.toDouble * targetDirection.xCoord + k1.toDouble * targetDirection.zCoord >= 0.0D) {
                j1 += MathHelper.floor_double(living.posX)
                i2 += MathHelper.floor_double(living.posY)
                k1 += MathHelper.floor_double(living.posZ)
                if(!flag1 || entity.isWithinHomeDistance_I(j1, i2, k1)) {
                    val f1 = entity.getBlockPathWeight_I(j1, i2, k1)
                    if(f1 > f) {
                        f = f1
                        k = j1
                        l = i2
                        i1 = k1
                        flag = true
                    }
                }
            }
        }
        if(flag) {
            Vec3.createVectorHelper(k.toDouble, l.toDouble, i1.toDouble)
        } else {
            null
        }
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    def findRandomTargetBlockAwayFrom(entity : IEntitySoulCustom, areaRadius : Int, areaHeight : Int, targetDirection : Vec3): Vec3 = {
        staticVector.xCoord = entity.asInstanceOf[EntityLiving].posX - targetDirection.xCoord
        staticVector.yCoord = entity.asInstanceOf[EntityLiving].posY - targetDirection.yCoord
        staticVector.zCoord = entity.asInstanceOf[EntityLiving].posZ - targetDirection.zCoord
        findRandomTargetBlock(entity, areaRadius, areaHeight, staticVector)
    }
}
