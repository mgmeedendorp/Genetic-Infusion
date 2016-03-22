package com.seremis.geninfusion.api

import com.seremis.geninfusion.api.genetics.{IGene, IGeneData, ISoul}
import com.seremis.geninfusion.api.util.{DataType, TypedName}
import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound

object GIApiInterface {

    var dataTypeRegistry: IDataTypeRegistry = _
    var geneDefaultsRegistry: IGeneDefaultsRegistry = _
    var geneRegistry: IGeneRegistry = _

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

    trait IGeneDefaultsRegistry {
        def register(clzz: Class[_ <: EntityLiving], defaultValue: IGeneData[_])

        def isClassRegistered(clzz: Class[_ <: EntityLiving]): Boolean

        @throws[IllegalArgumentException]
        def getDefaultValueForClass[A](clzz: Class[_ <: EntityLiving], geneName: TypedName[A]): IGeneData[A]

        @throws[IllegalArgumentException]
        def getSoulForClass(clzz: Class[_ <: EntityLiving]): ISoul
    }

    trait IGeneRegistry {
        def register(gene: IGene[_])
        def register(name: IGeneData[_])

        @throws[IllegalArgumentException]
        def getGeneForName[A](name: TypedName[A]): IGene[A]
        def hasGeneForName(defaultValue: TypedName[_]): Boolean

        def getAllGenes: Array[IGene[_]]
    }
}
