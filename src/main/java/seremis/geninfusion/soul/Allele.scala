package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.{EnumAlleleType, IAllele}

class Allele(var dominant: Boolean, var alleleData: Any, var alleleType: EnumAlleleType) extends IAllele {

    override def isDominant: Boolean = dominant

    override def getAlleleData: Any = alleleData
    override def getAlleleType: EnumAlleleType = alleleType

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("alleleType", alleleType.ordinal())
        alleleType.writeValueToNBT(compound, "alleleData", alleleData)
        compound.setBoolean("dominant", dominant)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        EnumAlleleType.values()(compound.getInteger("alleleType"))
        alleleData = alleleType.readValueFromNBT(compound, "alleleData")
        dominant = compound.getBoolean("dominant")
        compound
    }
}

object Allele {
    def fromNBT[_](compound: NBTTagCompound): Allele = {
        val dominant = compound.getBoolean("dominant")
        val alleleType = EnumAlleleType.values()(compound.getInteger("alleleType"))
        val alleleData = alleleType.readValueFromNBT(compound, "alleleData")

        new Allele(dominant, alleleData, alleleType)
    }
}