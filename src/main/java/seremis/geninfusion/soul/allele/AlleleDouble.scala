package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleDouble(isDominant: Boolean, var value: Double) extends Allele(isDominant, EnumAlleleType.DOUBLE) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Double])
    }

    def this(compound: NBTTagCompound) {
        this(false, 0.0F)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setDouble("value", value)
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = compound.getDouble("value")
    }
}
