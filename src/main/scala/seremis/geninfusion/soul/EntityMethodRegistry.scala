package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IEntityMethod, IEntityMethodRegistry}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

class EntityMethodRegistry extends IEntityMethodRegistry {

    var methods: HashMap[String, ListBuffer[IEntityMethod[_]]] = HashMap()

    override def register(srgName: String, method: IEntityMethod[_]) {
        var list: ListBuffer[IEntityMethod[_]] = ListBuffer()

        if(methods.contains(srgName))
            list = methods.get(srgName).get

        list += method

        methods += srgName -> list
    }

    override def getMethodsForSrgName(srgName: String): Option[ListBuffer[IEntityMethod[_]]] = {
        methods.get(srgName)
    }
}
