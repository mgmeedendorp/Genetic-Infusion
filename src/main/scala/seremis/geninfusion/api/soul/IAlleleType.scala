package seremis.geninfusion.api.soul

import net.minecraft.nbt.NBTTagCompound

trait IAlleleType {

    /**
     * Returns the class this allele type provides.
     * @return A class
     */
    def getAlleleTypeClass: Class[_]

    def writeToNBT(compound: NBTTagCompound, name: String, value: Any)

    def readFromNBT(compound: NBTTagCompound, name: String): Any
}
