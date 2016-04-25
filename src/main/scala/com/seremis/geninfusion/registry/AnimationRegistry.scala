package com.seremis.geninfusion.registry

import com.seremis.geninfusion.api.GIApiInterface.IAnimationRegistry
import com.seremis.geninfusion.api.model.animation.IAnimation

import scala.collection.mutable.HashMap

class AnimationRegistry extends IAnimationRegistry {

    val animations: HashMap[String, IAnimation] = HashMap()

    override def register(name: String, animation: IAnimation) {
        animations += (name -> animation)
    }

    override def getAnimations: Array[IAnimation] = animations.values.to[Array]

    @throws[IllegalArgumentException]
    override def getAnimationForName(name: String): IAnimation = {
        val option = animations.get(name)

        if(option.nonEmpty) {
            option.get
        } else {
            throw new IllegalArgumentException("There is no registered animation with name '" + name + "'! Check your spelling and make sure to register your animation.")
        }
    }
}
