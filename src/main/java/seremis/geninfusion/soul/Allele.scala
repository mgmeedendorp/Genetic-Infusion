package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.{IAllele, EnumAlleleType}

object Allele {
    def readAlleleFromNBT(compound: NBTTagCompound): IAllele = {
        val alleleType = EnumAlleleType.values()(compound.getInteger("type"))

        try {
            val constructor = alleleType.clazz.getConstructor(classOf[NBTTagCompound])

            return constructor.newInstance(compound)
        } catch {
            case e: Exception => e.printStackTrace()
        }
        null
    }
}

abstract class Allele(var dominant: Boolean, var alleleType: EnumAlleleType) extends IAllele {

    def this(compound: NBTTagCompound) {
        this()
        readFromNBT(compound)
    }

    override def isDominant: Boolean = dominant

    override def writeToNBT(compound: NBTTagCompound) {
        compound.setBoolean("isDominant", dominant)
        compound.setInteger("type", alleleType.ordinal())
    }

    override def readFromNBT(compound: NBTTagCompound) {
        dominant = compound.getBoolean("isDominant")
        alleleType = EnumAlleleType.values()(compound.getInteger("type"))
    }

    override def toString: String = {
        "Allele[type: " + alleleType + ", isDominant: " + dominant + "]"
    }
}
