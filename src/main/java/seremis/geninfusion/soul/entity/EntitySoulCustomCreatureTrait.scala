package seremis.geninfusion.soul.entity

import net.minecraft.entity.{Entity, EntityCreature}
import seremis.geninfusion.soul.TraitHandler

trait EntitySoulCustomCreatureTrait extends EntityCreature with EntitySoulCustomTrait {

    override def attackEntity(entity: Entity, distance: Float) {
        TraitHandler.attackEntity(this, entity, distance)
    }

    override def isMovementCeased: Boolean = false

    override def findPlayerToAttack(): Entity = TraitHandler.findPlayerToAttack(this)

    override def getBlockPathWeight(x: Int, y: Int, z: Int): Float = TraitHandler.getBlockPathWeight(this, x, y, z)

    override def updateWanderPath() = TraitHandler.updateWanderPath(this)
}
