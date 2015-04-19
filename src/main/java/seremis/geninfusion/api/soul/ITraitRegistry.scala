package seremis.geninfusion.api.soul

trait ITraitRegistry {

    /**
     * Register an ITrait. Registering an ITrait wil enable this trait's methods to be called to change or add
     * functionality to an Entity.
     *
     * @param name  a name for the ITrait.
     * @param trait The instance of the ITrait
     */
    def registerTrait(name: String, `trait`: ITrait)

    /**
     * Remove an ITrait from registry. This will completely stop the trait from being executed ever.
     *
     * @param name the name of the ITrait
     */
    def unregisterTrait(name: String)

    /**
     * Remove an ITrait from registry. This will completely stop the trait from being executed ever.
     *
     * @param trait the ITrait to be removed.
     */
    def unregisterTrait(`trait`: ITrait)

    /**
     * Returns the instance of the ITrait that was registered with the passed name.
     *
     * @param name The name of the trait
     * @return The ITrait instance that has this name
     */
    def getTrait(name: String): ITrait

    /**
     * Returns the name of an ITrait from it's instance.
     *
     * @param trait The ITrait instance
     * @return The name of the ITrait
     */
    def getName(`trait`: ITrait): String

    /**
     * Returns the Id of an ITrait from it's instance.
     *
     * @param trait The ITrait instance
     * @return The id of the ITrait
     */
    def getId(`trait`: ITrait): Int

    /**
     * Returns the Id of an ITrait from it's name.
     *
     * @param name The name of the ITrait
     * @return The id of the ITrait
     */
    def getId(name: String): Int

    /**
     * Returns an Array of all registered ITraits.
     *
     * @return An Array of ITraits.
     */
    def getTraits: Array[ITrait]
}
