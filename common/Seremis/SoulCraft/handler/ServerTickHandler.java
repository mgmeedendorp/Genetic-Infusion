package Seremis.SoulCraft.handler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.core.lib.Strings;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.util.Timer;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler {

    public static ServerTickHandler instance = new ServerTickHandler();

    public List<Timer> timers = new ArrayList<Timer>();

    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    public Timer getTimer(int id) {
        for(Timer timer : timers) {
            if(timer.timerId == id) {
                return timer;
            }
        }
        return null;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        Iterator<Timer> it = timers.iterator();
        
        while(it.hasNext()) {
            Timer timer = it.next();

            timer.tick();

            if(timer.timerEnd) {
                timerEnd(timer.timerId);
                it.remove();
            }
        }
        MagnetLinkHelper.instance.tick();
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return Strings.nameServerTickHandler;
    }

    public void timerEnd(int timerId) {
        Entity entity = null;

        MagnetLinkHelper.instance.reset();

        for(WorldServer world : MinecraftServer.getServer().worldServers) {
            entity = world.getEntityByID(timerId);
            if(entity != null) {
                break;
            }
        }

        if(entity != null && entity instanceof EntityPlayer && CommonProxy.proxy.isServerWorld(entity.worldObj)) {
            MagnetLinkHelper.instance.updatePlayerWithNetworks((EntityPlayer) entity);
        }
    }
}
