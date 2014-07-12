package seremis.soulcraft.entity;

import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.network.packet.PacketEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class SCEntity extends Entity {

    public SCEntity(World world) {
        super(world);
    }

    public void sendEntityDataToClient(int id, byte[] value) {
        if(CommonProxy.instance.isServerWorld(worldObj)) {
        	SoulCraft.packetPipeline.sendToAllAround(new PacketEntityData(value, id, getEntityId()), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 128));
        } else {
            receivePacketOnServer(id, value);
        }
    }

    public void sendEntityDataToServer(int id, byte[] value) {
        if(CommonProxy.instance.isRenderWorld(worldObj)) {
        	SoulCraft.packetPipeline.sendToServer(new PacketEntityData(value, id, getEntityId()));
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
