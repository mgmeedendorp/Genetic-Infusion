package seremis.geninfusion.core.proxy

import net.minecraft.entity.Entity
import net.minecraft.world.World

class CommonProxy {

    def registerRendering() {}

    def removeEntity(entity: Entity) = entity.worldObj.removeEntity(entity)

    def isRenderWorld(world: World): Boolean = world.isRemote

    def  isServerWorld(world: World): Boolean = !world.isRemote

    def playerName(): String = ""

    def registerHandlers() {}
}
