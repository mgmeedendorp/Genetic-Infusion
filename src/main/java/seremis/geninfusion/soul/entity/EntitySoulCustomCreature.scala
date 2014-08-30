package seremis.geninfusion.soul.entity

import net.minecraft.world.World
import seremis.geninfusion.api.soul.{ISoul, IEntitySoulCustom}

class EntitySoulCustomCreature(world: World) extends GIEntityCreatureScala(world) with EntitySoulCustomCreatureTrait with IEntitySoulCustom {

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }
}
