package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IAlleleType

class AlleleType[T](clzz: Class[T]) extends IAlleleType[T] {

    override def getAlleleTypeClass: Class[T] = clzz

    override def writeToNBT(compound: NBTTagCompound, value: T) = ???

    override def readFromNBT(compound: NBTTagCompound): T = ???
}
