package com.seremis.geninfusion.model.animation

import com.seremis.geninfusion.api.model.IModel
import com.seremis.geninfusion.api.model.animation.IAnimator

class Animator(model: IModel) extends IAnimator {

    override def animate(): Unit = ???

    override def enqueueAnimation(animationName: String, startCallback: () => Unit): Unit = {
        //think about server vs client
    }
}
