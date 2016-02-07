package seremis.geninfusion.soul

import org.apache.logging.log4j.Level
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.{IEntityMethod, IEntityMethodRegistry}

import scala.collection.immutable.HashMap

class EntityMethodRegistry extends IEntityMethodRegistry {

    var methodNames: HashMap[String, IEntityMethod[_]] = HashMap()
    var methodNamesInv: HashMap[IEntityMethod[_], String] = HashMap()

    var methodSrgNames: HashMap[String, IEntityMethod[_]] = HashMap()
    var methodSrgNamesInv: HashMap[IEntityMethod[_], String] = HashMap()

    override def register(srgName: String, methodName: String, method: IEntityMethod[_]) {
        if(methodSrgNames.contains(srgName)) {
            GeneticInfusion.logger.log(Level.WARN, "Someone is trying to register method " + srgName + " twice! This should not happen.")
        }

        methodNames += methodName -> method
        methodNamesInv += method -> methodName

        methodSrgNames += srgName -> method
        methodSrgNamesInv += method -> srgName
    }

    override def getMethodBySrgName(srgName: String): Option[IEntityMethod[_]] = {
        methodSrgNames.get(srgName)
    }

    override def getMethodByName(methodName: String): Option[IEntityMethod[_]] = {
        methodNames.get(methodName)
    }

    override def getMethodName(method: IEntityMethod[_]): Option[String] = {
        methodNamesInv.get(method)
    }

    override def getMethodSrgName(method: IEntityMethod[_]): Option[String] = {
        methodSrgNamesInv.get(method)
    }
}
