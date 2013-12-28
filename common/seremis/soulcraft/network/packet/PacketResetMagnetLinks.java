package seremis.soulcraft.network.packet;

import net.minecraft.network.INetworkManager;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketResetMagnetLinks extends SCPacket {

    public PacketResetMagnetLinks() {
        super(PacketTypeHandler.RESET_MAGNET_LINKS);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        MagnetLinkHelper.instance.reset();
    }
}
