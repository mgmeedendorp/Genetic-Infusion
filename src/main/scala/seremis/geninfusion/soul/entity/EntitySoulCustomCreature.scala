package seremis.geninfusion.soul.entity

import net.minecraft.entity._
import net.minecraft.world.World
import seremis.geninfusion.api.soul.ISoul

class EntitySoulCustomCreature(val world: World) extends EntityCreature(world) with EntitySoulCustomCreatureTrait {

    override var soul: ISoul = null

    def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
        this(world)
        setPosition(x, y, z)
        setSize(0.8F, 1.7F)
        this.soul = soul
        entityInit
        shouldCallEntityInit = false
    }

    override def getSoul: ISoul = soul
}