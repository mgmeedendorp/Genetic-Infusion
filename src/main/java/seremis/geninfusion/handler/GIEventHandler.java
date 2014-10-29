package seremis.geninfusion.handler;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import seremis.geninfusion.soul.entity.EntitySoulCustom;

public class GIEventHandler {

    @SubscribeEvent
    public void joinWorld(EntityJoinWorldEvent event) {
//        if(event.entity instanceof EntitySoulCustom) {
//            System.out.println(event.entity + " server? " + !event.world.isRemote);
//            EntitySoulCustom entity = (EntitySoulCustom)event.entity;
//            System.out.println("entity posX: " + event.entity.posX + " posY: " + event.entity.posY + " posZ: " + event.entity.posZ);
//            System.out.println("sync posX: " + entity.syncLogic().prevData().getDouble("posX") + " posY: " + entity.syncLogic().prevData().getDouble("posY") + " posZ: " + entity.syncLogic().prevData().getDouble("posZ"));
//            System.out.println("custom posX: " + entity.syncLogic().getDouble("posX") + " posY: " + entity.syncLogic().getDouble("posY") + " posZ: " + entity.syncLogic().getDouble("posZ"));
//        } else {
//            System.out.println(event.entity + " server? " + !event.world.isRemote);
//            System.out.println("posX: " + event.entity.posX + " posY: " + event.entity.posY + " posZ: " + event.entity.posZ);
//        }
    }
}
