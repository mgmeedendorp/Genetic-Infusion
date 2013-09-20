package Seremis.SoulCraft.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.network.PacketTypeHandler;

public class PacketResetMagnetLinks extends SCPacket {

    public PacketResetMagnetLinks() {
        super(PacketTypeHandler.RESET_MAGNET_LINKS);
    }

    @Override
    public void execute(INetworkManager network, EntityPlayer player) {
        for(MagnetLink link : MagnetLinkHelper.instance.getAllLinks()) {
            MagnetLinkHelper.instance.removeLink(link);
        }
    }
}
