package seremis.geninfusion.api.soul

trait IAnimationRegistry {
    
    /**
     * Register a new IAnimation.
     *
     * @param animation The IAnimation to register.
     * @param name      The unique name of the IAnimation.
     */
    def register(name: String, animation: IAnimation)

    /**
     * Get an IAnimation.
     *
     * @param name The name of the IAnimation.
     * @return The IAnimation with the name.
     */
    def getAnimation(name: String): IAnimation

    /**
     * Get an IAnimation's name from an instance of IAnimation.
     *
     * @param animation The IAnimation instance to get the name of.
     * @return The name of the IAnimation.
     */
    def getName(animation: IAnimation): String

    /**
     * Get a List of all the registered IAnimations.
     */
    def getAnimations: Array[IAnimation]
}
