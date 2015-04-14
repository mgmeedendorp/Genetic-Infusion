package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleBoolean(isDominant: Boolean, var value: Boolean) extends Allele(isDominant, EnumAlleleType.BOOLEAN) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Boolean])
    }

    def this(compound: NBTTagCompound) {
        this(false, false)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setBoolean("value", value)
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = compound.getBoolean("value")
    }
}