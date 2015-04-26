package seremis.geninfusion.api.soul;

/**
 * Animation type for animations. If animations have the same animation type, they cannot run at the same time.
 * Animation type UNDEFINED is meant for animations that run all the time and can be interrupted at any time.
 */
public enum EnumAnimationType {
    UNDEFINED,
    HEAD,
    LEGS,
    ARMS,
    BODY,
    OTHER
}
