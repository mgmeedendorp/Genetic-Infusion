package seremis.soulcraft.entity;

import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.network.PacketTypeHandler;
import seremis.soulcraft.network.packet.PacketEntityData;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class SCEntityLiving extends EntityLiving {
    
    public SCEntityLiving(World world) {
        super(world);
    }
    
    public void sendEntityDataToClient(int id, byte[] value) {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 128D, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketEntityData(value, id, entityId)));
        } else {
            receivePacketOnClient(id, value);
        }
    }

    public void sendEntityDataToServer(int id, byte[] value) {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketEntityData(value, id, entityId)));
        } else {
            receivePacketOnServer(id, value);
        }
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        if(noClip) {
            setPosition(par1, par3, par5);
            setRotation(par7, par8);
        } else {
            super.setPositionAndRotation2(par1, par3, par5, par7, par8, par9);
        }
    }

    public void receivePacketOnClient(int id, byte[] value) {

    }

    public void receivePacketOnServer(int id, byte[] value) {

    }

}
