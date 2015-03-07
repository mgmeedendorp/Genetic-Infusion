package seremis.geninfusion.api.soul;

public interface IAnimation {

    /**
     * If this animation applies to this entity. This only gets called in the entity's first render tick!
     * @param entity The entity to be animated.
     * @return Whether this animation applies to the passed entity.
     */
    public boolean canAnimateEntity(IEntitySoulCustom entity);

    /**
     * Should this animation be started this tick? This gets called every tick if canAnimateEntity() returns true.
     * @param entity The entity to be animated.
     * @return Whether this animation should start.
     */
    public boolean shouldStartAnimation(IEntitySoulCustom entity);

    /**
     * Start this animation. This gets called at the start of an animation.
     * @param entity The entity to be animated.
     */
    public void startAnimation(IEntitySoulCustom entity);

    /**
     * Checks if this animation should continue executing, checked before every animate() call. If this returns false, the animation will stop.
     * @param entity The entity to be animated.
     * @return If this animation should continue
     */
    public boolean continueAnimation(IEntitySoulCustom entity);

    /**
     * Animate this entity.
     * @param entity the entity to be animated
     * @param timeModifier Increases over time. Has the same value as the first parameter in the setRotationAngles method in the Model class.
     * @param walkSpeed Increases and decreases with the entity speed. (Not sure about the unit of this value). Has he same value as the 2nd parameter in setRotationAngles.
     * @param specialRotation The rotation set in the handleRotationFloat method in the render file. Has the same value as the 3rd parameter in setRotationAngles.
     * @param rotationYawHead The rotationYaw of the entity's head. Has the same value as the 4th parameter in setRotationAngles.
     * @param rotationPitch The rotationPitch of the entity. Has the same value as the 5th parameter in setRotationAngles.
     * @param scale The scale this entity should render at. Standard at 0.0625F (1/16). Has the same value as the 6th parameter in setRotationAngles.
     */
    public void animate(IEntitySoulCustom entity, float timeModifier, float walkSpeed, float specialRotation, float rotationYawHead, float rotationPitch, float scale);

    /**
     * Checks if this animation can be interrupted by another animation.
     * @param entity The entity to be animated.
     * @return Whether this animation can be interrupted.
     */
    public boolean canBeInterrupted(IEntitySoulCustom entity);

    /**
     * Returns the animation type of this animation. Animations with the same animation types cannot run concurrently, except for EnumAnimationType.UNDEFINED.
     * @return This animation's animation type.
     */
    public EnumAnimationType getAnimationType();

    /**
     * Reset the animation, gets called when continueAnimation() returns false.
     * @param entity The entity to be animated.
     */
    public void stopAnimation(IEntitySoulCustom entity);
}
