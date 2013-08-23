package Seremis.SoulCraft.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import Seremis.SoulCraft.network.packet.SCPacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        SCPacket sCPacket = PacketTypeHandler.buildPacket(packet.data);
        sCPacket.execute(manager, player);
    }

}
