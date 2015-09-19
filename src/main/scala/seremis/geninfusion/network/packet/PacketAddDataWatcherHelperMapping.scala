package seremis.geninfusion.network.packet

import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.api.util.DataWatcherHelper
import seremis.geninfusion.util.UtilNBT

class PacketAddDataWatcherHelperMapping(var entityId: Int, var mappingId: Int, var mappingName: String, var valueNBT: NBTTagCompound) extends IMessage {


    //Because this apparently needs an empty constructor to work..
    def this() {
        this(0, 0, null, null)
    }

    override def fromBytes(buf: ByteBuf) {
        entityId = buf.readInt()
        mappingId = buf.readInt()

        val nbtArray = new Array[Byte](buf.readInt())
        val mappingNameArray = new Array[Byte](buf.readInt())

        buf.readBytes(nbtArray)
        buf.readBytes(mappingNameArray)

        valueNBT = UtilNBT.byteArrayToCompound(nbtArray).get
        mappingName = new String(mappingNameArray)
    }

    override def toBytes(buf: ByteBuf) {
        val nbtArray = UtilNBT.compoundToByteArray(valueNBT).get
        val mappingNameArray = mappingName.getBytes

        buf.writeInt(entityId)
        buf.writeInt(mappingId)
        buf.writeInt(nbtArray.length)
        buf.writeInt(mappingNameArray.length)
        buf.writeBytes(nbtArray)
        buf.writeBytes(mappingNameArray)
    }
}

object PacketAddDataWatcherHelperMapping {

    class Handler extends IMessageHandler[PacketAddDataWatcherHelperMapping, IMessage] {

        override def onMessage(message: PacketAddDataWatcherHelperMapping, ctx: MessageContext): IMessage = {
            val ent = Minecraft.getMinecraft.theWorld.getEntityByID(message.entityId)

            if (ent != null) {
                handleMessage(message, ent)
            }

            null
        }
    }

    def handleMessage(message: PacketAddDataWatcherHelperMapping, entity: Entity) {
        DataWatcherHelper.addMapping(entity.getDataWatcher, message.mappingId, message.mappingName)

        val dataType = message.valueNBT.getString(message.mappingName + ".type")
        var obj: Any = null

        if(dataType == "byte") obj = message.valueNBT.getByte(message.mappingName)
        if(dataType == "short") obj = message.valueNBT.getShort(message.mappingName)
        if(dataType == "integer") obj = message.valueNBT.getInteger(message.mappingName)
        if(dataType == "float") obj = message.valueNBT.getFloat(message.mappingName)
        if(dataType == "string") obj = message.valueNBT.getString(message.mappingName)
        if(dataType == "itemStack") obj = ItemStack.loadItemStackFromNBT(message.valueNBT.getCompoundTag(message.mappingName))
        if(dataType == "chunkCoordinates") obj = new ChunkCoordinates(message.valueNBT.getInteger(message.mappingName + ".x"), message.valueNBT.getInteger(message.mappingName + ".y"), message.valueNBT.getInteger(message.mappingName + ".z"))

        entity.getDataWatcher.addObject(message.mappingId, obj.asInstanceOf[AnyRef])
        println("registered on client")
    }
}