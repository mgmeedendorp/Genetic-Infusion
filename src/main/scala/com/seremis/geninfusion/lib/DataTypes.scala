package com.seremis.geninfusion.lib

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.util.{DataType, TypedName}
import com.seremis.geninfusion.genetics.{Ancestry, AncestryLeaf, AncestryNode}
import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object DataTypes {

    val typeBoolean = new DataType[Boolean] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Boolean], data: Boolean): Unit = compound.setBoolean(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Boolean]): Boolean = compound.getBoolean(name.name)
    }

    val typeByte = new DataType[Byte] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Byte], data: Byte): Unit = compound.setByte(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Byte]): Byte = compound.getByte(name.name)
    }

    val typeShort = new DataType[Short] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Short], data: Short): Unit = compound.setShort(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Short]): Short = compound.getShort(name.name)
    }

    val typeInt = new DataType[Int] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Int], data: Int): Unit = compound.setInteger(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Int]): Int = compound.getInteger(name.name)
    }

    val typeFloat = new DataType[Float] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Float], data: Float): Unit = compound.setFloat(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Float]): Float = compound.getFloat(name.name)
    }

    val typeDouble = new DataType[Double] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Double], data: Double): Unit = compound.setDouble(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Double]): Double = compound.getDouble(name.name)
    }

    val typeLong = new DataType[Long] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Long], data: Long): Unit = compound.setLong(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Long]): Long = compound.getLong(name.name)
    }

    val typeString = new DataType[String] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[String], data: String): Unit = compound.setString(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[String]): String = compound.getString(name.name)
    }

    val typeClass = new DataType[Class[_]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Class[_]], data: Class[_]): Unit = compound.setString(name.name, data.getName)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Class[_]]): Class[_] = Class.forName(compound.getString(name.name))
    }

    val typeItemStack = new DataType[ItemStack] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[ItemStack], data: ItemStack): Unit = compound.setTag(name.name, data.writeToNBT(new NBTTagCompound))
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[ItemStack]): ItemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name.name))
    }

    val typeCompound = new DataType[NBTTagCompound] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[NBTTagCompound], data: NBTTagCompound): Unit = compound.setTag(name.name, data)
        override def readFromNBT(compound: NBTTagCompound, name: TypedName[NBTTagCompound]): NBTTagCompound = compound.getCompoundTag(name.name)
    }

    val typeAncestry = new DataType[Ancestry] {
        override def writeToNBT(compound: NBTTagCompound, tName: TypedName[Ancestry], data: Ancestry): Unit = {
            data match {
                case d: AncestryNode => writeNode(compound, tName, d)
                case d: AncestryLeaf => writeLeaf(compound, tName, d)
            }

        }

        def writeNode(compound: NBTTagCompound, tName: TypedName[Ancestry], data: AncestryNode): Unit = {
            GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, tName.copy(name = tName.name + ".parent1"), data.parent1)
            GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, tName.copy(name = tName.name + ".parent2"), data.parent2)
            compound.setBoolean(tName.name + ".isNode", true)
        }

        def writeLeaf(compound: NBTTagCompound, tName: TypedName[Ancestry], data: AncestryLeaf): Unit = {
            GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, TypedName(tName.name + ".clzz", classOf[Class[_ <: EntityLiving]]), data.clzz)
            GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, TypedName(tName.name + ".readableName", classOf[String]), data.readableName)
            compound.setBoolean(tName.name + ".isNode", false)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Ancestry]): Ancestry = {
            val isNode = compound.getBoolean(name.name + ".isNode")
            if(isNode) {
                readNode(compound, name)
            } else {
                readLeaf(compound, name)
            }
        }

        def readNode(compound: NBTTagCompound, tName: TypedName[Ancestry]): AncestryNode = {
            val parent1 = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, tName.copy(name = tName.name + ".parent1"))
            val parent2 = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, tName.copy(name = tName.name + ".parent2"))

            AncestryNode(parent1, parent2)
        }

        def readLeaf(compound: NBTTagCompound, tName: TypedName[Ancestry]): AncestryLeaf = {
            val clzz = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, TypedName(tName.name + ".clzz", classOf[Class[_ <: EntityLiving]]))
            val readableName = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, TypedName(tName.name + ".readableName", classOf[String]))

            AncestryLeaf(readableName, clzz)
        }
    }

    val typeBooleanArray = new DataType[Array[Boolean]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Boolean]], data: Array[Boolean]): Unit = {
            for(i <- data.indices) {
                compound.setBoolean(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Boolean]]): Array[Boolean] = {
            val out = Array.ofDim[Boolean](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getBoolean(name.name + "." + i)
            }
            out
        }
    }

    val typeByteArray = new DataType[Array[Byte]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Byte]], data: Array[Byte]): Unit = compound.setByteArray(name.name, data)

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Byte]]): Array[Byte] = compound.getByteArray(name.name)
    }

    val typeShortArray = new DataType[Array[Short]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Short]], data: Array[Short]): Unit = {
            for(i <- data.indices) {
                compound.setShort(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Short]]): Array[Short] = {
            val out = Array.ofDim[Short](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getShort(name.name + "." + i)
            }
            out
        }
    }

    val typeIntArray = new DataType[Array[Int]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Int]], data: Array[Int]): Unit = compound.setIntArray(name.name, data)

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Int]]): Array[Int] = compound.getIntArray(name.name)
    }

    val typeFloatArray = new DataType[Array[Float]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Float]], data: Array[Float]): Unit = {
            for(i <- data.indices) {
                compound.setFloat(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Float]]): Array[Float] = {
            val out = Array.ofDim[Float](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getFloat(name.name + "." + i)
            }
            out
        }
    }

    val typeDoubleArray = new DataType[Array[Double]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Double]], data: Array[Double]): Unit = {
            for(i <- data.indices) {
                compound.setDouble(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Double]]): Array[Double] = {
            val out = Array.ofDim[Double](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getDouble(name.name + "." + i)
            }
            out
        }
    }

    val typeLongArray = new DataType[Array[Long]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Long]], data: Array[Long]): Unit = {
            for(i <- data.indices) {
                compound.setLong(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Long]]): Array[Long] = {
            val out = Array.ofDim[Long](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getLong(name.name + "." + i)
            }
            out
        }
    }

    val typeStringArray = new DataType[Array[String]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[String]], data: Array[String]): Unit = {
            for(i <- data.indices) {
                compound.setString(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[String]]): Array[String] = {
            val out = Array.ofDim[String](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getString(name.name + "." + i)
            }
            out
        }
    }

    val typeClassArray = new DataType[Array[Class[_]]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[Class[_]]], data: Array[Class[_]]): Unit = {
            for(i <- data.indices) {
                compound.setString(name.name + "." + i, data(i).getName)
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[Class[_]]]): Array[Class[_]] = {
            val out = Array.ofDim[Class[_]](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = Class.forName(compound.getString(name.name + "." + i))
            }
            out
        }
    }

    val typeItemStackArray = new DataType[Array[ItemStack]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[ItemStack]], data: Array[ItemStack]): Unit = {
            for(i <- data.indices) {
                compound.setTag(name.name + "." + i, data(i).writeToNBT(new NBTTagCompound))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[ItemStack]]): Array[ItemStack] = {
            val out = Array.ofDim[ItemStack](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name.name + "." + i))
            }
            out
        }
    }

    val typeCompoundArray = new DataType[Array[NBTTagCompound]] {
        override def writeToNBT(compound: NBTTagCompound, name: TypedName[Array[NBTTagCompound]], data: Array[NBTTagCompound]): Unit = {
            for(i <- data.indices) {
                compound.setTag(name.name + "." + i, data(i))
            }
            compound.setInteger(name.name + ".length", data.length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: TypedName[Array[NBTTagCompound]]): Array[NBTTagCompound] = {
            val out = Array.ofDim[NBTTagCompound](compound.getInteger(name.name + ".length"))
            for (i <- out.indices) {
                out(i) = compound.getCompoundTag(name.name + "." + i)
            }
            out
        }
    }
}
