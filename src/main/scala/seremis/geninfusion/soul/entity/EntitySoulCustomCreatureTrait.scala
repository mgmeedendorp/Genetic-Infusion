package seremis.geninfusion.soul.entity

import net.minecraft.entity.{Entity, EntityCreature}
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.soul.TraitHandler

trait EntitySoulCustomCreatureTrait extends EntityCreature with EntitySoulCustomTrait {

    override def attackEntity(entity: Entity, distance: Float) = attackEntity_I(entity, distance)
    override def attackEntity_I(entity: Entity, distance: Float) {
        TraitHandler.attackEntity(this, entity, distance)
    }

    override def isMovementCeased = isMovementCeased_I
    override def isMovementCeased_I: Boolean = false

    override def findPlayerToAttack = findPlayerToAttack_I
    override def findPlayerToAttack_I: Entity = TraitHandler.findPlayerToAttack(this)

    override def getBlockPathWeight(x: Int, y: Int, z: Int) = getBlockPathWeight_I(x, y, z)
    override def getBlockPathWeight_I(x: Int, y: Int, z: Int): Float = TraitHandler.getBlockPathWeight(this, x, y, z)

    override def updateWanderPath = updateWanderPath_I
    override def updateWanderPath_I = TraitHandler.updateWanderPath(this)

    override def isWithinHomeDistance(x: Int, y: Int, z: Int) = isWithinHomeDistance_I(x, y, z)
    override def isWithinHomeDistance_I(x: Int, y: Int, z: Int): Boolean = TraitHandler.isWithinHomeDistance(this, x, y, z)

    override def isWithinHomeDistanceCurrentPosition = isWithinHomeDistanceCurrentPosition_I
    override def isWithinHomeDistanceCurrentPosition_I: Boolean = TraitHandler.isWithinHomeDistanceCurrentPosition(this)

    override def getHomePosition = getHomePosition_I
    override def getHomePosition_I: ChunkCoordinates = TraitHandler.getHomePosition(this)

    override def hasHome = hasHome_I
    override def hasHome_I: Boolean = TraitHandler.hasHome(this)

    override def getMaximumHomeDistance = getMaxHomeDistance_I
    override def getMaxHomeDistance_I: Float = TraitHandler.getMaxHomeDistance(this)

    override def detachHome = detachHome_I
    override def detachHome_I = TraitHandler.detachHome(this)

    override def setHomeArea(x: Int, y: Int, z: Int, maxDistance: Int) = setHomeArea_I(x, y, z, maxDistance)
    override def setHomeArea_I(x: Int, y: Int, z: Int, maxDistance: Int) = TraitHandler.setHomeArea(this, x, y, z, maxDistance)
}
