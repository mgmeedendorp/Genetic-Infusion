package seremis.soulcraft.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.SoulHandler;


public class EventHandlerSC {

    @ForgeSubscribe
    public void entitySpawn(EntityJoinWorldEvent event) {
        if(CommonProxy.proxy.isServerWorld(event.entity.worldObj) && event.entity instanceof EntityLivingBase) {
            SoulHandler.instance.addSoulTo((EntityLivingBase) event.entity);
        }
    }
    
    @ForgeSubscribe
    public void entityRightClick(EntityInteractEvent event) {
        if(CommonProxy.proxy.isServerWorld(event.entity.worldObj) && event.target instanceof EntityLivingBase) {
            SoulHandler.instance.entityRightClicked((EntityLivingBase) event.target, event.entityPlayer);
        }
    }
    
}
