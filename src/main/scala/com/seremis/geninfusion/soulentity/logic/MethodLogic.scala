package com.seremis.geninfusion.soulentity.logic

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.soulentity.{IEntityMethod, ISoulEntity}
import com.seremis.geninfusion.api.util.TypedName

import scala.collection.mutable.HashMap

class MethodLogic(entity: ISoulEntity) {

    val methods = GIApiInterface.entityMethodRegistry.getMethodsForSoul(entity.getSoul)

    val finalMethods: HashMap[TypedName[_], (Seq[Any]) => _] = HashMap()

    def callMethod[A](name: TypedName[A], superMethod: () => A, args: Any*): A = {
        val option = methods.get(name)

        if(option.nonEmpty) {
            for(method <- option.get) {
                val returnValue = method.asInstanceOf[IEntityMethod[A]].callMethod(entity, superMethod, args)

                if(returnValue.nonEmpty) {
                    return returnValue.get
                }
            }
        }

        val option2 = finalMethods.get(name)

        if(option2.nonEmpty) {
            return option2.get.asInstanceOf[(Seq[Any]) => A](args)
        }

        superMethod()
    }

    def addFinalMethod[A](name: TypedName[A], superMethod: (Seq[Any]) => A): Unit = {
        finalMethods += (name -> superMethod)
    }
}
