package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.{IAlleleType, SoulHelper, IAllele}

class Allele(var dominant: Boolean, var alleleData: Any, var alleleType: IAlleleType) extends IAllele {

    override def isDominant: Boolean = dominant

    override def getAlleleData: Any = alleleData
    override def getAlleleType: IAlleleType = alleleType

    def this(dominant: Boolean, alleleData: Any, alleleType: Class[_]) {
        this(dominant, alleleData, SoulHelper.alleleTypeRegistry.getAlleleTypeForClass(alleleType))
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("alleleType", SoulHelper.alleleTypeRegistry.getAlleleTypeId(alleleType))
        if(alleleData != null)
            alleleType.writeToNBT(compound, "alleleData", alleleData)
        compound.setBoolean("dominant", dominant)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        alleleType = SoulHelper.alleleTypeRegistry.getAlleleTypeFromId(compound.getInteger("alleleType"))
        if(compound.hasKey("alleleData"))
            alleleData = alleleType.readFromNBT(compound, "alleleData")
        dominant = compound.getBoolean("dominant")
        compound
    }

    override def toString: String = {
        "Allele:[isDominant: " + isDominant + ", alleleType: " + alleleType + ", alleleData: " + alleleData + "]"
    }
}

object Allele {
    def fromNBT(compound: NBTTagCompound): Allele = {
        val dominant = compound.getBoolean("dominant")
        val alleleType = SoulHelper.alleleTypeRegistry.getAlleleTypeFromId(compound.getInteger("alleleType"))
        val alleleData = alleleType.readFromNBT(compound, "alleleData")

        new Allele(dominant, alleleData, alleleType)
    }
}