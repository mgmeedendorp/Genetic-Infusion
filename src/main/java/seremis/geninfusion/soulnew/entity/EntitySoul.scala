package seremis.geninfusion.soul.entity

import net.minecraft.entity._
import seremis.geninfusion.soulnew.entity.simulated.SimulatedEntityLiving;

class EntitySoul(world: World) extends EntityLiving(world) {

  var soul: ISoul = null

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }

  def getSimulatedEntity(): SimulatedEntityLiving = {
    new SimulatedEntityLiving(this)
  }
}