package com.seremis.geninfusion.api.registry

import com.seremis.geninfusion.api.util.{DataType, TypedName}
import net.minecraft.nbt.NBTTagCompound

object GIRegistry {

    var dataTypeRegistry: IDataTypeRegistry = _

    trait IDataTypeRegistry {
        def register[A](data: DataType[A], clzz: Class[A])

        @throws[IllegalArgumentException]
        def getDataTypeForClass[A](clzz: Class[A]): DataType[A]
        def hasDataTypeForClass(clzz: Class[_]): Boolean

        @throws[IllegalArgumentException]
        def readValueFromNBT[A](compound: NBTTagCompound, name: TypedName[A]): A
        @throws[IllegalArgumentException]
        def writeValueToNBT[A](compound: NBTTagCompound, name: TypedName[A], data: A)
    }


}
