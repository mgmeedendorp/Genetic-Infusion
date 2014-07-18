package seremis.geninfusion.network.packet;

import seremis.geninfusion.entity.GIEntity;
import seremis.geninfusion.entity.GIEntityLiving;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketEntityData extends AbstractPacket {

    int entityId;
    byte[] data;
    int length;
    int id;
	
	public PacketEntityData() {
		
	}
	
	public PacketEntityData(byte[] data, int id, int entityId) {
        this.entityId = entityId;
        this.data = data;
        this.id = id;

        length = data.length;
    }

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        PacketBuffer pbuff = new PacketBuffer(buffer);
        pbuff.writeByte(id);
        pbuff.writeInt(length);
        pbuff.writeBytes(data);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketBuffer pbuff = new PacketBuffer(buffer);
        id = pbuff.readByte();
        length = pbuff.readInt();
        data = new byte[length];
        pbuff.readBytes(data);		
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		Entity ent = player.worldObj.getEntityByID(entityId);
		
		if(ent != null && ent instanceof GIEntity) {
			((GIEntity) ent).receivePacketOnClient(id, data);
		} else if(ent != null && ent instanceof GIEntityLiving) {
			((GIEntityLiving) ent).receivePacketOnClient(id, data);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		Entity ent = player.worldObj.getEntityByID(entityId);
		
		if(ent != null && ent instanceof GIEntity) {
			((GIEntity) ent).receivePacketOnServer(id, data);
		} else if(ent != null && ent instanceof GIEntityLiving) {
			((GIEntityLiving) ent).receivePacketOnServer(id, data);
		}
	}
}
