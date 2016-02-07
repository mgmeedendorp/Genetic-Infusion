package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

object EntityMethodHandler {

    val registry = SoulHelper.entityMethodRegistry

    def handleMethodCall[T](entity: IEntitySoulCustom, srgName: String, args: Any*): T = {
        val method = registry.getMethodBySrgName(srgName)

        if(method.nonEmpty) {
            try {
                return method.get.callMethod(entity, args).asInstanceOf[T]
            } catch {
                case e: ClassCastException => throw new ClassCastException("The IEntityMethod for " + srgName + " with name  " + registry.getMethodName(method.get).get + "  has the wrong return type!")
            }
        } else {
            throw new NullPointerException("No IEntityMethod registered for method (" + srgName + ").")
        }
    }
}
