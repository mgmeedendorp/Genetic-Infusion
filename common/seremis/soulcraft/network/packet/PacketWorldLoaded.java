package seremis.soulcraft.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketWorldLoaded extends SCPacket {

    public PacketWorldLoaded() {
        super(PacketTypeHandler.WORLD_LOADED);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        MagnetLinkHelper.instance.updatePlayerWithNetworks((EntityPlayer)player);
    }
}
