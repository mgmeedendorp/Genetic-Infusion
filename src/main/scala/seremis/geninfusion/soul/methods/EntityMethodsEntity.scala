package seremis.geninfusion.soul.methods

import seremis.geninfusion.api.lib.reflection.VariableLib._
import seremis.geninfusion.api.soul.{IEntityMethod, IEntitySoulCustom}
import seremis.geninfusion.api.util.EntityMethodHelper._

object EntityMethodsEntity {

    class MethodGetEntityId extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getEntityId(entity)

        def getEntityId(implicit entity: IEntitySoulCustom): Int = EntityEntityId.int
    }

    class MethodSetEntityId extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setEntityId(entity, args(0).asInstanceOf[Int])

        def setEntityId(implicit entity: IEntitySoulCustom, id: Int): Unit = EntityEntityId.int(id)
    }
}
