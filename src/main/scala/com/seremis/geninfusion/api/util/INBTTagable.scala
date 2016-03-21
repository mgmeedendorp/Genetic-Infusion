package com.seremis.geninfusion.api.util

import net.minecraft.nbt.NBTTagCompound

/**
  * A simple trait with methods to read and write from NBT.
  */
trait INBTTagable {
    def readFromNBT(compound: NBTTagCompound): NBTTagCompound
    def writeToNBT(compound: NBTTagCompound): NBTTagCompound
}
