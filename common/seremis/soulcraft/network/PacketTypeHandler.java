package seremis.soulcraft.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import seremis.soulcraft.core.lib.DefaultProps;
import seremis.soulcraft.network.packet.PacketAddMagnetLink;
import seremis.soulcraft.network.packet.PacketEntityData;
import seremis.soulcraft.network.packet.PacketHeatMagnetLink;
import seremis.soulcraft.network.packet.PacketRemoveMagnetLink;
import seremis.soulcraft.network.packet.PacketRemoveMagnetLinkConnector;
import seremis.soulcraft.network.packet.PacketResetMagnetLinks;
import seremis.soulcraft.network.packet.PacketTileData;
import seremis.soulcraft.network.packet.PacketWorldLoaded;
import seremis.soulcraft.network.packet.SCPacket;

public enum PacketTypeHandler {

    TILEDATA(PacketTileData.class), ADD_MAGNET_LINK(PacketAddMagnetLink.class), REMOVE_MAGNET_LINK(PacketRemoveMagnetLink.class), REMOVE_MAGNET_LINK_CONNECTOR(PacketRemoveMagnetLinkConnector.class), RESET_MAGNET_LINKS(PacketResetMagnetLinks.class), ENTITY_DATA(PacketEntityData.class), HEAT_MAGNET_LINKS(PacketHeatMagnetLink.class), WORLD_LOADED(PacketWorldLoaded.class);

    private Class<? extends SCPacket> clazz;

    PacketTypeHandler(Class<? extends SCPacket> clazz) {
        this.clazz = clazz;
    }

    public static SCPacket buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        SCPacket packet = null;

        try {
            packet = values()[selector].clazz.newInstance();
        }

        catch(Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);
        return packet;
    }

    public static SCPacket buildPacket(PacketTypeHandler type) {
        SCPacket packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }

        catch(Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(SCPacket packet) {
        byte[] data = packet.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = DefaultProps.PACKET_CHANNEL;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packet.isChunkData;

        return packet250;
    }
}
