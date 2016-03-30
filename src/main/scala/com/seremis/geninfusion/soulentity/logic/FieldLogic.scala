package com.seremis.geninfusion.soulentity.logic

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import com.seremis.geninfusion.api.util.{INBTTagable, TypedName}

import scala.collection.mutable.HashMap

class FieldLogic(entity: ISoulEntity) {

    val dataMap = new FieldLogicData()

    def makePersistent(name: TypedName[_]): Unit = {
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

    def setVar[A](name: TypedName[A], value: A): Unit = {
        dataMap += (name -> (value, false))
    }

    def getVar[A](name: TypedName[A]): A = {
        val option = dataMap.get(name)
        if(option.nonEmpty) {
            option.get._1.asInstanceOf[A]
        } else {
            throw new IllegalArgumentException("Someone tried to get a variable from an entity, but the variable was never set. This should not happen.")
        }
    }

    //TODO fix saving to nbt in nice way.
}
class FieldLogicData extends HashMap[TypedName[_], (Any, Boolean)] with INBTTagable
