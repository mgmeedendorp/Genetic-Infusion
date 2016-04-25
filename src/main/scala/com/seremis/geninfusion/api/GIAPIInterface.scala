package com.seremis.geninfusion.api

import com.seremis.geninfusion.api.genetics.{IChromosome, IGene, ISoul}
import com.seremis.geninfusion.api.model.animation.IAnimation
import com.seremis.geninfusion.api.soulentity.IEntityMethod
import com.seremis.geninfusion.api.util.{DataType, TypedName}
import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound

import scala.collection.mutable.ListBuffer

object GIApiInterface {

    var animationRegistry: IAnimationRegistry = _
    var dataTypeRegistry: IDataTypeRegistry = _
    var entityMethodRegistry: IEntityMethodRegistry = _
    var geneDefaultsRegistry: IGeneDefaultsRegistry = _
    var geneRegistry: IGeneRegistry = _

    trait IAnimationRegistry {
        def register(name: String, animation: IAnimation)

        @throws[IllegalArgumentException]
        def getAnimationForName(name: String): IAnimation

        def getAnimations: Array[IAnimation]
    }

    trait IDataTypeRegistry {
        def register[A](data: DataType[A], clzz: Class[A])

        @throws[IllegalArgumentException]
        def getDataTypeForClass[A](clzz: Class[A]): DataType[A]
        def hasDataTypeForClass(clzz: Class[_]): Boolean

        @throws[IllegalArgumentException]
        def readValueFromNBT[A](compound: NBTTagCompound, name: String, dataClass: Class[A]): A
        @throws[IllegalArgumentException]
        def writeValueToNBT[A](compound: NBTTagCompound, name: String, dataClass: Class[A], data: A)
    }

    trait IEntityMethodRegistry {
        def register[A](name: TypedName[A], method: IEntityMethod[A])

        @throws[IllegalArgumentException]
        def getMethodsForName[A](name: TypedName[A]): List[IEntityMethod[A]]
        def hasMethodForName(name: TypedName[_]): Boolean

        def getAllMethodNames: Array[TypedName[_]]
        def getAllMethods: Map[TypedName[_], ListBuffer[IEntityMethod[_]]]

        def getMethodsForSoul(soul: ISoul): Map[TypedName[_], List[IEntityMethod[_]]]
    }

    trait IGeneDefaultsRegistry {
        def register[A](clzz: Class[_ <: EntityLiving], geneName: TypedName[A], defaultValue: IChromosome[A])

        def isClassRegistered(clzz: Class[_ <: EntityLiving]): Boolean

        @throws[IllegalArgumentException]
        def getDefaultValueForClass[A](clzz: Class[_ <: EntityLiving], geneName: TypedName[A]): IChromosome[A]

        @throws[IllegalArgumentException]
        def getSoulForClass(clzz: Class[_ <: EntityLiving]): ISoul
    }

    trait IGeneRegistry {
        def register(gene: IGene[_])
        def register[A](name: TypedName[A], defaultValue: IChromosome[A])

        @throws[IllegalArgumentException]
        def getGeneForName[A](name: TypedName[A]): IGene[A]
        def hasGeneForName(defaultValue: TypedName[_]): Boolean

        def getAllGenes: Array[IGene[_]]
    }
}
