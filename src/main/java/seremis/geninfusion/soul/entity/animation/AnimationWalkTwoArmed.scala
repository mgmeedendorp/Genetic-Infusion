package seremis.geninfusion.soul.entity.animation

import net.minecraft.entity.EntityLiving
import net.minecraft.item.EnumAction
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{EnumAnimationType, IEntitySoulCustom}

class AnimationWalkTwoArmed extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = {
        getModelArms(entity).length == 2
    }

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val arms: Array[ModelPart] = getModelArms(entity)
        val body = getModelBody(entity)
        val head = getModelHead(entity)

        arms(0).rotateAngleX = MathHelper.cos(timeModifier * 0.6662F + PI) * 2.0F * limbSwing * 0.5F
        arms(1).rotateAngleX = MathHelper.cos(timeModifier * 0.6662F) * 2.0F * limbSwing * 0.5F
        arms(0).rotateAngleZ = 0.0F
        arms(1).rotateAngleZ = 0.0F

        if(living.isRiding) {
            arms(0).rotateAngleX += -(PI / 5F)
            arms(1).rotateAngleX += -(PI / 5F)
        }

        if(living.getHeldItem != null) {
            arms(0).rotateAngleX = arms(0).rotateAngleX * 0.5F - (Math.PI.toFloat / 10F)
        }

        arms(0).rotateAngleY = 0.0F
        arms(1).rotateAngleY = 0.0F

        val swingProgress = living.getSwingProgress(entity.getFloat("partialTickTime"))

        if(swingProgress > -9990.0F) {
            var f6 = swingProgress

            arms(0).rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F
            arms(0).rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F
            arms(1).rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F
            arms(1).rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F
            arms(0).rotateAngleY += body.rotateAngleY
            arms(1).rotateAngleY += body.rotateAngleY
            arms(1).rotateAngleX += body.rotateAngleY
            f6 = 1.0F - swingProgress
            f6 *= f6
            f6 *= f6
            f6 = 1.0F - f6
            val f7 = MathHelper.sin(f6 * PI)
            val f8: Float = MathHelper.sin(swingProgress * PI) * -(head(0).rotateAngleX - 0.7F) * 0.75F
            arms(0).rotateAngleX = (arms(0).rotateAngleX.toDouble - (f7.toDouble * 1.2D + f8.toDouble)).toFloat
            arms(0).rotateAngleY += body.rotateAngleY * 2.0F
            arms(0).rotateAngleZ = MathHelper.sin(swingProgress * PI) * -0.4F
        }

        if(living.isSneaking) {
            arms(0).rotateAngleX += 0.4F
            arms(1).rotateAngleX += 0.4F
        }

        arms(0).rotateAngleZ += MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
        arms(1).rotateAngleZ -= MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
        arms(0).rotateAngleX += MathHelper.sin(limbSwing * 0.067F) * 0.05F
        arms(1).rotateAngleX -= MathHelper.sin(limbSwing * 0.067F) * 0.05F

        if (living.getHeldItem != null && living.getHeldItem.getItemUseAction == EnumAction.bow) {
            val f6 = 0.0F
            val f7 = 0.0F
            arms(0).rotateAngleZ = 0.0F
            arms(1).rotateAngleZ = 0.0F
            arms(0).rotateAngleY = -(0.1F - f6 * 0.6F) + head(0).rotateAngleY
            arms(1).rotateAngleY = 0.1F - f6 * 0.6F + head(0).rotateAngleY + 0.4F
            arms(0).rotateAngleX = -(PI / 2F) + head(0).rotateAngleX
            arms(1).rotateAngleX = -(PI / 2F) + head(0).rotateAngleX
            arms(0).rotateAngleX -= f6 * 1.2F - f7 * 0.4F
            arms(1).rotateAngleX -= f6 * 1.2F - f7 * 0.4F
            arms(0).rotateAngleZ += MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
            arms(1).rotateAngleZ -= MathHelper.cos(limbSwing * 0.09F) * 0.05F + 0.05F
            arms(0).rotateAngleX += MathHelper.sin(limbSwing * 0.067F) * 0.05F
            arms(1).rotateAngleX -= MathHelper.sin(limbSwing * 0.067F) * 0.05F
        }
    }

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false

    override def getAnimationType: EnumAnimationType = EnumAnimationType.UNDEFINED

    override def stopAnimation(entity: IEntitySoulCustom) {}
}