package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IAlleleType
import seremis.geninfusion.api.soul.util.ModelPart

abstract class AlleleType(clzz: Class[_]) extends IAlleleType {

    override def getAlleleTypeClass: Class[_] = clzz

    override def writeToNBT(compound: NBTTagCompound, name: String, value: Any)
    override def readFromNBT(compound: NBTTagCompound, name: String): Any
}

object AlleleType {

    val typeBoolean = new AlleleType(classOf[Boolean]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setBoolean(name, value.asInstanceOf[Boolean])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getBoolean(name)
    }

    val typeByte = new AlleleType(classOf[Byte]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setByte(name, value.asInstanceOf[Byte])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getByte(name)
    }

    val typeShort = new AlleleType(classOf[Short]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setShort(name, value.asInstanceOf[Short])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getShort(name)
    }

    val typeInt = new AlleleType(classOf[Int]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setInteger(name, value.asInstanceOf[Int])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getInteger(name)
    }

    val typeFloat = new AlleleType(classOf[Float]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setFloat(name, value.asInstanceOf[Float])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getFloat(name)
    }

    val typeDouble = new AlleleType(classOf[Double]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setDouble(name, value.asInstanceOf[Double])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getDouble(name)
    }

    val typeLong = new AlleleType(classOf[Long]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setLong(name, value.asInstanceOf[Long])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getLong(name)
    }

    val typeString = new AlleleType(classOf[String]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setString(name, value.asInstanceOf[String])
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = compound.getString(name)
    }

    val typeClass = new AlleleType(classOf[Class[_]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setString(name, value.asInstanceOf[Class[_]].getName)
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = try{ Class.forName(compound.getString(name)) } catch { case e: Exception => println(compound.getString(name)); return null}
    }

    val typeItemStack = new AlleleType(classOf[ItemStack]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setTag(name, value.asInstanceOf[ItemStack].writeToNBT(compound))
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name))
    }

    val typeModelPart = new AlleleType(classOf[ModelPart]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = compound.setTag(name, value.asInstanceOf[ModelPart].writeToNBT(compound))
        override def readFromNBT(compound: NBTTagCompound, name: String): Any = ModelPart.fromNBT(compound.getCompoundTag(name))
    }

    val typeBooleanArray = new AlleleType(classOf[Array[Boolean]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Boolean]].length) {
                compound.setBoolean("value." + i, value.asInstanceOf[Array[Boolean]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Boolean]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Boolean](length)
            for (i <- 0 until length) {
                out(i) = compound.getBoolean("value." + i)
            }
            out
        }
    }

    val typeByteArray = new AlleleType(classOf[Array[Byte]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Byte]].length) {
                compound.setByte("value." + i, value.asInstanceOf[Array[Byte]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Byte]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Byte](length)
            for (i <- 0 until length) {
                out(i) = compound.getByte("value." + i)
            }
            out
        }
    }

    val typeShortArray = new AlleleType(classOf[Array[Short]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Short]].length) {
                compound.setShort("value." + i, value.asInstanceOf[Array[Short]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Short]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Short](length)
            for (i <- 0 until length) {
                out(i) = compound.getShort("value." + i)
            }
            out
        }
    }

    val typeIntArray = new AlleleType(classOf[Array[Int]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Int]].length) {
                compound.setInteger("value." + i, value.asInstanceOf[Array[Int]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Int]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Int](length)
            for (i <- 0 until length) {
                out(i) = compound.getInteger("value." + i)
            }
            out
        }
    }

    val typeFloatArray = new AlleleType(classOf[Array[Float]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Float]].length) {
                compound.setFloat("value." + i, value.asInstanceOf[Array[Float]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Float]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Float](length)
            for (i <- 0 until length) {
                out(i) = compound.getFloat("value." + i)
            }
            out
        }
    }

    val typeDoubleArray = new AlleleType(classOf[Array[Double]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Double]].length) {
                compound.setDouble("value." + i, value.asInstanceOf[Array[Double]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Double]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Double](length)
            for (i <- 0 until length) {
                out(i) = compound.getDouble("value." + i)
            }
            out
        }
    }

    val typeLongArray = new AlleleType(classOf[Array[Long]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Long]].length) {
                compound.setLong("value." + i, value.asInstanceOf[Array[Long]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Long]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Long](length)
            for (i <- 0 until length) {
                out(i) = compound.getLong("value." + i)
            }
            out
        }
    }

    val typeStringArray = new AlleleType(classOf[Array[String]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[String]].length) {
                compound.setString("value." + i, value.asInstanceOf[Array[String]](i))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[String]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[String](length)
            for (i <- 0 until length) {
                out(i) = compound.getString("value." + i)
            }
            out
        }
    }

    val typeClassArray = new AlleleType(classOf[Array[Class[_]]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[Class[_]]].length) {
                compound.setString("value." + i, value.asInstanceOf[Array[Class[_]]](i).getName)
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[Class[_]]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[Class[_]](length)
            for (i <- 0 until length) {
                out(i) = Class.forName(compound.getString("value." + i))
            }
            out
        }
    }

    val typeItemStackArray = new AlleleType(classOf[Array[ItemStack]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[ItemStack]].length) {
                compound.setTag("value." + i, value.asInstanceOf[Array[ItemStack]](i).writeToNBT(new NBTTagCompound))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[ItemStack]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[ItemStack](length)
            for (i <- 0 until length) {
                out(i) = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("value." + i))
            }
            out
        }
    }

    val typeModelPartArray = new AlleleType(classOf[Array[ModelPart]]) {
        override def writeToNBT(compound: NBTTagCompound, name: String, value: Any) = {
            for(i <- 0 until value.asInstanceOf[Array[ModelPart]].length) {
                compound.setTag("value." + i, value.asInstanceOf[Array[ModelPart]](i).writeToNBT(new NBTTagCompound))
            }
            compound.setInteger("value.length", value.asInstanceOf[Array[ModelPart]].length)
        }

        override def readFromNBT(compound: NBTTagCompound, name: String): Any = {
            val length = compound.getInteger("value.length")
            val out = Array.ofDim[ModelPart](length)
            for (i <- 0 until length) {
                out(i) = ModelPart.fromNBT(compound.getCompoundTag("value." + i))
            }
            out
        }
    }
}
