package seremis.soulcraft.network.packet;

import ibxm.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class SCPacket {

    public PacketTypeHandler packetType;
    public boolean isChunkData = false;

    public SCPacket(PacketTypeHandler packetType) {
        this.packetType = packetType;
    }

    public byte[] populate() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeByte(packetType.ordinal());
            writeData(dos);
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }
        return bos.toByteArray();
    }

    public void readPopulate(DataInputStream data) {

        try {
            readData(data);
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readData(DataInputStream data) throws IOException {

    }

    public void writeData(DataOutputStream dos) throws IOException {

    }

    public void execute(INetworkManager network, Player player) {

    }

    public void setKey(int key) {

    }
}
