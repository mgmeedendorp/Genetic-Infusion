package seremis.geninfusion.soul.entity.animation

import net.minecraft.entity.EntityLiving
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{EnumAnimationType, IEntitySoulCustom}

class AnimationTwoLegged extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = getModelLegs(entity).length == 2

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val legs: Array[ModelPart] = getModelLegs(entity)

        legs(0).rotateAngleX = MathHelper.cos(timeModifier * 0.6662F) * 1.4F * limbSwing
        legs(1).rotateAngleX = MathHelper.cos(timeModifier * 0.6662F + PI) * 1.4F * limbSwing
        legs(0).rotateAngleY = 0.0F
        legs(1).rotateAngleY = 0.0F

        if(living.isRiding) {
            legs(0).rotateAngleX = -(PI * 2F / 5F)
            legs(1).rotateAngleX = -(PI * 2F / 5F)
            legs(0).rotateAngleY = PI/ 10F
            legs(1).rotateAngleY = -(PI / 10F)
        }

        if(living.isSneaking) {
            legs(0).rotationPointZ = 4.0F
            legs(1).rotationPointZ = 4.0F
            legs(0).rotationPointY = 9.0F
            legs(1).rotationPointY = 9.0F
        } else {
            legs(0).rotationPointZ = legs(0).initialRotationPointZ
            legs(1).rotationPointZ = legs(1).initialRotationPointZ
            legs(0).rotationPointY = legs(0).initialRotationPointY
            legs(1).rotationPointY = legs(1).initialRotationPointY
        }
    }

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false

    override def getAnimationType: EnumAnimationType = EnumAnimationType.UNDEFINED

    override def stopAnimation(entity: IEntitySoulCustom) {}
}