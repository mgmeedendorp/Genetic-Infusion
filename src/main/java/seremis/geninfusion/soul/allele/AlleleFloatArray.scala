package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleFloatArray(isDominant: Boolean, var value: Array[Float]) extends Allele(isDominant, EnumAlleleType.FLOAT_ARRAY) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args.drop(1).asInstanceOf[Array[Float]])
    }

    def this(compound: NBTTagCompound) {
        this(false, null)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("length", value.length)
        for (i <- 0 until value.length) {
            compound.setFloat("value" + i, value(i))
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = Array.ofDim[Float](compound.getInteger("length"))
        for (i <- 0 until value.length) {
            value(i) = compound.getFloat("value" + i)
        }
    }
}
