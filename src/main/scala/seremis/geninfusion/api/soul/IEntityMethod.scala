package seremis.geninfusion.api.soul

trait IEntityMethod[T] {

    def callMethod(entity: IEntitySoulCustom, args: Any*): T
}
