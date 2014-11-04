package seremis.geninfusion.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import seremis.geninfusion.api.magnet.MagnetLinkHelper;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.util.ITimerCaller;
import seremis.geninfusion.util.Timer;

import java.util.concurrent.CopyOnWriteArrayList;

public class ServerTickHandler implements ITimerCaller {

    public static ServerTickHandler instance = new ServerTickHandler();

    private CopyOnWriteArrayList<Timer> timers = new CopyOnWriteArrayList<Timer>();

    public void addTimerForLinkUpdateToPlayer(EntityPlayer player) {
        timers.add(new Timer(player.getEntityId(), 60, 1, this));
    }

    @SubscribeEvent
    public void tickEnd(TickEvent event) {
        MagnetLinkHelper.instance.tick();

        for(Timer timer : timers) {
            timer.tick();
        }
    }

    @Override
    public void timerTick(Timer timer) {
        Entity entity = null;

        System.out.println("TimerEnds");

        timers.remove(timer);

        for(WorldServer world : MinecraftServer.getServer().worldServers) {
            entity = world.getEntityByID(timer.timerId);
            if(entity != null) {
                break;
            }
        }

        System.out.println("Timer ended. Entity: " + entity + " With id: " + timer.timerId);

        if(entity != null && entity instanceof EntityPlayer && CommonProxy.instance.isServerWorld(entity.worldObj)) {
            MagnetLinkHelper.instance.updatePlayerWithNetworks((EntityPlayer) entity);
        }
    }
}
