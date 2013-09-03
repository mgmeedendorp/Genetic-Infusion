package Seremis.SoulCraft.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import Seremis.SoulCraft.network.PacketTypeHandler;

public abstract class SCPacket {

    public PacketTypeHandler packetType;

    public SCPacket(PacketTypeHandler packetType) {
        this.packetType = packetType;
    }

    public byte[] populate() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeByte(packetType.ordinal());
            this.writeData(dos);
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }
        return bos.toByteArray();
    }

    public void readPopulate(DataInputStream data) {

        try {
            this.readData(data);
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readData(DataInputStream data) throws IOException {

    }

    public void writeData(DataOutputStream dos) throws IOException {

    }

    public void execute(INetworkManager network, EntityPlayer player) {

    }

    public void setKey(int key) {

    }
}
