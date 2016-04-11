package com.seremis.geninfusion.soulentity

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.soulentity.logic.FieldLogic
import com.seremis.geninfusion.util.UtilNBT
import io.netty.buffer.ByteBuf
import net.minecraft.entity._
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData

trait SoulEntityLivingTrait extends EntityLiving with EntityLivingMethodOverrides with ISoulEntity with IEntityAdditionalSpawnData {

    var soul: ISoul
    val world: World

    var fieldLogic: FieldLogic = new FieldLogic(this)

    override def getSoul: ISoul = soul

    override def getVar[A](name: TypedName[A]): A = fieldLogic.getVar(name)
    override def setVar[A](name: TypedName[A], value: A): Unit = fieldLogic.setVar(name, value)
    override def makePersistent(name: TypedName[_]): Unit = fieldLogic.makePersistent(name)

    override def callMethod[A](name: TypedName[A], args: Any*): A = methodLogic.callMethod(name, () => null.asInstanceOf[A], args)


    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT(compound)

        val bytes = UtilNBT.compoundToByteArray(compound)
        data.writeInt(bytes.length)
        data.writeBytes(bytes)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readInt
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound): Unit = {
        GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, "soul", classOf[ISoul], soul)
        fieldLogic.writeToNBT(compound)
        super.writeToNBT(compound)
    }

    override def readFromNBT(compound: NBTTagCompound): Unit = {
        soul = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, "soul", classOf[ISoul])
        fieldLogic.readFromNBT(compound)
        super.readFromNBT(compound)
    }
}
