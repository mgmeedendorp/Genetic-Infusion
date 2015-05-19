package seremis.geninfusion.tileentity

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.network.ModPackets
import seremis.geninfusion.network.packet.PacketTileData

trait GITile extends TileEntity {

    private var direction: Int = _

    def getDirection: Int = direction

    def setDirection(direction: Int) {
        this.direction = direction
        getWorld.addBlockEvent(xCoord, yCoord, zCoord, getBlockType, 1, direction)
    }

    def isUseableByPlayer(player: EntityPlayer): Boolean = true

    override def receiveClientEvent(eventId: Int, variable: Int): Boolean = {
        if (eventId == 1) {
            direction = variable
            getWorld.markBlockForUpdate(xCoord, yCoord, zCoord)
            true
        } else {
            super.receiveClientEvent(eventId, variable)
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        direction = compound.getInteger("direction")
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("direction", direction)
    }

    @SideOnly(Side.CLIENT)
    override def onDataPacket(net: NetworkManager, packet: S35PacketUpdateTileEntity) {
        readFromNBT(packet.getNbtCompound)
    }

    override def getDescriptionPacket: Packet = {
        val compound = new NBTTagCompound()
        writeToNBT(compound)
        new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, compound)
    }

    def sendTileDataToServer(id: Int, data: Array[Byte]) {
        if (GeneticInfusion.commonProxy.isRenderWorld(getWorld)) {
            ModPackets.wrapper.sendToServer(new PacketTileData(data, id, this.xCoord, this.yCoord, this.zCoord))
        } else {
            setTileDataFromClient(id, data)
        }
    }

    def setTileDataFromServer(id: Int, data: Array[Byte]) {}

    def sendTileDataToClient(id: Int, data: Array[Byte]) {
        if (GeneticInfusion.commonProxy.isServerWorld(getWorld)) {
            ModPackets.wrapper.sendToAllAround(new PacketTileData(data, id, xCoord, yCoord, zCoord), new TargetPoint(getWorld.provider.dimensionId, xCoord, yCoord, zCoord, 128))
        } else {
            setTileDataFromServer(id, data)
        }
    }

    def setTileDataFromClient(id: Int, data: Array[Byte]) {}
}
