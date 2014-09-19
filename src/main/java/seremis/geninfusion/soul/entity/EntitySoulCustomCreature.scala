package seremis.geninfusion.soul.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.entity.Entity
import net.minecraft.world.World
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul}
import seremis.geninfusion.entity.GIEntityCreature

class EntitySoulCustomCreature(world: World) extends GIEntityCreature(world) with EntitySoulCustomCreatureTrait with IEntitySoulCustom with IEntityAdditionalSpawnData {

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }


  override def getEntityToAttack = entityToAttack
  def setEntityToAttack(value: Entity) {entityToAttack = value}

  def getHasAttacked = hasAttacked
  def setHasAttacked(value: Boolean) {hasAttacked = value}

  def getFleeingTick = fleeingTick
  def setFleeingTick(value: Int) {fleeingTick = value}
}
