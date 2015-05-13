package seremis.geninfusion.api.soul

import net.minecraft.nbt.NBTTagCompound

trait IAlleleType[T] {

    /**
     * Returns the class this allele type provides.
     * @return A class
     */
    def getAlleleTypeClass: Class[T]

    def writeToNBT(compound: NBTTagCompound, value: T)

    def readFromNBT(compound: NBTTagCompound): T
}
