package Seremis.SoulCraft.network.packet;

import net.minecraft.network.INetworkManager;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.network.PacketTypeHandler;
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
