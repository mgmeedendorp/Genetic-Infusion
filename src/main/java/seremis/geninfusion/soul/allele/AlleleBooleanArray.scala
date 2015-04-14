package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleBooleanArray(isDominant: Boolean, var value: Array[Boolean]) extends Allele(isDominant, EnumAlleleType.BOOLEAN_ARRAY) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Boolean]].drop(1))
    }

    def this(compound: NBTTagCompound) {
        this(true, null)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("length", value.length)
        for (i <- 0 until value.length) {
            compound.setBoolean("value" + i, value(i))
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = Array.ofDim[Boolean](compound.getInteger("length"))
        for (i <- 0 until value.length) {
            value(i) = compound.getBoolean("value" + i)
        }
    }
}