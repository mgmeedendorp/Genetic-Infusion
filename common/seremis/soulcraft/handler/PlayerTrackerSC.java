package seremis.soulcraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTrackerSC implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        ServerTickHandler.instance.addTimerForLinkUpdateToPlayer(player);
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
