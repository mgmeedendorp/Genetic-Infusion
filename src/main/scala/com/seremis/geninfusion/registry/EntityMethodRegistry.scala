package com.seremis.geninfusion.registry

import com.seremis.geninfusion.api.GIApiInterface.IEntityMethodRegistry
import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.soulentity.IEntityMethod
import com.seremis.geninfusion.api.util.TypedName

import scala.collection.mutable.{HashMap, ListBuffer}

class EntityMethodRegistry extends IEntityMethodRegistry {

    val mappedMethods: HashMap[TypedName[_], ListBuffer[IEntityMethod[_]]] = HashMap()

    override def register[A](name: TypedName[A], method: IEntityMethod[A]): Unit = {
        var list: ListBuffer[IEntityMethod[_]] = mappedMethods.getOrElse(name, ListBuffer())

        list += method

        mappedMethods += (name -> list)
    }

    @throws[IllegalArgumentException]
    override def getMethodsForName[A](name: TypedName[A]): List[IEntityMethod[A]] = {
        val option = mappedMethods.get(name)

        if(option.nonEmpty) {
            option.get.toList.asInstanceOf[List[IEntityMethod[A]]]
        } else {
            methodNotRegistered(name.name)
        }
    }

    @throws[IllegalArgumentException]
    def methodNotRegistered(name: String) = throw new IllegalArgumentException("The IEntityMethod '" + name + "' is not registered. Make sure to register it before using it!")

    override def hasMethodForName(name: TypedName[_]): Boolean = mappedMethods.get(name).nonEmpty

    override def getAllMethodNames: Array[TypedName[_]] = mappedMethods.keys.to[Array]
    override def getAllMethods: Map[TypedName[_], ListBuffer[IEntityMethod[_]]] = mappedMethods.toMap

    override def getMethodsForSoul(soul: ISoul): Map[TypedName[_], List[IEntityMethod[_]]] = {
        val result: HashMap[TypedName[_], List[IEntityMethod[_]]] = HashMap()

        for((key, value) <- mappedMethods) {
            val methods: ListBuffer[IEntityMethod[_]] = ListBuffer()

            for(method <- value) {
                if(method.shouldCallMethodForSoul(soul)) {
                    methods += method
                }
            }

            if(methods.nonEmpty) {
                result += (key -> methods.toList)
            }
        }

        result.toMap
    }
}
