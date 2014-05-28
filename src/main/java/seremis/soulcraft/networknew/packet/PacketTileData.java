package seremis.soulcraft.networknew.packet;

import seremis.soulcraft.tileentity.SCTile;
import seremis.soulcraft.tileentity.SCTileMagnetConnector;
import seremis.soulcraft.tileentity.SCTileMagnetConsumer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketTileData extends AbstractPacket {

    public int x;
    public int y;
    public int z;
    public int length;
    public byte[] data;
    public int id;
	
	public PacketTileData() {
		
	}
	
	public PacketTileData(byte[] data, int id, int x, int y, int z) {
		this.x = x;
	    this.y = y;
	    this.z = z;
	    this.data = data;
	    this.id = id;

	    length = data.length;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        PacketBuffer pbuff = new PacketBuffer(buffer);
        pbuff.writeByte(id);
        pbuff.writeInt(x);
        pbuff.writeShort(y);
        pbuff.writeInt(z);
        pbuff.writeInt(length);
        pbuff.writeBytes(data);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        PacketBuffer pbuff = new PacketBuffer(buffer);
        id = pbuff.readByte();
        x = pbuff.readInt();
        y = pbuff.readShort();
        z = pbuff.readInt();
        length = pbuff.readInt();
        data = new byte[length];
        pbuff.readBytes(data);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if(player.worldObj.getTileEntity(x, y, z) instanceof SCTile) {
            ((SCTile) player.worldObj.getTileEntity(x, y, z)).sendTileDataToClient(id, data);
        }
        if(player.worldObj.getTileEntity(x, y, z) instanceof SCTileMagnetConnector) {
            ((SCTileMagnetConnector) player.worldObj.getTileEntity(x, y, z)).sendTileDataToClient(id, data);
        }
        if(player.worldObj.getTileEntity(x, y, z) instanceof SCTileMagnetConsumer) {
            ((SCTileMagnetConsumer) player.worldObj.getTileEntity(x, y, z)).sendTileDataToClient(id, data);
        }
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if(player.worldObj.getTileEntity(x, y, z) instanceof SCTile) {
            ((SCTile) player.worldObj.getTileEntity(x, y, z)).sendTileDataToServer(id, data);
        }
        if(player.worldObj.getTileEntity(x, y, z) instanceof SCTileMagnetConnector) {
            ((SCTileMagnetConnector) player.worldObj.getTileEntity(x, y, z)).sendTileDataToServer(id, data);
        }
        if(player.worldObj.getTileEntity(x, y, z) instanceof SCTileMagnetConsumer) {
            ((SCTileMagnetConsumer) player.worldObj.getTileEntity(x, y, z)).sendTileDataToServer(id, data);
        }
	}
}
