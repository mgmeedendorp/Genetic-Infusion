package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

object EntityMethodHandler {

    val registry = SoulHelper.entityMethodRegistry

    def handleMethodCall[T](entity: IEntitySoulCustom, srgName: String, args: Any*): T = {
        val methods = registry.getMethodsForSrgName(srgName)

        if(methods.nonEmpty) {
            try {
                methods.get.callMethod(entity, args).asInstanceOf[T]
            } catch {
                case e: ClassCastException => throw new ClassCastException("The IEntityMethod for " + srgName + "  has the wrong return type!")
            }
        } else {
            throw new IllegalArgumentException("There is no IEntityMethod for method with srgName: " + srgName + ".")
        }
    }
}
