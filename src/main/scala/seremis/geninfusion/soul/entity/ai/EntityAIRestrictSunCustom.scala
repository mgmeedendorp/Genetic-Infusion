package seremis.geninfusion.soul.entity.ai

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import seremis.geninfusion.api.soul.IEntitySoulCustom

class EntityAIRestrictSunCustom(var entity: IEntitySoulCustom) extends EntityAIBase {

    var living: EntityLiving = entity.asInstanceOf[EntityLiving]

    override def shouldExecute(): Boolean = entity.getWorld_I.isDaytime

    override def startExecuting() {
        this.living.getNavigator.setAvoidSun(true)
    }

    override def resetTask() {
        this.living.getNavigator.setAvoidSun(false)
    }
}