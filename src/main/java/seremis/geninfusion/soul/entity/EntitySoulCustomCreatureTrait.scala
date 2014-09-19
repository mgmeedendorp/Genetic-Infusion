package seremis.geninfusion.soul.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.entity.{EntityCreature, Entity}
import net.minecraft.world.World
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.entity.GIEntityCreature

trait EntitySoulCustomCreatureTrait extends EntityCreature with EntitySoulCustomTrait with IEntityAdditionalSpawnData with IEntitySoulCustom {

  override def syncVariables() {
    super.syncVariables()
    syncCreature()
  }

  private var syncEntityToAttack: Entity = null;
  private var syncHasAttacked: Boolean = false;
  private var syncFleeingTick: Int = 0;

  private def syncCreature() {
    if(syncEntityToAttack != getEntityToAttack) {
      variableInteger.put("entityToAttack", getEntityToAttack.getEntityId)
      syncEntityToAttack = getEntityToAttack
    } else if(syncEntityToAttack.getEntityId != getInteger("entityToAttack")) {
      setEntityToAttack(worldObj.getEntityByID(getInteger("entityToAttack")))
      syncEntityToAttack = getEntityToAttack
    }
    if (syncHasAttacked != getHasAttacked) {
      variableBoolean.put("hasAttacked", getHasAttacked)
      syncHasAttacked = getHasAttacked
    } else if (syncHasAttacked != getBoolean("hasAttacked")) {
      setHasAttacked(getBoolean("hasAttacked"))
      syncHasAttacked = getHasAttacked
    }
    if (syncFleeingTick != getFleeingTick) {
      variableInteger.put("fleeingTick", getFleeingTick)
      syncFleeingTick = getFleeingTick
    } else if (syncFleeingTick != getPersistentInteger("fleeingTick")) {
      setFleeingTick(getPersistentInteger("fleeingTick"))
      syncFleeingTick = getFleeingTick
    }
  }

}

class GIEntityCreatureScala(world: World) extends GIEntityCreature(world) {

  override def getEntityToAttack = entityToAttack
  def setEntityToAttack(value: Entity) {entityToAttack = value}

  def getHasAttacked = hasAttacked
  def setHasAttacked(value: Boolean) {hasAttacked = value}

  def getFleeingTick = fleeingTick
  def setFleeingTick(value: Int) {fleeingTick = value}
}
