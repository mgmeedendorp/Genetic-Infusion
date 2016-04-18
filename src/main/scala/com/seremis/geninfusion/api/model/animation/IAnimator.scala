package com.seremis.geninfusion.api.model.animation

trait IAnimator {

    def animate()

    def enqueueAnimation(animationName: String, startCallback: () => Unit)
}
