package seremis.geninfusion.tileentity

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity
import seremis.geninfusion.api.soul.{ISoul, ISoulReceptor, SoulHelper}

class TileCrystal extends TileEntity with GITile with ISoulReceptor {

    var soul: Option[ISoul] = None
    //This should always be false on the server
    var hasSoulClient = false
    var colorCounter = 40
    val maxColorCounter = 40

    override def updateEntity() {
        if(hasSoul && colorCounter > 0)
            colorCounter -= 1
    }

    /**
     * Gets the ISoul this ISoulReceptor currently has.
     * @return The Option[ISoul] this ISoulReceptor contains.
     */
    override def getSoul: Option[ISoul] = soul

    /**
     * Sets the ISoul for this ISoulReceptor to contain.
     * @param soul The ISoul to set.
     */
    override def setSoul(soul: Option[ISoul]) = this.soul = soul

    /**
     * @return true if this ISoulReceptor has an ISoul.
     */
    override def hasSoul: Boolean = soul.nonEmpty || hasSoulClient

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)

        if(compound.hasKey("soul"))
            soul = SoulHelper.instanceHelper.getISoulInstance(compound.getCompoundTag("soul"))
        else
            soul = None

        colorCounter = compound.getInteger("colorCounter")
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)

        soul.foreach(s => compound.setTag("soul", s.writeToNBT(new NBTTagCompound)))

        compound.setInteger("colorCounter", colorCounter)
    }

    override def getDescriptionPacket: Packet = {
        val compound = new NBTTagCompound
        compound.setBoolean("hasSoul", hasSoul)
        new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound)
    }

    override def onDataPacket(manager: NetworkManager, packet: S35PacketUpdateTileEntity) {
        hasSoulClient = packet.getNbtCompound.getBoolean("hasSoul")
    }

    override def shouldRenderInPass(pass: Int): Boolean = {
        pass == 1
    }

    override def setTileDataFromServer(id: Int, data: Array[Byte]) {
        if(id == 0) {
            hasSoulClient = true
            colorCounter = 0
        }
    }
}
