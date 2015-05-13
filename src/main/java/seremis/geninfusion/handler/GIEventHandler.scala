package seremis.geninfusion.handler

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.world.WorldEvent
import seremis.geninfusion.lib.DefaultProps
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}
import seremis.geninfusion.world.GIWorldSavedData

class GIEventHandler {

    @SubscribeEvent
    def joinWorld(event: EntityJoinWorldEvent) {
        if (event.entity.isInstanceOf[EntitySoulCustom]) {
            println(event.entity + " server? " + !event.world.isRemote)
        } else if (event.entity.isInstanceOf[EntitySoulCustomCreature]) {
            println(event.entity + " server? " + !event.world.isRemote)
        }
    }

    @SubscribeEvent
    def loadWorld(event: WorldEvent.Load) {
        event.world.perWorldStorage.setData(DefaultProps.ID, new GIWorldSavedData())
    }
}