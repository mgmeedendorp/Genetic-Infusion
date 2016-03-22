package com.seremis.geninfusion.register

import com.seremis.geninfusion.api.GIApiInterface._
import com.seremis.geninfusion.lib.DataTypes._
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object RegisterDataTypes extends Register {

    override def register() {
        dataTypeRegistry.register(typeBoolean, classOf[Boolean])
        dataTypeRegistry.register(typeByte, classOf[Byte])
        dataTypeRegistry.register(typeShort, classOf[Short])
        dataTypeRegistry.register(typeInt, classOf[Int])
        dataTypeRegistry.register(typeFloat, classOf[Float])
        dataTypeRegistry.register(typeDouble, classOf[Double])
        dataTypeRegistry.register(typeLong, classOf[Long])
        dataTypeRegistry.register(typeString, classOf[String])
        dataTypeRegistry.register(typeClass, classOf[Class[_]])
        dataTypeRegistry.register(typeItemStack, classOf[ItemStack])
        dataTypeRegistry.register(typeCompound, classOf[NBTTagCompound])

        dataTypeRegistry.register(typeBooleanArray, classOf[Array[Boolean]])
        dataTypeRegistry.register(typeByteArray, classOf[Array[Byte]])
        dataTypeRegistry.register(typeShortArray, classOf[Array[Short]])
        dataTypeRegistry.register(typeIntArray, classOf[Array[Int]])
        dataTypeRegistry.register(typeFloatArray, classOf[Array[Float]])
        dataTypeRegistry.register(typeDoubleArray, classOf[Array[Double]])
        dataTypeRegistry.register(typeLongArray, classOf[Array[Long]])
        dataTypeRegistry.register(typeStringArray, classOf[Array[String]])
        dataTypeRegistry.register(typeClassArray, classOf[Array[Class[_]]])
        dataTypeRegistry.register(typeItemStackArray, classOf[Array[ItemStack]])
        dataTypeRegistry.register(typeCompoundArray, classOf[Array[NBTTagCompound]])
    }

    override def registerClient() {}

    override def registerServer() {}
}
