package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IAllele
import seremis.geninfusion.util.UtilNBT._

import scala.reflect.ClassTag

class Allele[T: ClassTag](var dominant: Boolean, var alleleData: T) extends IAllele[T] {

    override def isDominant: Boolean = dominant

    override def getAlleleData: T = alleleData

    override def writeToNBT(compound: NBTTagCompound) {
        alleleData.writeToNBT(compound, "alleleData")
        println(compound)
        dominant.writeToNBT(compound, "dominant")
    }

    override def readFromNBT(compound: NBTTagCompound) {
        alleleData.readFromNBT(compound, "alleleData")
        dominant.readFromNBT(compound, "dominant")
    }
}

object Allele {
    def fromNBT(compound: NBTTagCompound): Allele[_] = {
        var allele: Allele[_] = null
        val dataType = "".readFromNBT(compound, "alleleData")
        val dominant = false.readFromNBT(compound, "dominant")

        allele = new Allele[dataType.type](dominant, dataType)

        println("Allele.fromNBT() compound:  " + compound)
        allele
    }
}