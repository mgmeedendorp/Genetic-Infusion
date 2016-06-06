package com.seremis.geninfusion.soulentity.logic

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import com.seremis.geninfusion.api.util.VariableName
import com.seremis.geninfusion.util.GIReflectionHelper
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.common.util.Constants

import scala.collection.mutable.{HashMap, ListBuffer}

class FieldLogic(entity: ISoulEntity) {

    protected val dataMap: HashMap[VariableName[_], (Any, Boolean)] = HashMap()
    protected val fields: ListBuffer[String] = {
        var clazz: Any = entity.getClass
        val list: ListBuffer[String] = ListBuffer()

        while(clazz != null) {
            for(field <- clazz.asInstanceOf[Class[_]].getDeclaredFields) {
                list += field.getName
            }
            clazz = clazz.asInstanceOf[Class[_]].getSuperclass
        }
        list
    }

    def makePersistent(name: VariableName[_]): Unit = {
        if(GIApiInterface.dataTypeRegistry.hasDataTypeForClass(name.clzz)) {
            val option = dataMap.get(name)
            if(option.nonEmpty) {
                dataMap += (name -> (option.get._1, true))
            } else {
                throw new IllegalArgumentException("Someone tried to make a variable persistent in an entity, but the variable was never set. This should not happen.")
            }
        } else {
            throw new IllegalArgumentException("Someone tried to make variable '" + name.name + "' of type '" + name.clzz + "' persistent, but there is no registered DataType to write it to NBT!")
        }
    }

    def setVar[A](name: VariableName[A], value: A): Unit = {
        if(fields.contains(name.name)) {
            GIReflectionHelper.setField(entity, name.name, value)
        } else {
            dataMap += (name ->(value, false))
        }
    }

    def getVar[A](name: VariableName[A]): A = {
        if(fields.contains(name.name)) {
            GIReflectionHelper.getField(entity, name.name).asInstanceOf[A]
        } else {
            val option = dataMap.get(name)
            if(option.nonEmpty) {
                option.get._1.asInstanceOf[A]
            } else {
                throw new IllegalArgumentException("Someone tried to get a variable from an entity, but the variable was never set. This should not happen.")
            }
        }
    }

    def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = new NBTTagList

        for((key, value) <- dataMap) {
            if(value._2) {
                val nbt = new NBTTagCompound
                GIApiInterface.dataTypeRegistry.writeValueToNBT(nbt, "key", classOf[VariableName[_]], key)
                GIApiInterface.dataTypeRegistry.writeValueToNBT(nbt, "val", key.asInstanceOf[VariableName[Any]].clzz, value._1)
                list.appendTag(nbt)
            }
        }

        compound.setTag("fieldDataMap", list)

        compound
    }

    def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = compound.getTagList("fieldDataMap", Constants.NBT.TAG_COMPOUND)

        for(i <- 0 until list.tagCount()) {
            val tag = list.getCompoundTagAt(i)

            val key = GIApiInterface.dataTypeRegistry.readValueFromNBT(tag, "key", classOf[VariableName[_]])
            val value = GIApiInterface.dataTypeRegistry.readValueFromNBT(tag, "val", key.clzz)

            dataMap += (key -> (value, true))
        }

        compound
    }
}
