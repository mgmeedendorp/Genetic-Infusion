package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.pathfinding.PathEntity
import net.minecraft.util.{MathHelper, Vec3}
import net.minecraft.village.{Village, VillageDoorInfo}
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.util.GIRandomPositionGenerator
//remove if not needed
import scala.collection.JavaConversions._

class EntityAIMoveThroughVillageCustom(var entity: IEntitySoulCustom, var moveSpeed: Double, var isNocturnal: Boolean) extends EntityAIBase {
    setMutexBits(1)

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    var pathNavigate: PathEntity = null
    var doorInfo: VillageDoorInfo = null
    var doorList: List[VillageDoorInfo] = List()

    override def shouldExecute(): Boolean = {
        resizeDoorList()

        if (!(isNocturnal && entity.getWorld.isDaytime)) {
            val village = entity.getWorld.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.living.posX),
                MathHelper.floor_double(this.living.posY), MathHelper.floor_double(this.living.posZ), 0)
            if (village != null) {
                doorInfo = findNearestDoor(village)

                if (doorInfo != null) {
                    val breakDoors = living.getNavigator.getCanBreakDoors
                    living.getNavigator.setBreakDoors(false)
                    pathNavigate = living.getNavigator.getPathToXYZ(this.doorInfo.posX.toDouble, this.doorInfo.posY.toDouble, this.doorInfo.posZ.toDouble)
                    living.getNavigator.setBreakDoors(breakDoors)

                    if (pathNavigate != null) {
                        return true
                    } else {
                        val walkGoal = GIRandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 7, Vec3.createVectorHelper(this.doorInfo.posX.toDouble, this.doorInfo.posY.toDouble, this.doorInfo.posZ.toDouble))

                        if (walkGoal != null) {
                            living.getNavigator.setBreakDoors(false)
                            pathNavigate = living.getNavigator.getPathToXYZ(walkGoal.xCoord, walkGoal.yCoord, walkGoal.zCoord)
                            living.getNavigator.setBreakDoors(breakDoors)
                            return pathNavigate != null
                        }
                    }
                }
            }
        }
        false
    }

    override def continueExecuting(): Boolean = {
        if (this.living.getNavigator.noPath()) {
            false
        } else {
            val f = this.living.width + 4.0F
            this.living.getDistanceSq(this.doorInfo.posX.toDouble, this.doorInfo.posY.toDouble, this.doorInfo.posZ.toDouble) > (f * f).toDouble
        }
    }

    override def startExecuting() {
        this.living.getNavigator.setPath(this.pathNavigate, this.moveSpeed)
    }

    override def resetTask() {
        if (this.living.getNavigator.noPath() || this.living.getDistanceSq(this.doorInfo.posX.toDouble, this.doorInfo.posY.toDouble, this.doorInfo.posZ.toDouble) < 16.0D) {
            this.doorList.add(this.doorInfo)
        }
    }

    private def findNearestDoor(village: Village): VillageDoorInfo = {
        var doorInfo: VillageDoorInfo = null

        var nearestDoorDistance = java.lang.Integer.MAX_VALUE
        val doors = village.getVillageDoorInfoList.asInstanceOf[List[VillageDoorInfo]]

        for (door <- doors) {
            val doorDistance = door.getDistanceSquared(MathHelper.floor_double(this.living.posX), MathHelper.floor_double(this.living.posY), MathHelper.floor_double(this.living.posZ))
            if (doorDistance < nearestDoorDistance && !doesDoorListContain(door)) {
                doorInfo = door
                nearestDoorDistance = doorDistance
            }
        }
        doorInfo
    }

    private def doesDoorListContain(door: VillageDoorInfo): Boolean = {
        doorList.find(doorInfo => door.posX == doorInfo.posX && door.posY == doorInfo.posY && door.posZ == doorInfo.posZ).exists(_ => true)
    }

    private def resizeDoorList() {
        if (doorList.size > 15) {
            doorList.remove(0)
        }
    }
}