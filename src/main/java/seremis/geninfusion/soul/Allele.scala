package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IAllele
import seremis.geninfusion.util.UtilNBT._

import scala.reflect.ClassTag

class Allele[T: ClassTag](var dominant: Boolean, var alleleData: T) extends IAllele[T] {

    def this() {
        this(false, AnyRef.asInstanceOf[T])
    }

    override def isDominant: Boolean = dominant

    override def getAlleleData: T = alleleData

    override def writeToNBT(compound: NBTTagCompound) {
        alleleData.writeToNBT(compound, "alleleData")
        dominant.readFromNBT(compound, "dominant")
    }

    override def readFromNBT(compound: NBTTagCompound) {
        alleleData.readFromNBT(compound, "alleleData")
        dominant.readFromNBT(compound, "dominant")
    }
}

object Allele {
    def fromNBT(compound: NBTTagCompound): Allele[Any] = {
        val allele = new Allele[Any]()
        allele.readFromNBT(compound)
        allele
    }
}