package seremis.soulcraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.network.PacketTypeHandler;
import seremis.soulcraft.tileentity.SCTile;
import seremis.soulcraft.tileentity.SCTileMagnetConnector;
import seremis.soulcraft.tileentity.SCTileMagnetConsumer;
import cpw.mods.fml.common.network.Player;

public class PacketTileData extends SCPacket {

    public int x;
    public int y;
    public int z;
    public int length;
    public byte[] data;
    public int id;

    public PacketTileData() {
        super(PacketTypeHandler.TILEDATA);
    }

    public PacketTileData(byte[] data, int id, int x, int y, int z) {
        super(PacketTypeHandler.TILEDATA);
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
        this.id = id;

        length = data.length;
    }

    @Override
    public void readData(DataInputStream dataStream) throws IOException {
        this.x = dataStream.readInt();
        this.y = dataStream.readInt();
        this.z = dataStream.readInt();
        this.length = dataStream.readInt();
        this.data = new byte[length];
        dataStream.readFully(data);
        this.id = dataStream.readInt();
    }

    @Override
    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(x);
        dataStream.writeInt(y);
        dataStream.writeInt(z);
        dataStream.writeInt(length);
        dataStream.write(data);
        dataStream.writeInt(id);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        if(CommonProxy.proxy.isRenderWorld(((EntityPlayer) player).worldObj)) {
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTile) {
                ((SCTile) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToClient(id, data);
            }
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTileMagnetConnector) {
                ((SCTileMagnetConnector) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToClient(id, data);
            }
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTileMagnetConsumer) {
                ((SCTileMagnetConsumer) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToClient(id, data);
            }
        } else {
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTile) {
                ((SCTile) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToServer(id, data);
            }
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTileMagnetConnector) {
                ((SCTileMagnetConnector) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToServer(id, data);
            }
            if(((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z) instanceof SCTileMagnetConsumer) {
                ((SCTileMagnetConsumer) ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z)).sendTileDataToServer(id, data);
            }
        }
    }
}
