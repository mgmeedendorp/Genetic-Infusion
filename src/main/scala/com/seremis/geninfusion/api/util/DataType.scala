package com.seremis.geninfusion.api.util

import net.minecraft.nbt.NBTTagCompound

/**
  * DataTypes allow type-safe storage and retrieval of various types of data
  * from a NBTTagCompound.
  * Every type needs its own DataType, which then can be registered
  * through GIRegistry's dataTypeRegistry. Once registered, the registry's methods allow
  * for retrieval and storage of various data types from and to NBT.
  *
  * @tparam A The type of the data to store.
  */
trait DataType[A] {
    def writeToNBT(compound: NBTTagCompound, name: TypedName[A], data: A)
    def readFromNBT(compound: NBTTagCompound, name: TypedName[A]): A
}
