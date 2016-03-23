package com.seremis.geninfusion.api.soulentity

trait IEntityMethod[A] {

    def shouldCallMethodForEntity(entity: ISoulEntity): Boolean
    def callMethod(entity: ISoulEntity, superMethod: () => Unit, args: Any*): Option[A]
}
