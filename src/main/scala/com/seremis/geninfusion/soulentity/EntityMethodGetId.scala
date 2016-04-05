package com.seremis.geninfusion.soulentity

import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.soulentity.{IEntityMethod, ISoulEntity}

class EntityMethodGetId extends IEntityMethod[Int] {
    override def shouldCallMethodForSoul(entity: ISoul): Boolean = true

    override def callMethod(entity: ISoulEntity, superMethod: () => Int, args: Any*): Option[Int] = {
        println("Entity Id" + entity.asLiving.getEntityId)

        None
    }
}
