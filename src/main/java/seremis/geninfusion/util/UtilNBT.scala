package seremis.geninfusion.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import seremis.geninfusion.api.soul.util.ModelPart

import scala.reflect.ClassTag

object UtilNBT {

    implicit class ArrayToNBT[T](var array: Array[T]) {

        def writeToNBT(compound: NBTTagCompound, name: String) {
            val list = new NBTTagList

            for(i <- 0 until array.length) {
                val arrayCompound = new NBTTagCompound
                array(i).writeToNBT(arrayCompound, name + "." + i)
                list.appendTag(arrayCompound)
            }
            compound.setTag(name, list)
        }

        def readFromNBT(compound: NBTTagCompound, name: String)(implicit m: ClassTag[T]) {
            val list = compound.getTag(name).asInstanceOf[NBTTagList]
            val result = new Array[T](list.tagCount())

            for(i <- 0 until list.tagCount()) {
                result(i).readFromNBT(list.getCompoundTagAt(i), name)
            }
            array = result
        }
    }

    implicit class ObjectToNBT[T](var primitive: T) {

        def writeToNBT(compound: NBTTagCompound, name: String) {
            primitive match {
                case b: Boolean =>
                    compound.setBoolean(name, b)
                    compound.setString(name + ".dataType", "boolean")
                case b: Byte =>
                    compound.setByte(name, b)
                    compound.setString(name + ".dataType", "byte")
                case s: Short =>
                    compound.setShort(name, s)
                    compound.setString(name + ".dataType", "short")
                case i: Int =>
                    compound.setInteger(name, i)
                    compound.setString(name + ".dataType", "integer")
                case f: Float =>
                    compound.setFloat(name, f)
                    compound.setString(name + ".dataType", "float")
                case d: Double =>
                    compound.setDouble(name, d)
                    compound.setString(name + ".dataType", "double")
                case l: Long =>
                    compound.setLong(name, l)
                    compound.setString(name + ".dataType", "long")
                case s: String =>
                    compound.setString(name, s)
                    compound.setString(name + ".dataType", "string")
                case c: Class[_] =>
                    compound.setString(name, c.getName)
                    compound.setString(name + ".dataType", "class")
                case i: ItemStack =>
                    val nbt = new NBTTagCompound
                    i.writeToNBT(nbt)
                    compound.setTag(name, nbt)
                    compound.setString(name + ".dataType", "itemStack")
                case m: ModelPart =>
                    val nbt = new NBTTagCompound
                    m.writeToNBT(nbt)
                    compound.setTag(name, nbt)
                    compound.setString(name + ".dataType", "modelPart")
                case a: Array[_] =>
                    a.writeToNBT(compound, name)
                    compound.setString(name + ".dataType", "array")
            }
        }

        def readFromNBT(compound: NBTTagCompound, name: String)(implicit m: ClassTag[T]) {
            val dataType = compound.getString(name + ".dataType")

            dataType match {
                case "boolean" =>
                    primitive = compound.getBoolean(name).asInstanceOf[T]
                case "byte" =>
                    primitive = compound.getByte(name).asInstanceOf[T]
                case "short" =>
                    primitive = compound.getShort(name).asInstanceOf[T]
                case "integer" =>
                    primitive = compound.getInteger(name).asInstanceOf[T]
                case "float" =>
                    primitive = compound.getFloat(name).asInstanceOf[T]
                case "double" =>
                    primitive = compound.getDouble(name).asInstanceOf[T]
                case "long" =>
                    primitive = compound.getLong(name).asInstanceOf[T]
                case "string" =>
                    primitive = compound.getString(name).asInstanceOf[T]
                case "class" =>
                    primitive = Class.forName(compound.getString(name)).asInstanceOf[T]
                case "itemStack" =>
                    primitive = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name)).asInstanceOf[T]
                case "modelPart" =>
                    primitive = ModelPart.fromNBT(compound.getCompoundTag(name)).asInstanceOf[T]
                case "array" =>
                    val array = new Array[T](0)
                    array.readFromNBT(compound, name)
                    primitive = array.asInstanceOf[T]
            }
        }
    }
}