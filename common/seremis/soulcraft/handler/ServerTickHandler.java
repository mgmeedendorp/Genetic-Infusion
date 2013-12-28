package seremis.soulcraft.handler;

import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.core.lib.Strings;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.util.ITimerCaller;
import seremis.soulcraft.util.Timer;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler, ITimerCaller {

    public static ServerTickHandler instance = new ServerTickHandler();

    private CopyOnWriteArrayList<Timer> timers = new CopyOnWriteArrayList<Timer>();
    
    public void addTimerForLinkUpdateToPlayer(EntityPlayer player) {
        timers.add(new Timer(player.entityId, 60, 1, this));
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        MagnetLinkHelper.instance.tick();
        
        for(Timer timer : timers) {
            timer.tick();
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return Strings.nameServerTickHandler;
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
        
        if(entity != null && entity instanceof EntityPlayer && CommonProxy.proxy.isServerWorld(entity.worldObj)) {
            MagnetLinkHelper.instance.updatePlayerWithNetworks((EntityPlayer) entity);
        }
    }
}
