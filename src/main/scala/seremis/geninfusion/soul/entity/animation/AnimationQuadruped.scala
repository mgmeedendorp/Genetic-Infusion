package seremis.geninfusion.soul.entity.animation

import net.minecraft.util.MathHelper
import seremis.geninfusion.api.render.cuboid.Cuboid
import seremis.geninfusion.api.soul.{EnumAnimationType, IEntitySoulCustom}

class AnimationQuadruped extends Animation {
    /**
     * If this animation applies to this entity. This only gets called in the entity's first render tick!
     *
     * @param entity The entity to be animated.
     * @return Whether this animation applies to the passed entity.
     */
    override def canAnimateEntity(entity: IEntitySoulCustom): Boolean = getModelLeftLegs(entity).nonEmpty && getModelRightLegs(entity).nonEmpty && getModelLegs(entity).get.length == 4 && getModelLeftLegs(entity).get.length == 2 && getModelRightLegs(entity).get.length == 2

    /**
     * Returns the animation type of this animation. Animations with the same animation types cannot run concurrently,
     * except for EnumAnimationType.UNDEFINED.
     *
     * @return This animation's animation type.
     */
    override def getAnimationType: EnumAnimationType = EnumAnimationType.Undefined

    /**
     * Should this animation be started this tick? This gets called every tick if canAnimateEntity() returns true.
     *
     * @param entity The entity to be animated.
     * @return Whether this animation should start.
     */
    override def shouldStartAnimation(entity: IEntitySoulCustom): Boolean = true

    /**
     * Start this animation. This gets called at the start of an animation.
     *
     * @param entity The entity to be animated.
     */
    override def startAnimation(entity: IEntitySoulCustom) {}

    /**
     * Checks if this animation should continue executing, checked before every animate() call. If this returns false,
     * the animation will stop.
     *
     * @param entity The entity to be animated.
     * @return If this animation should continue
     */
    override def continueAnimation(entity: IEntitySoulCustom): Boolean = true

    /**
     * Animate this entity.
     *
     * @param entity          the entity to be animated
     * @param timeModifier    Increases over time. Has the same value as the first parameter in the setRotationAngles
     *                        method in the Model class.
     * @param walkSpeed       Increases and decreases with the entity speed. (Not sure about the unit of this value).
     *                        Has he same value as the 2nd parameter in setRotationAngles.
     * @param specialRotation The rotation set in the handleRotationFloat method in the render file. Has the same value
     *                        as the 3rd parameter in setRotationAngles.
     * @param rotationYawHead The rotationYaw of the entity's head. Has the same value as the 4th parameter in
     *                        setRotationAngles.
     * @param rotationPitch   The rotationPitch of the entity. Has the same value as the 5th parameter in
     *                        setRotationAngles.
     * @param scale           The scale this entity should render at. Standard at 0.0625F (1/16). Has the same value as
     *                        the 6th parameter in setRotationAngles.
     */
    override def animate(entity: IEntitySoulCustom, timeModifier: Float, walkSpeed: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val leftLeg1: Cuboid = getModelLeftLegs(entity).get(0)
        val rightLeg1: Cuboid = getModelRightLegs(entity).get(0)
        val leftLeg2: Cuboid = getModelLeftLegs(entity).get(1)
        val rightLeg2: Cuboid = getModelRightLegs(entity).get(1)

        leftLeg1.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F) * 1.4F * walkSpeed
        rightLeg1.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F + PI) * 1.4F * walkSpeed
        rightLeg2.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F + PI) * 1.4F * walkSpeed
        leftLeg2.rotateAngleX = MathHelper.cos(timeModifier * 0.6662F) * 1.4F * walkSpeed
    }

    /**
     * Reset the animation, gets called when continueAnimation() returns false.
     *
     * @param entity The entity to be animated.
     */
    override def stopAnimation(entity: IEntitySoulCustom) {}

    /**
     * Checks if this animation can be interrupted by another animation.
     *
     * @param entity The entity to be animated.
     * @return Whether this animation can be interrupted.
     */
    override def canBeInterrupted(entity: IEntitySoulCustom): Boolean = false
}
