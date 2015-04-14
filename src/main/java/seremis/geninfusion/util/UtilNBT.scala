package seremis.geninfusion.util

import net.minecraft.nbt.{NBTTagList, NBTTagCompound}

object UtilNBT {

    implicit class ArrayToNBT[T](val array: Array[T]) extends INBTTagable {

        override def writeToNBT(compound: NBTTagCompound) {
            val list = new NBTTagList

            for(i <- 0 until array.length) {
                val arrayCompound = new NBTTagCompound

                array(i).writeToNBT(arrayCompound)("")
                "".writeToNBT(arrayCompound)("")
            }
        }

        override def readFromNBT(compound: NBTTagCompound) {

        }
    }

    implicit class PrimitiveToNBT[T](name: String)(implicit var primitive: T) extends INBTTagable {

        override def writeToNBT(compound: NBTTagCompound) {
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
            }
        }

        override def readFromNBT(compound: NBTTagCompound) {
            val dataType = compound.getString(name + ".dataType")

            dataType match {
                case "boolean" =>
                    primitive = compound.getBoolean(name)
                case "byte" =>
                    primitive = compound.getByte(name)
                case "short" =>
                    primitive = compound.getShort(name)
                case "integer" =>
                    primitive = compound.getInteger(name)
                case "float" =>
                    primitive = compound.getFloat(name)
                case "double" =>
                    primitive = compound.getDouble(name)
                case "long" =>
                    primitive = compound.getLong(name)
                case "string" =>
                    primitive = compound.getString(name)
            }
        }
    }
}
