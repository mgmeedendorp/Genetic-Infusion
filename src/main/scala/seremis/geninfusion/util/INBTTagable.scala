package seremis.geninfusion.util

import net.minecraft.nbt.NBTTagCompound

trait INBTTagable {

    def writeToNBT(compound: NBTTagCompound): NBTTagCompound

    def readFromNBT(compound: NBTTagCompound): NBTTagCompound
}
