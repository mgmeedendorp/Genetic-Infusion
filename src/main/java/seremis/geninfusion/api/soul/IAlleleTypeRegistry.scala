package seremis.geninfusion.api.soul

trait IAlleleTypeRegistry {

    /**
     * Call this method to register an IAlleleType
     * @param alleleType The IAlleleType to register.
     */
    def registerAlleleType(alleleType: IAlleleType)

    /**
     * Returns the IAlleleType for the provided Class.
     * @param clzz The Class to find an IAlleleType for.
     * @return The IAlleleType for this class.
     */
    def getAlleleTypeForClass(clzz: Class[_]): IAlleleType

    /**
     * Checks whether there is a registered IAlleleType for this class.
     * @param clzz The Class to check for.
     * @return If an IAlleleType is registered with this class.
     */
    def hasClassAlleleType(clzz: Class[_]): Boolean
}
