package seremis.geninfusion.api.soul

/**
 * Animation type for animations. If animations have the same animation type, they cannot run at the same time.
 * Animation type UNDEFINED is meant for animations that run all the time and can be interrupted at any time.
 */
//TODO replace this with a registry for animationParts
object EnumAnimationType {
    final val Undefined = new EnumAnimationType("undefined")
    final val Head = new EnumAnimationType("head")
    final val Legs = new EnumAnimationType("legs")
    final val Arms = new EnumAnimationType("arms")
    final val Body = new EnumAnimationType("body")
    final val Wings = new EnumAnimationType("wings")
    final val Other = new EnumAnimationType("other")
}
class EnumAnimationType(name: String) {

    def getName = name

    override def equals(o: Any) = o.isInstanceOf[EnumAnimationType] && o.asInstanceOf[EnumAnimationType].getName == getName
}
