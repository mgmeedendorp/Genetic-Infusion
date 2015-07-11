package seremis.geninfusion.api.soul

/**
 * Any TileEntity that can absorb an ISoul when an IEntitySoulCustom dies, should implement this trait.
 */
trait ISoulReceptor {

    /**
     * Gets the ISoul this ISoulReceptor currently has.
     * @return The Option[ISoul] this ISoulReceptor contains.
     */
    def getSoul: Option[ISoul]

    /**
     * @return true if this ISoulReceptor has an ISoul.
     */
    def hasSoul: Boolean

    /**
     * Sets the ISoul for this ISoulReceptor to contain.
     * @param soul The ISoul to set.
     */
    def setSoul(soul: Option[ISoul])
}
