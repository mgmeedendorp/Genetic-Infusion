package seremis.geninfusion.soul.allele

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleClass(isDominant: Boolean, var value: Class[_]) extends Allele(isDominant, EnumAlleleType.CLASS) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Class[_]])
    }

    def this(compound: NBTTagCompound) {
        this(false, null)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setString("value", value.getName)
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        try {
            value = Class.forName(compound.getString("value"))
        } catch {
            case e: ClassNotFoundException => e.printStackTrace()
        }
    }
}