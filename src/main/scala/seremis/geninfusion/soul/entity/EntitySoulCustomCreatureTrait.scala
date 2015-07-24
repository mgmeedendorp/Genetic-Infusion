package seremis.geninfusion.soul.entity

import net.minecraft.entity.{Entity, EntityCreature}
import seremis.geninfusion.soul.TraitHandler

trait EntitySoulCustomCreatureTrait extends EntityCreature with EntitySoulCustomTrait {

    override def attackEntity(entity: Entity, distance: Float) = attackEntity_I(entity, distance)
    override def attackEntity_I(entity: Entity, distance: Float) {
        TraitHandler.attackEntity(this, entity, distance)
    }

    override def isMovementCeased = isMovementCeased_I

    override def findPlayerToAttack = findPlayerToAttack_I

    override def getBlockPathWeight(x: Int, y: Int, z: Int) = getBlockPathWeight_I(x, y, z)

    override def updateWanderPath = updateWanderPath_I

    override def isWithinHomeDistance(x: Int, y: Int, z: Int) = isWithinHomeDistance_I(x, y, z)

    override def isWithinHomeDistanceCurrentPosition = isWithinHomeDistanceCurrentPosition_I

    override def getHomePosition = getHomePosition_I

    override def hasHome = hasHome_I

    override def getMaximumHomeDistance = getMaxHomeDistance_I

    override def detachHome = detachHome_I

    override def setHomeArea(x: Int, y: Int, z: Int, maxDistance: Int) = setHomeArea_I(x, y, z, maxDistance)
}
