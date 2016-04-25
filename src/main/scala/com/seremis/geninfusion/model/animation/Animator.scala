package com.seremis.geninfusion.model.animation

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.model.IModel
import com.seremis.geninfusion.api.model.animation.{IAnimation, IAnimator}

import scala.collection.mutable.Queue

class Animator(model: IModel) extends IAnimator {

    protected val queuedAnimations: Queue[(IAnimation, () => Unit)] = Queue()

    override def animate() {
        //packet handling and stuff
    }

    //Should only be called on server
    override def enqueueAnimation(animationName: String, startCallback: () => Unit): Unit = {
        queuedAnimations += (GIApiInterface.animationRegistry.getAnimationForName(animationName) -> startCallback)
    }
}
