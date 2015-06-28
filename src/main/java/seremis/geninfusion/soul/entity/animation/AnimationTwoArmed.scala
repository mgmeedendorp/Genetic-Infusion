package seremis.geninfusion.soul.entity.animation

import net.minecraft.entity.EntityLiving
import net.minecraft.item.EnumAction
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.lib.VariableLib
import seremis.geninfusion.api.soul.{EnumAnimationType, IEntitySoulCustom}
import seremis.geninfusion.api.util.render.model.ModelPart

class AnimationTwoArmed extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = return getModelArms(entity).length == 2 && getModelLeftArms(entity).length == 1 && getModelRightArms(entity).length == 1

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val leftArm: ModelPart = getModelLeftArms(entity)(0)
        val rightArm: ModelPart = getModelRightArms(entity)(0)
        val body = getModelBody(entity)
        val head = getModelHead(entity)

        leftArm.rotationPointX = leftArm.initialRotationPointX
        leftArm.rotationPointY = leftArm.initialRotationPointY
        leftArm.rotationPointZ = leftArm.initialRotationPointZ
        rightArm.rotationPointX = rightArm.initialRotationPointX
        rightArm.rotationPointY = rightArm.initialRotationPointY
        rightArm.rotationPointZ = rightArm.initialRotationPointZ

        if(!armsHorizontal(entity) || (living.getHeldItem != null && living.getHeldItem.getItemUseAction == EnumAction.bow)) {
            leftArm.rotateAngleX = leftArm.initialRotateAngleX
            rightArm.rotateAngleX = rightArm.initialRotateAngleX
            leftArm.rotateAngleX += MathHelper.cos(timeModifier * 0.6662F + PI) * 2.0F * limbSwing * 0.5F
            rightArm.rotateAngleX += MathHelper.cos(timeModifier * 0.6662F) * 2.0F * limbSwing * 0.5F
            leftArm.rotateAngleZ = leftArm.initialRotateAngleZ
            rightArm.rotateAngleZ = rightArm.initialRotateAngleZ

            if(living.isRiding) {
                leftArm.rotateAngleX += -(PI / 5F)
                rightArm.rotateAngleX += -(PI / 5F)
            }

            if(living.getHeldItem != null) {
                leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - (PI / 10F)
            }

            leftArm.rotateAngleY = leftArm.initialRotateAngleY
            rightArm.rotateAngleY = rightArm.initialRotateAngleY

            val swingProgress = living.getSwingProgress(entity.getFloat(VariableLib.EntityPartialTickTime))

            if(swingProgress > -9990.0F) {
                var f6 = swingProgress

                if(body != null) {
                    leftArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F
                    leftArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F
                    rightArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F
                    rightArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F
                    leftArm.rotateAngleY += body.rotateAngleY
                    rightArm.rotateAngleY += body.rotateAngleY
                    rightArm.rotateAngleX += body.rotateAngleY
                }
                f6 = 1.0F - swingProgress
                f6 *= f6
                f6 *= f6
                f6 = 1.0F - f6
                val f7 = MathHelper.sin(f6 * PI)
                val f8: Float = MathHelper.sin(swingProgress * PI) * -(head(0).rotateAngleX - 0.7F) * 0.75F
                leftArm.rotateAngleX = (leftArm.rotateAngleX.toDouble - (f7.toDouble * 1.2D + f8.toDouble)).toFloat
                if(body != null)
                    leftArm.rotateAngleY += body.rotateAngleY * 2.0F
                leftArm.rotateAngleZ = MathHelper.sin(swingProgress * PI) * -0.4F
            }

            if(living.getHeldItem != null && living.getHeldItem.getItemUseAction == EnumAction.bow) {
                val f6 = 0.0F
                val f7 = 0.0F
                leftArm.rotateAngleZ = leftArm.initialRotateAngleZ
                rightArm.rotateAngleZ = rightArm.initialRotateAngleZ
                leftArm.rotateAngleY = -(0.1F - f6 * 0.6F) + head(0).rotateAngleY
                rightArm.rotateAngleY = 0.1F - f6 * 0.6F + head(0).rotateAngleY + 0.4F
                leftArm.rotateAngleX = -(PI / 2F) + head(0).rotateAngleX
                rightArm.rotateAngleX = -(PI / 2F) + head(0).rotateAngleX
                leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F
                rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F
                leftArm.rotateAngleZ += MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
                rightArm.rotateAngleZ -= MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
                leftArm.rotateAngleX += MathHelper.sin(limbSwing * 0.067F) * 0.05F
                rightArm.rotateAngleX -= MathHelper.sin(limbSwing * 0.067F) * 0.05F
            }

            if(living.isSneaking) {
                leftArm.rotateAngleX += 0.4F
                rightArm.rotateAngleX += 0.4F
            }

            leftArm.rotateAngleZ += MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
            rightArm.rotateAngleZ -= MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
            leftArm.rotateAngleX += MathHelper.sin(limbSwing * 0.067F) * 0.05F
            rightArm.rotateAngleX -= MathHelper.sin(limbSwing * 0.067F) * 0.05F

        } else {
            val swingProgress = living.getSwingProgress(entity.getFloat(VariableLib.EntityPartialTickTime))

            val f6 = MathHelper.sin(swingProgress * PI)
            val f7 = MathHelper.sin((1.0F - (1.0F - swingProgress) * (1.0F - swingProgress)) * PI)
            leftArm.rotateAngleZ = leftArm.initialRotateAngleZ
            rightArm.rotateAngleZ = rightArm.initialRotateAngleZ
            leftArm.rotateAngleY = leftArm.initialRotateAngleY
            rightArm.rotateAngleY = rightArm.initialRotateAngleY
            leftArm.rotateAngleY -= 0.1F - f6 * 0.6F
            rightArm.rotateAngleY += 0.1F - f6 * 0.6F
            leftArm.rotateAngleX = leftArm.initialRotateAngleX
            rightArm.rotateAngleX = rightArm.initialRotateAngleX
            leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F
            rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F
            leftArm.rotateAngleZ += MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F
            rightArm.rotateAngleZ -= MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F
            leftArm.rotateAngleX += MathHelper.sin(specialRotation * 0.067F) * 0.05F
            rightArm.rotateAngleX -= MathHelper.sin(specialRotation * 0.067F) * 0.05F
        }
    }

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false

    override def getAnimationType: EnumAnimationType = EnumAnimationType.Undefined

    override def stopAnimation(entity: IEntitySoulCustom) {}
}