package com.seremis.geninfusion.lib

import com.seremis.geninfusion.api.util.DataType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object DataTypes {

    val typeBoolean = new DataType[Boolean] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Boolean): Unit = compound.setBoolean(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Boolean = compound.getBoolean(name)
    }

    val typeByte = new DataType[Byte] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Byte): Unit = compound.setByte(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Byte = compound.getByte(name)
    }

    val typeShort = new DataType[Short] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Short): Unit = compound.setShort(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Short = compound.getShort(name)
    }

    val typeInt = new DataType[Int] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Int): Unit = compound.setInteger(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Int = compound.getInteger(name)
    }

    val typeFloat = new DataType[Float] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Float): Unit = compound.setFloat(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Float = compound.getFloat(name)
    }

    val typeDouble = new DataType[Double] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Double): Unit = compound.setDouble(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Double = compound.getDouble(name)
    }

    val typeLong = new DataType[Long] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Long): Unit = compound.setLong(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): Long = compound.getLong(name)
    }

    val typeString = new DataType[String] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: String): Unit = compound.setString(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): String = compound.getString(name)
    }

    val typeClass = new DataType[Class[_]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Class[_]): Unit = compound.setString(name, data.getName)
        override def readFromNBT(compound: NBTTagCompound, name: String): Class[_] = Class.forName(compound.getString(name))
    }

    val typeItemStack = new DataType[ItemStack] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: ItemStack): Unit = compound.setTag(name, data.writeToNBT(new NBTTagCompound))
        override def readFromNBT(compound: NBTTagCompound, name: String): ItemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name))
    }

    val typeCompound = new DataType[NBTTagCompound] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: NBTTagCompound): Unit = compound.setTag(name, data)
        override def readFromNBT(compound: NBTTagCompound, name: String): NBTTagCompound = compound.getCompoundTag(name)
    }

    val typeBooleanArray = new DataType[Array[Boolean]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Boolean]): Unit = {
            for(i <- data.indices) {
                compound.setBoolean(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Boolean] = {
            val out = Array.ofDim[Boolean](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getBoolean(name + "." + i)
            }
            out
        }
    }

    val typeByteArray = new DataType[Array[Byte]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Byte]): Unit = compound.setByteArray(name, data)

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Byte] = compound.getByteArray(name)
    }

    val typeShortArray = new DataType[Array[Short]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Short]): Unit = {
            for(i <- data.indices) {
                compound.setShort(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Short] = {
            val out = Array.ofDim[Short](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getShort(name + "." + i)
            }
            out
        }
    }

    val typeIntArray = new DataType[Array[Int]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Int]): Unit = compound.setIntArray(name, data)

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Int] = compound.getIntArray(name)
    }

    val typeFloatArray = new DataType[Array[Float]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Float]): Unit = {
            for(i <- data.indices) {
                compound.setFloat(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Float] = {
            val out = Array.ofDim[Float](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getFloat(name + "." + i)
            }
            out
        }
    }

    val typeDoubleArray = new DataType[Array[Double]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Double]): Unit = {
            for(i <- data.indices) {
                compound.setDouble(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Double] = {
            val out = Array.ofDim[Double](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getDouble(name + "." + i)
            }
            out
        }
    }

    val typeLongArray = new DataType[Array[Long]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Long]): Unit = {
            for(i <- data.indices) {
                compound.setLong(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Long] = {
            val out = Array.ofDim[Long](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getLong(name + "." + i)
            }
            out
        }
    }

    val typeStringArray = new DataType[Array[String]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[String]): Unit = {
            for(i <- data.indices) {
                compound.setString(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[String] = {
            val out = Array.ofDim[String](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getString(name + "." + i)
            }
            out
        }
    }

    val typeClassArray = new DataType[Array[Class[_]]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[Class[_]]): Unit = {
            for(i <- data.indices) {
                compound.setString(name + "." + i, data(i).getName)
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[Class[_]] = {
            val out = Array.ofDim[Class[_]](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = Class.forName(compound.getString(name + "." + i))
            }
            out
        }
    }

    val typeItemStackArray = new DataType[Array[ItemStack]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[ItemStack]): Unit = {
            for(i <- data.indices) {
                compound.setTag(name + "." + i, data(i).writeToNBT(new NBTTagCompound))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[ItemStack] = {
            val out = Array.ofDim[ItemStack](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name + "." + i))
            }
            out
        }
    }

    val typeCompoundArray = new DataType[Array[NBTTagCompound]] {
        override def writeToNBT(compound: NBTTagCompound, name: String, data: Array[NBTTagCompound]): Unit = {
            for(i <- data.indices) {
                compound.setTag(name + "." + i, data(i))
            }
            compound.setInteger(name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Array[NBTTagCompound] = {
            val out = Array.ofDim[NBTTagCompound](compound.getInteger(name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getCompoundTag(name + "." + i)
            }
            out
        }
    }
}
