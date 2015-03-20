package seremis.geninfusion.handler;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import seremis.geninfusion.lib.DefaultProps;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;
import seremis.geninfusion.world.GIWorldSavedData;

public class GIEventHandler {

    @SubscribeEvent
    public void joinWorld(EntityJoinWorldEvent event) {
        if(event.entity instanceof EntitySoulCustom) {
            System.out.println(event.entity + " server? " + !event.world.isRemote);
        } else if(event.entity instanceof EntitySoulCustomCreature) {
            System.out.println(event.entity + " server? " + !event.world.isRemote);
        }
    }

    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        event.world.perWorldStorage.setData(DefaultProps.ID(), new GIWorldSavedData());
    }
}
