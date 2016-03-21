package com.seremis.geninfusion.registry

import com.seremis.geninfusion.api.registry.GIRegistry.IDataTypeRegistry
import com.seremis.geninfusion.api.util.{DataType, TypedName}
import net.minecraft.nbt.NBTTagCompound

import scala.collection.mutable.HashMap

class DataTypeRegistry extends IDataTypeRegistry {

    private var register: HashMap[Class[_], DataType[_]] = HashMap()

    override def register[A](data: DataType[A], clzz: Class[A]): Unit = {
        register += (clzz -> data)
    }

    @throws[IllegalArgumentException]
    override def getDataTypeForClass[A](clzz: Class[A]): DataType[A] = {
        val option = register.get(clzz).asInstanceOf[Option[DataType[A]]]

        if(option.nonEmpty) {
            option.get
        } else {
            noRegisteredDataType(clzz)
        }
    }

    override def hasDataTypeForClass(clzz: Class[_]): Boolean = register.get(clzz).nonEmpty

    override def readValueFromNBT[A](compound: NBTTagCompound, name: TypedName[A]): A = getDataTypeForClass(name.clzz).readFromNBT(compound, name.name)

    override def writeValueToNBT[A](compound: NBTTagCompound, name: TypedName[A], data: A): Unit = getDataTypeForClass(name.clzz).writeToNBT(compound, name.name, data)

    @throws[IllegalArgumentException]
    def noRegisteredDataType(clzz: Class[_]) = throw new IllegalArgumentException("There is no registered DataType for class " + clzz.getName + ". Make sure to register the DataType before using it!.")
}
