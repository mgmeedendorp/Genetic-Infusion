package seremis.geninfusion.api.soul.util.animation;

public enum EnumAnimationPartType {

    ANIMATION_PART_LINEAR(AnimationPartLinear.class),
    ANIMATION_PART_WAVE(AnimationPartWave.class);

    public Class<? extends AnimationPart> clazz;

    private EnumAnimationPartType(Class<? extends AnimationPart> clazz) {
        this.clazz = clazz;
    }
}
