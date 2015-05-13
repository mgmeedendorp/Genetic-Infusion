package seremis.geninfusion.network.packet

import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import seremis.geninfusion.entity.GIEntity

class PacketEntityData(var data: Array[Byte], var id: Int, var entityId: Int) extends IMessage {

    var length: Int = data.length

    override def fromBytes(buf: ByteBuf) {
        entityId = buf.readInt()
        id = buf.readByte()
        length = buf.readInt()
        data = Array.ofDim[Byte](length)
        buf.readBytes(data)
    }

    override def toBytes(buf: ByteBuf) {
        buf.writeInt(entityId)
        buf.writeByte(id)
        buf.writeInt(length)
        buf.writeBytes(data)
    }
}

object PacketEntityData {

    class Handler extends IMessageHandler[PacketEntityData, IMessage] {

        override def onMessage(message: PacketEntityData, ctx: MessageContext): IMessage = {
            if (ctx.side == Side.CLIENT) {
                val ent = Minecraft.getMinecraft.theWorld.getEntityByID(message.entityId)

                if (ent != null && ent.isInstanceOf[GIEntity]) {
                    ent.asInstanceOf[GIEntity].receivePacketOnServer(message.id, message.data)
                }
            } else {
                val ent = ctx.getServerHandler.playerEntity.worldObj.getEntityByID(message.entityId)

                if (ent != null && ent.isInstanceOf[GIEntity]) {
                    ent.asInstanceOf[GIEntity].receivePacketOnServer(message.id, message.data)
                }
            }
            null
        }
    }
}