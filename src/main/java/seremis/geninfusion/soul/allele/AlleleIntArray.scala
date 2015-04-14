package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleIntArray(isDominant: Boolean, var value: Array[Int]) extends Allele(isDominant, EnumAlleleType.INT_ARRAY) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args.drop(1).asInstanceOf[Array[Int]])
    }

    def this(compound: NBTTagCompound) {
        super(compound)
        readFromNBT(compound)
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = compound.getIntArray("value")
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setIntArray("value", value)
    }
}
