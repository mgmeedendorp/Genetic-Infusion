package com.seremis.geninfusion.registry

import com.seremis.geninfusion.api.GIApiInterface.IDataTypeRegistry
import com.seremis.geninfusion.api.util.DataType
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

    @throws[IllegalArgumentException]
    override def readValueFromNBT[A](compound: NBTTagCompound, name: String, dataClass: Class[A]): A = getDataTypeForClass(dataClass).readFromNBT(compound, name)

    @throws[IllegalArgumentException]
    override def writeValueToNBT[A](compound: NBTTagCompound, name: String, dataClass: Class[A], data: A): Unit = getDataTypeForClass(dataClass).writeToNBT(compound, name, data)

    @throws[IllegalArgumentException]
    def noRegisteredDataType(clzz: Class[_]) = throw new IllegalArgumentException("There is no registered DataType for class " + clzz.getName + ". Make sure to register the DataType before using it!.")
}
