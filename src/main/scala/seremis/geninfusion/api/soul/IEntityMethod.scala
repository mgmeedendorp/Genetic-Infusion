package seremis.geninfusion.api.soul

trait IEntityMethod[T] {

    def callMethod(entity: IEntitySoulCustom, preventSuperCall: () => Unit, args: Any*): Option[T]
}
