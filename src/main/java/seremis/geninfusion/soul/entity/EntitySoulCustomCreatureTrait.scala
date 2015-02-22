package seremis.geninfusion.soul.entity

import net.minecraft.entity.{Entity, EntityCreature}
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.soul.TraitHandler

trait EntitySoulCustomCreatureTrait extends EntityCreature with EntitySoulCustomTrait {

    override def attackEntity(entity: Entity, distance: Float) {
        TraitHandler.attackEntity(this, entity, distance)
    }

    override def isMovementCeased: Boolean = false

    override def findPlayerToAttack(): Entity = TraitHandler.findPlayerToAttack(this)

    override def getBlockPathWeight(x: Int, y: Int, z: Int): Float = TraitHandler.getBlockPathWeight(this, x, y, z)

    override def updateWanderPath() = TraitHandler.updateWanderPath(this)

    override def isWithinHomeDistance(x: Int, y: Int, z: Int): Boolean = TraitHandler.isWithinHomeDistance(this, x, y, z)

    override def isWithinHomeDistanceCurrentPosition: Boolean = TraitHandler.isWithinHomeDistanceCurrentPosition(this)

    override def getHomePosition: ChunkCoordinates = TraitHandler.getHomePosition(this)

    override def hasHome: Boolean = TraitHandler.hasHome(this)

    override def getMaxHomeDistance: Float = TraitHandler.getMaxHomeDistance(this)

    override def detachHome() = TraitHandler.detachHome(this)

    override def setHomeArea(x: Int, y: Int, z: Int, maxDistance: Int) = TraitHandler.setHomeArea(this, x, y, z, maxDistance)
}
