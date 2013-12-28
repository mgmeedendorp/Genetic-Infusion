package seremis.soulcraft.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import seremis.soulcraft.network.packet.SCPacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        SCPacket scPacket = PacketTypeHandler.buildPacket(packet.data);
        scPacket.execute(manager, player);
    }

}
