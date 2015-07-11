package seremis.geninfusion.entity

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.Entity
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.network.ModPackets
import seremis.geninfusion.network.packet.PacketEntityData

trait GIEntity extends Entity {

    def sendEntityDataToClient(id: Int, value: Array[Byte]) {
        if (GeneticInfusion.commonProxy.isServerWorld(worldObj)) {
            ModPackets.wrapper.sendToAllAround(new PacketEntityData(value, id, getEntityId), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 128))
        } else {
            receivePacketOnClient(id, value)
        }
    }

    def sendEntityDataToServer(id: Int, value: Array[Byte]) {
        if (GeneticInfusion.commonProxy.isRenderWorld(worldObj)) {
            ModPackets.wrapper.sendToServer(new PacketEntityData(value, id, getEntityId))
        } else {
            receivePacketOnServer(id, value)
        }
    }

    @SideOnly(Side.CLIENT)
    override def setPositionAndRotation2(par1: Double, par3: Double, par5: Double, par7: Float, par8: Float, par9: Int) {
        if (noClip) {
            setPositionAndRotation(par1, par3, par5, par7, par8)
        } else {
            super.setPositionAndRotation2(par1, par3, par5, par7, par8, par9)
        }
    }

    @SideOnly(Side.CLIENT)
    def receivePacketOnClient(id: Int, value: Array[Byte]) {}

    def receivePacketOnServer(id: Int, value: Array[Byte]) {}
}