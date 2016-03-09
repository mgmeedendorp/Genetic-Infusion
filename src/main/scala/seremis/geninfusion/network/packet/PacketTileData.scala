package seremis.geninfusion.network.packet

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import seremis.geninfusion.tileentity.GITile

class PacketTileData(var data: Array[Byte], var id: Int, var x: Int, var y: Int, var z: Int) extends IMessage {

    var length: Int = data.length

    //Because this apparently needs an empty constructor to work..
    def this() {
        this(Array(0.toByte), 0, 0, 0, 0)
    }

    override def fromBytes(buf: ByteBuf) {
        id = buf.readByte()
        x = buf.readInt()
        y = buf.readShort()
        z = buf.readInt()
        length = buf.readInt()
        data = Array.ofDim[Byte](length)
        buf.readBytes(data)
    }

    override def toBytes(buf: ByteBuf) {
        buf.writeByte(id)
        buf.writeInt(x)
        buf.writeShort(y)
        buf.writeInt(z)
        buf.writeInt(length)
        buf.writeBytes(data)
    }
}

object PacketTileData {

    class Handler extends IMessageHandler[PacketTileData, IMessage] {

        override def onMessage(message: PacketTileData, ctx: MessageContext): IMessage = {
            if (ctx.side == Side.CLIENT) {
                val tile = Minecraft.getMinecraft.theWorld.getTileEntity(message.x, message.y, message.z)

                if (tile != null && tile.isInstanceOf[GITile]) {
                    tile.asInstanceOf[GITile].setTileDataFromServer(message.id, message.data)
                }
            } else {
                val tile = ctx.getServerHandler.playerEntity.worldObj.getTileEntity(message.x, message.y, message.z)

                if (tile != null && tile.isInstanceOf[GITile]) {
                    tile.asInstanceOf[GITile].setTileDataFromClient(message.id, message.data)
                }
            }
            null
        }
    }
}