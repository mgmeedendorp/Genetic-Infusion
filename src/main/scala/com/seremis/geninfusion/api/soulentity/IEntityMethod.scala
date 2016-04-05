package com.seremis.geninfusion.api.soulentity

import com.seremis.geninfusion.api.genetics.ISoul

trait IEntityMethod[A] {

    def shouldCallMethodForSoul(entity: ISoul): Boolean
    def callMethod(entity: ISoulEntity, superMethod: () => A, args: Any*): Option[A]
}
