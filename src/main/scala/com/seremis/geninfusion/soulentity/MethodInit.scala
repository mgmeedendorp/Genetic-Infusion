package com.seremis.geninfusion.soulentity

import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.soulentity.{IEntityMethod, ISoulEntity}

class MethodInit extends IEntityMethod[Unit] {
    override def shouldCallMethodForSoul(entity: ISoul): Boolean = true

    override def callMethod(entity: ISoulEntity, superMethod: () => Unit, args: Any*): Option[Unit] = {
        println("SUCCESS!!")
        println(entity.getSoul)
        Some(superMethod())
    }
}
