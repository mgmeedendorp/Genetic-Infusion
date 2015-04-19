package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IAllele
import seremis.geninfusion.util.UtilNBT._

class Allele(var dominant: Boolean, var alleleData: Any) extends IAllele {

    override def isDominant: Boolean = dominant

    override def getAlleleData: Any = alleleData

    override def writeToNBT(compound: NBTTagCompound) {
        alleleData.writeToNBT(compound, "alleleData")
        dominant.writeToNBT(compound, "dominant")
    }

    override def readFromNBT(compound: NBTTagCompound) {
        alleleData.readFromNBT(compound, "alleleData")
        dominant.readFromNBT(compound, "dominant")
    }
}

object Allele {
    def fromNBT(compound: NBTTagCompound): Allele = {
        var data: Any = AnyRef.readFromNBT(compound, "alleleData")
        val dataType = "".readFromNBT(compound, "alleleData.dataType")

        val dominant = false.readFromNBT(compound, "dominant")

        val allele = new Allele(dominant, data)

        allele
    }
}