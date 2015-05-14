package seremis.geninfusion.api.soul

/**
 * Animation type for animations. If animations have the same animation type, they cannot run at the same time.
 * Animation type UNDEFINED is meant for animations that run all the time and can be interrupted at any time.
 */
//TODO replace this with a registry for animationParts
object EnumAnimationType {
    final val UNDEFINED = new AnimationType("undefined")
    final val HEAD = new AnimationType("head")
    final val LEGS = new AnimationType("legs")
    final val ARMS = new AnimationType("arms")
    final val BODY = new AnimationType("body")
    final val WINGS = new AnimationType("wings")
    final val OTHER = new AnimationType("other")
}
class AnimationType(name: String) {

    def getName = name

    override def equals(o: Any) = o.isInstanceOf[AnimationType] && o.asInstanceOf[AnimationType].getName == getName
}
