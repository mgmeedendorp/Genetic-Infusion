package seremis.geninfusion.soul.entity

import seremis.geninfusion.soulnew.entity.EntitySoul
import net.minecraft.world.World

class SimulatedEntityLiving(world: World) extends EntityLiving(world: World) {
	
	def this(world: World, entity: EntitySoul) {
		this(world)
		applyData(entity)
	}
	
	def applyData(entity: EntitySoul) {
		//TODO this
	}
}