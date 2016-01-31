package seremis.geninfusion.soul.entity.animation

import net.minecraft.util.MathHelper
import seremis.geninfusion.api.render.cuboid.Cuboid
import seremis.geninfusion.api.soul.{EnumAnimationType, IEntitySoulCustom}

class AnimationOctoped extends Animation {

    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = getModelLegs(entity).nonEmpty && getModelLegs(entity).get.length == 8 && getModelLeftLegs(entity).get.length == 4 && getModelRightLegs(entity).get.length == 4

    override def getAnimationType: EnumAnimationType = EnumAnimationType.Undefined

    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    override def startAnimation(entity: IEntitySoulCustom) {}

    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    override def animate(entity: IEntitySoulCustom, timeModifier: Float, walkSpeed: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) = {
        val leftLeg1: Cuboid = getModelLeftLegs(entity).get(0)
        val rightLeg1: Cuboid = getModelRightLegs(entity).get(0)
        val leftLeg2: Cuboid = getModelLeftLegs(entity).get(1)
        val rightLeg2: Cuboid = getModelRightLegs(entity).get(1)
        val leftLeg3: Cuboid = getModelLeftLegs(entity).get(2)
        val rightLeg3: Cuboid = getModelRightLegs(entity).get(2)
        val leftLeg4: Cuboid = getModelLeftLegs(entity).get(3)
        val rightLeg4: Cuboid = getModelRightLegs(entity).get(3)

        leftLeg1.resetInitialRotateAngles()
        rightLeg1.resetInitialRotateAngles()
        leftLeg2.resetInitialRotateAngles()
        rightLeg2.resetInitialRotateAngles()
        leftLeg3.resetInitialRotateAngles()
        rightLeg3.resetInitialRotateAngles()
        leftLeg4.resetInitialRotateAngles()
        rightLeg4.resetInitialRotateAngles()

        val f6 = PI / 4F

        leftLeg1.rotateAngleZ = f6
        rightLeg1.rotateAngleZ = -f6
        leftLeg2.rotateAngleZ = f6 * 0.74F
        rightLeg2.rotateAngleZ = -f6 * 0.74F
        leftLeg3.rotateAngleZ = f6 * 0.74F
        rightLeg3.rotateAngleZ = -f6 * 0.74F
        leftLeg4.rotateAngleZ = f6
        rightLeg4.rotateAngleZ = -f6

        val f7 = 0.3926991F

        leftLeg1.rotateAngleY = -f7 * 2.0F
        rightLeg1.rotateAngleY = f7 * 2.0F
        leftLeg2.rotateAngleY = -f7
        rightLeg2.rotateAngleY = f7
        leftLeg3.rotateAngleY = f7
        rightLeg3.rotateAngleY = -f7
        leftLeg4.rotateAngleY = f7 * 2.0F
        rightLeg4.rotateAngleY = -f7 * 2.0F

        val f9 = -(MathHelper.cos(timeModifier * 0.6662F * 2.0F) * 0.4F) * walkSpeed
        val f10 = -(MathHelper.cos(timeModifier * 0.6662F * 2.0F + PI) * 0.4F) * walkSpeed
        val f11 = -(MathHelper.cos(timeModifier * 0.6662F * 2.0F + (PI / 2F)) * 0.4F) * walkSpeed
        val f12 = -(MathHelper.cos(timeModifier * 0.6662F * 2.0F + (PI * 3F / 2F)) * 0.4F) * walkSpeed
        val f13 = Math.abs(MathHelper.sin(timeModifier * 0.6662F) * 0.4F) * walkSpeed
        val f14 = Math.abs(MathHelper.sin(timeModifier * 0.6662F + PI) * 0.4F) * walkSpeed
        val f15 = Math.abs(MathHelper.sin(timeModifier * 0.6662F + (PI / 2F)) * 0.4F) * walkSpeed
        val f16 = Math.abs(MathHelper.sin(timeModifier * 0.6662F + (PI * 3F / 2F)) * 0.4F) * walkSpeed
        leftLeg1.rotateAngleY += f9
        rightLeg1.rotateAngleY += -f9
        leftLeg2.rotateAngleY += f10
        rightLeg2.rotateAngleY += -f10
        leftLeg3.rotateAngleY += f11
        rightLeg3.rotateAngleY += -f11
        leftLeg4.rotateAngleY += f12
        rightLeg4.rotateAngleY += -f12
        leftLeg1.rotateAngleZ += f13
        rightLeg1.rotateAngleZ += -f13
        leftLeg2.rotateAngleZ += f14
        rightLeg2.rotateAngleZ += -f14
        leftLeg3.rotateAngleZ += f15
        rightLeg3.rotateAngleZ += -f15
        leftLeg4.rotateAngleZ += f16
        rightLeg4.rotateAngleZ += -f16
    }

    override def stopAnimation(entity: IEntitySoulCustom) {}

    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false
}
