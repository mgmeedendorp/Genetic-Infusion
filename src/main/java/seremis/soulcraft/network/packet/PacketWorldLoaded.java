package seremis.soulcraft.network.packet;

import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;

public class PacketWorldLoaded extends SCPacket {

    public PacketWorldLoaded() {
        super(PacketTypeHandler.WORLD_LOADED);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        MagnetLinkHelper.instance.updatePlayerWithNetworks((EntityPlayer)player);
    }
}
