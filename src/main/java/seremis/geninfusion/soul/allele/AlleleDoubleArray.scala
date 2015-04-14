package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleDoubleArray(isDominant: Boolean, var value: Array[Double]) extends Allele(isDominant, EnumAlleleType.DOUBLE_ARRAY) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Double]].drop(1))
    }

    def this(compound: NBTTagCompound) {
        this(false, null)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("length", value.length)
        for (i <- 0 until value.length) {
            compound.setDouble("value" + i, value(i))
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = Array.ofDim[Double](compound.getInteger("length"))
        for (i <- 0 until value.length) {
            value(i) = compound.getDouble("value" + i)
        }
    }
}
