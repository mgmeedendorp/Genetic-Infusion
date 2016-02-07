package seremis.geninfusion.api.soul

trait IEntityMethodRegistry {

    def register(srgName: String, methodName: String, method: IEntityMethod[_])

    def getMethodByName(methodName: String): Option[IEntityMethod[_]]
    def getMethodBySrgName(srgName: String): Option[IEntityMethod[_]]

    def getMethodName(method: IEntityMethod[_]): Option[String]
    def getMethodSrgName(method: IEntityMethod[_]): Option[String]
}
