package Seremis.SoulCraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.entity.SCEntity;
import Seremis.SoulCraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketEntityData extends SCPacket {

    int entityId;
    byte[] data;
    int length;
    int id;
    
    public PacketEntityData() {
        super(PacketTypeHandler.ENTITY_DATA);
    }
    
    public PacketEntityData(byte[] data, int id, int entityId) {
        super(PacketTypeHandler.ENTITY_DATA);
        this.entityId = entityId;
        this.data = data;
        this.id = id;
        
        length = data.length;
    }
    
    @Override
    public void readData(DataInputStream dataStream) throws IOException {
        this.entityId = dataStream.readInt();
        this.length = dataStream.readInt();
        this.data = new byte[length];
        dataStream.readFully(data);
        this.id = dataStream.readInt();
    }

    @Override
    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(entityId);
        dataStream.writeInt(length);
        dataStream.write(data);
        dataStream.writeInt(id);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        Entity ent = ((EntityPlayer)player).worldObj.getEntityByID(entityId);
        
        if(ent != null && ent instanceof SCEntity) {
            if(CommonProxy.proxy.isRenderWorld(((EntityPlayer)player).worldObj)) {
                ((SCEntity)ent).receivePacketOnClient(id, data);
            }
            if(CommonProxy.proxy.isServerWorld(((EntityPlayer)player).worldObj)) {
                ((SCEntity)ent).receivePacketOnServer(id, data);
            }
        }
    }
}
