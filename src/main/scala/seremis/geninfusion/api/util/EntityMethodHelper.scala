package seremis.geninfusion.api.util

import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul}
import seremis.geninfusion.api.util.data.Data

object EntityMethodHelper {

    implicit def entityToLiving(entity: IEntitySoulCustom): EntityLiving = entity.asInstanceOf[EntityLiving]

    def soul(implicit entity: IEntitySoulCustom): ISoul = entity.getSoul
    def world(implicit entity: IEntitySoulCustom): World = entity.worldObj

    implicit class EntityVars(str: String)(implicit entity: IEntitySoulCustom) {
        def byte_++ = entity.setByte(str, (entity.getByte(str) + 1).toByte)
        def short_++ = entity.setShort(str, (entity.getShort(str) + 1).toShort)
        def int_++ = entity.setInteger(str, entity.getInteger(str) + 1)
        def float_++ = entity.setFloat(str, entity.getFloat(str) + 1)
        def double_++ = entity.setDouble(str, entity.getDouble(str) + 1)
        def long_++ = entity.setLong(str, entity.getLong(str) + 1)

        def byte_-- = entity.setByte(str, (entity.getByte(str) - 1).toByte)
        def short_-- = entity.setShort(str, (entity.getShort(str) - 1).toShort)
        def int_-- = entity.setInteger(str, entity.getInteger(str) - 1)
        def float_-- = entity.setFloat(str, entity.getFloat(str) - 1)
        def double_-- = entity.setDouble(str, entity.getDouble(str) - 1)
        def long_-- = entity.setLong(str, entity.getLong(str) - 1)

        def boolean = entity.getBoolean(str)
        def byte = entity.getByte(str)
        def short = entity.getShort(str)
        def int = entity.getInteger(str)
        def float = entity.getFloat(str)
        def double = entity.getDouble(str)
        def long = entity.getLong(str)
        def string = entity.getString(str)
        def stack = entity.getItemStack(str)
        def data = entity.getData(str)
        def nbt = entity.getNBT(str)
        def obj[T]: T = entity.getObject[T](str)

        def booleanArr = entity.getBooleanArray(str)
        def byteArr = entity.getByteArray(str)
        def shortArr = entity.getShortArray(str)
        def intArr = entity.getIntegerArray(str)
        def floatArr = entity.getFloatArray(str)
        def doubleArr = entity.getDoubleArray(str)
        def longArr = entity.getLongArray(str)
        def stringArr = entity.getStringArray(str)
        def stackArr = entity.getItemStackArray(str)
        def dataArr = entity.getDataArray(str)
        def nbtArr = entity.getNBTArray(str)

        def booleanT = entity.setBoolean(str, true)
        def booleanF = entity.setBoolean(str, false)
        def boolean(v: Boolean) = entity.setBoolean(str, v)
        def byte(v: Byte) = entity.setByte(str, v)
        def short(v: Short) = entity.setShort(str, v)
        def int(v: Int) = entity.setInteger(str, v)
        def float(v: Float) = entity.setFloat(str, v)
        def double(v: Double) = entity.setDouble(str, v)
        def long(v: Long) = entity.setLong(str, v)
        def string(v: String) = entity.setString(str, v)
        def stack(v: ItemStack) = entity.setItemStack(str, v)
        def data(v: Data) = entity.setData(str, v)
        def nbt(v: NBTTagCompound) = entity.setNBT(str, v)
        def obj(v: Any) = entity.setObject(str, v)

        def booleanArr(v: Array[Boolean]) = entity.setBooleanArray(str, v)
        def byteArr(v: Array[Byte]) = entity.setByteArray(str, v)
        def shortArr(v: Array[Short]) = entity.setShortArray(str, v)
        def intArr(v: Array[Int]) = entity.setIntegerArray(str, v)
        def floatArr(v: Array[Float]) = entity.setFloatArray(str, v)
        def doubleArr(v: Array[Double]) = entity.setDoubleArray(str, v)
        def longArr(v: Array[Long]) = entity.setLongArray(str, v)
        def stringArr(v: Array[String]) = entity.setStringArray(str, v)
        def stackArr(v: Array[ItemStack]) = entity.setItemStackArray(str, v)
        def dataArr(v: Array[Data]) = entity.setDataArray(str, v)
        def nbtArr(v: Array[NBTTagCompound]) = entity.setNBTArray(str, v)

        def method[T](v: Any*): T = entity.callMethod[T](str, v)
    }
}
