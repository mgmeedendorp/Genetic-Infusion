package seremis.geninfusion.soul.entity.animation

import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul.{AnimationType, EnumAnimationType, IEntitySoulCustom}

class AnimationHead extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = getModelHead(entity) != null

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float): Unit = {
        val living = entity.asInstanceOf[EntityLiving]

        val headParts = getModelHead(entity)

        for(head <- headParts) {
            head.rotateAngleY = rotationYawHead / (180F / PI)
            head.rotateAngleX = rotationPitch / (180F / PI)

            if(living.isSneaking) {
                head.rotationPointY = 1.0F
            } else {
                head.rotationPointY = head.initialRotationPointY
            }
        }
    }

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false

    override def getAnimationType: AnimationType = EnumAnimationType.UNDEFINED

    override def stopAnimation(entity: IEntitySoulCustom) {}
}
