package seremis.geninfusion.handler;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;

public class GIEventHandler {

    @SubscribeEvent
    public void joinWorld(EntityJoinWorldEvent event) {
        if(event.entity instanceof EntitySoulCustom) {
            System.out.println(event.entity + " server? " + !event.world.isRemote);
        } else if(event.entity instanceof EntitySoulCustomCreature) {
            System.out.println(event.entity + " server? " + !event.world.isRemote);
        }
    }
}
