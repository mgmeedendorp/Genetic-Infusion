package com.seremis.geninfusion.soulentity

import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.util.UtilNBT
import io.netty.buffer.ByteBuf
import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData

trait SoulEntityLivingTrait extends EntityLiving with ISoulEntity with IEntityAdditionalSpawnData {

    var soul: ISoul
    val world: World

    override def getSoul: ISoul = soul

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT(compound)

        val bytes = UtilNBT.compoundToByteArray(compound)
        data.writeInt(bytes.length)
        data.writeBytes(bytes)

        /*
        if(DataWatcherHelper.cachedPackets.contains(getEntityId)) {
            data.writeInt(DataWatcherHelper.cachedPackets.get(getEntityId).get.size)

            DataWatcherHelper.cachedPackets.get(getEntityId).get.foreach(packet => {
                packet.toBytes(data)
            })
        }
        */
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readInt
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes)
        readFromNBT(compound)

        /*
        if(data.readerIndex() < data.writerIndex) {
            val size = data.readInt()

            for(i <- 0 until size) {
                val packet = new PacketAddDataWatcherHelperMapping()

                packet.fromBytes(data)
                PacketAddDataWatcherHelperMapping.handleMessage(packet, this)
            }
        }
        */
    }


    override def getVar[A](name: TypedName[A]): A = ???
    override def setVar[A](name: TypedName[A], value: A): Unit = ???
    override def makePersistent(name: TypedName[_]): Unit = ???

    override def callMethod[A](name: TypedName[A], args: Any*): A = ???
}
