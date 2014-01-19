package seremis.soulcraft.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.SoulHandler;


public class EventHandlerSC {

    @ForgeSubscribe
    public void entitySpawn(EntityJoinWorldEvent event) {
        if(CommonProxy.proxy.isServerWorld(event.entity.worldObj) && event.entity instanceof EntityLiving) {
            SoulHandler.addSoulTo((EntityLiving) event.entity);
            SoulHandler.entityInit((EntityLiving) event.entity);
        }
    }
}
