package seremis.geninfusion.soul.entity.animation

import net.minecraft.entity.EntityLiving
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.{AnimationType, EnumAnimationType, IEntitySoulCustom}

class AnimationTwoLegged extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = getModelLegs(entity).length == 2 && getModelLeftLegs(entity).length == 1 && getModelRightLegs(entity).length == 1

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val leftLeg = getModelLeftLegs(entity)(0)
        val rightLeg = getModelRightLegs(entity)(0)

        leftLeg.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F) * 1.4F * limbSwing
        rightLeg.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F + PI) * 1.4F * limbSwing
        leftLeg.rotateAngleY = 0.0F
        rightLeg.rotateAngleY = 0.0F

        if(living.isRiding) {
            leftLeg.rotateAngleX = -(PI * 2F / 5F)
            rightLeg.rotateAngleX = -(PI * 2F / 5F)
            leftLeg.rotateAngleY = PI / 10F
            rightLeg.rotateAngleY = -(PI / 10F)
        }

        if(living.isSneaking) {
            leftLeg.rotationPointZ = 4.0F
            rightLeg.rotationPointZ = 4.0F
            leftLeg.rotationPointY = 9.0F
            rightLeg.rotationPointY = 9.0F
        } else {
            leftLeg.rotationPointZ = leftLeg.initialRotationPointZ
            rightLeg.rotationPointZ = rightLeg.initialRotationPointZ
            leftLeg.rotationPointY = leftLeg.initialRotationPointY
            rightLeg.rotationPointY = rightLeg.initialRotationPointY
        }
    }

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false

    override def getAnimationType: AnimationType = EnumAnimationType.UNDEFINED

    override def stopAnimation(entity: IEntitySoulCustom) {}
}