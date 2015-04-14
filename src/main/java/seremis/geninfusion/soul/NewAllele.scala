package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import seremis.geninfusion.api.soul.IAllele
import seremis.geninfusion.api.soul.util.{ModelPart, Data}

class NewAllele[T](var dominant: Boolean, var alleleData: T) extends IAllele {

    override def isDominant: Boolean = dominant

    override def writeToNBT(compound: NBTTagCompound) {
        alleleData match {
            case b: Boolean =>
                compound.setBoolean("value", b)
                compound.setString("dataType", "boolean")
            case bA: Array[Boolean] =>
                val list = new NBTTagList
                bA.foreach(b => {
                    val compound1 = new NBTTagCompound
                    compound1.setBoolean("value." + bA.indexOf(b), b)
                    list.appendTag(compound1)
                })
                compound.setTag("value", list)
                compound.setString("dataType", "booleanA")
            case b: Byte =>
                compound.setByte("value", b)
                compound.setString("dataType", "byte")
            case bA: Array[Byte] =>
                val data = new Data()
                data.setByteArray("value", bA)
                data.writeToNBT(compound)
                compound.setString("dataType", "byteA")
            case s: Short =>
                compound.setShort("value", s)
                compound.setString("dataType", "short")
            case sA: Array[Short] =>
                val data = new Data()
                data.setShortArray("value", sA)
                data.writeToNBT(compound)
                compound.setString("dataType", "shortA")
            case i: Int =>
                compound.setInteger("value", i)
                compound.setString("dataType", "integer")
            case iA: Array[Int] =>
                val data = new Data()
                data.setIntegerArray("value", iA)
                data.writeToNBT(compound)
                compound.setString("dataType", "integerA")
            case f: Float =>
                compound.setFloat("value", f)
                compound.setString("dataType", "float")
            case fA: Array[Float] =>
                data.setFloatArray("value", fA)
                compound.setString("dataType", "floatA")
            case d: Double =>
                compound.setDouble("value", d)
                compound.setString("dataType", "double")
            case dA: Array[Double] =>
                data.setDoubleArray("value", dA)
                compound.setString("dataType", "doubleA")
            case l: Long =>
                compound.setLong("value", l)
                compound.setString("dataType", "long")
            case lA: Array[Long] =>
                data.setLongArray("value", lA)
                compound.setString("dataType", "longA")
            case s: String =>
                compound.setString("value", s)
                compound.setString("dataType", "string")
            case sA: Array[String] =>
                data.setStringArray("value", sA)
                compound.setString("dataType", "stringA")
            case c: Class =>
                compound.setString("value", c.getName)
                compound.setString("dataType", "class")
            case cA: Array[Class] =>
                data.setStringArray("value", cA.map(c => c.getName))
                compound.setString("dataType", "classA")
            case m: ModelPart =>
                m.writeToNBT(compound)
                compound.setString("dataType", "modelPart")
            case mA: Array[ModelPart] =>
                val list = new NBTTagList
                mA.foreach(m => {
                    val compound1 = new NBTTagCompound
                    m.writeToNBT(compound1)
                    list.appendTag(compound1)
                })
                compound.setTag("value", list)
                compound.setString("dataType", "modelPartA")
            case i: ItemStack =>
                i.writeToNBT(compound)
                compound.setString("dataType", "itemStack")
            case iA: Array[ItemStack] =>
                val list = new NBTTagList
                iA.foreach(i => {
                    val compound1 = new NBTTagCompound
                    i.writeToNBT(compound1)
                    list.appendTag(compound1)
                })
                compound.setTag("value", list)
                compound.setString("dataType", "itemStackA")
        }
        compound.setBoolean("dominant", dominant)
    }

    override def readFromNBT(compound: NBTTagCompound) {

    }
}
