package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleFloat(isDominant: Boolean, var value: Float) extends Allele(isDominant, EnumAlleleType.FLOAT) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Float])
    }

    def this(compound: NBTTagCompound) {
        this(false, 0.0F)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setFloat("value", value)
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = compound.getFloat("value")
    }
}
