package seremis.geninfusion.api.soul

import scala.collection.mutable.ListBuffer

trait IEntityMethodRegistry {

    def register(srgName: String, method: IEntityMethod[_])

    def getMethodsForSrgName(srgName: String): Option[ListBuffer[IEntityMethod[_]]]
}
