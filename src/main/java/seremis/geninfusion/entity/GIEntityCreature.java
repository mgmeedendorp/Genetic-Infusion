package seremis.geninfusion.entity;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.EntityCreature;
import net.minecraft.world.World;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.network.packet.PacketEntityData;

public class GIEntityCreature extends EntityCreature {

    public GIEntityCreature(World world) {
        super(world);
    }

    public void sendEntityDataToClient(int id, byte[] value) {
        if(CommonProxy.instance.isServerWorld(worldObj)) {
            GeneticInfusion.packetPipeline.sendToAllAround(new PacketEntityData(value, id, getEntityId()), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 128));
        } else {
            receivePacketOnServer(id, value);
        }
    }

    public void sendEntityDataToServer(int id, byte[] value) {
        if(CommonProxy.instance.isRenderWorld(worldObj)) {
            GeneticInfusion.packetPipeline.sendToServer(new PacketEntityData(value, id, getEntityId()));
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
