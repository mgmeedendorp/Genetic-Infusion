package Seremis.SoulCraft.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import Seremis.SoulCraft.core.lib.DefaultProps;
import Seremis.SoulCraft.network.packet.PacketSC;

public enum PacketTypeHandler 
{
    ;
    private Class<? extends PacketSC> clazz;

    PacketTypeHandler(Class<? extends PacketSC> clazz) 
    {
        this.clazz = clazz;
    }

    public static PacketSC buildPacket(byte[] data) 
    {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketSC packet = null;

        try 
        {
            packet = values()[selector].clazz.newInstance();
        }

        catch (Exception e) 
        {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);
        return packet;
    }

    public static PacketSC buildPacket(PacketTypeHandler type) 
    {
        PacketSC packet = null;

        try 
        {
            packet = values()[type.ordinal()].clazz.newInstance();
        }

        catch (Exception e) 
        {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketSC packet)
    {
        byte[] data = packet.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = DefaultProps.PACKET_CHANNEL;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packet.isChunkDataPacket;

        return packet250;
    }
}
