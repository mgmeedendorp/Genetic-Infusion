package seremis.geninfusion.api.soul

/**
 * Animation type for animations. If animations have the same animation type, they cannot run at the same time.
 * Animation type UNDEFINED is meant for animations that run all the time and can be interrupted at any time.
 */
//TODO replace this with a registry for animationParts
object EnumAnimationType {
    final val UNDEFINED = new EnumAnimationType("undefined")
    final val HEAD = new EnumAnimationType("head")
    final val LEGS = new EnumAnimationType("legs")
    final val ARMS = new EnumAnimationType("arms")
    final val BODY = new EnumAnimationType("body")
    final val WINGS = new EnumAnimationType("wings")
    final val OTHER = new EnumAnimationType("other")
}
class EnumAnimationType(name: String) {

    def getName = name

    override def equals(o: Any) = o.isInstanceOf[EnumAnimationType] && o.asInstanceOf[EnumAnimationType].getName == getName
}
