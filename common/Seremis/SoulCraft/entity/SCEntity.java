package Seremis.SoulCraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketEntityData;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class SCEntity extends Entity {

    public SCEntity(World world) {
        super(world);
    }
    
    public void sendEntityDataToClient(int id, byte[] value) {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketEntityData(value, id, entityId)));
        } else {
            receivePacketOnServer(id, value);
        }
    }
    
    public void sendEntityDataToServer(int id, byte[] value) {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketEntityData(value, id, entityId)));
        } else {
            receivePacketOnServer(id, value);
        }
    }
    
    public void receivePacketOnClient(int id, byte[] value) {
        
    }
    
    public void receivePacketOnServer(int id, byte[] value) {
        
    }
    
    public abstract ResourceLocation getTexture();
}
