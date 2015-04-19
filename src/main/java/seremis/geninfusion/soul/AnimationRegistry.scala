package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IAnimation, IAnimationRegistry}

class AnimationRegistry extends IAnimationRegistry {

    var animations: Map[String, IAnimation] = Map()
    var names: Map[IAnimation, String] = Map()

    override def register(name: String, animation: IAnimation) {
        if(!animations.contains(name)) {
            animations += (name -> animation)
            names += (animation -> name)
        }
    }

    override def getAnimations: Array[IAnimation] = {
        animations.values.toArray
    }

    override def getName(animation: IAnimation): String = {
        names.get(animation).get
    }

    override def getAnimation(name: String): IAnimation = {
        animations.get(name).get
    }
}
