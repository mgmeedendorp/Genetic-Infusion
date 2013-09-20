package Seremis.SoulCraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import Seremis.SoulCraft.util.Timer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerHandlerSC implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        ServerTickHandler.instance.addTimer(new Timer(player.entityId, 50));
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {

    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {

    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {

    }

}
