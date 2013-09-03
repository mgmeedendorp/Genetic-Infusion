package Seremis.SoulCraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.tileentity.SCTile;
import Seremis.SoulCraft.tileentity.SCTileMagnetConnector;

public class PacketTileData extends SCPacket {

    public int x;
    public int y;
    public int z;
    public int data;
    public int id;

    public PacketTileData() {
        super(PacketTypeHandler.TILEDATA);
    }

    public PacketTileData(int data, int id, int x, int y, int z) {
        super(PacketTypeHandler.TILEDATA);
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
        this.id = id;
    }

    public void readData(DataInputStream dataStream) throws IOException {
        this.x = dataStream.readInt();
        this.y = dataStream.readInt();
        this.z = dataStream.readInt();
        this.data = dataStream.readInt();
        this.id = dataStream.readInt();
    }

    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(x);
        dataStream.writeInt(y);
        dataStream.writeInt(z);
        dataStream.writeInt(data);
        dataStream.writeInt(id);
    }
    
    public void execute(INetworkManager network, EntityPlayer player) {
        if(player.worldObj.getBlockTileEntity(x, y, z) instanceof SCTile) 
            ((SCTile)player.worldObj.getBlockTileEntity(x, y, z)).sendTileData(id, data);
        if(player.worldObj.getBlockTileEntity(x, y, z) instanceof SCTileMagnetConnector) 
            ((SCTileMagnetConnector)player.worldObj.getBlockTileEntity(x, y, z)).sendTileData(id, data);
    }
}
