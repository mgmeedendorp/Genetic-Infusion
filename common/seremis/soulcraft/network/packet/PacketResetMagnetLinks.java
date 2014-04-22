package seremis.soulcraft.network.packet;

import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import ibxm.Player;

public class PacketResetMagnetLinks extends SCPacket {

    public PacketResetMagnetLinks() {
        super(PacketTypeHandler.RESET_MAGNET_LINKS);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        MagnetLinkHelper.instance.reset();
    }
}
