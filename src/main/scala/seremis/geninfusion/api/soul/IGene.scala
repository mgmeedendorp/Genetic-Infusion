package seremis.geninfusion.api.soul

trait IGene {

    /**
     * Return the IAlleleType this IGene contains.
     * @return The IAlleleType of this IGene.
     */
    def getAlleleType: IAlleleType

    /**
     * Mutate a chromosome containing a value associated with this IGene.
     *
     * @param chromosome The chromosome to mutate
     * @return A mutated version of this chromosome
     */
    def mutate(chromosome: IChromosome): IChromosome

    /**
     * Combine the chromosomes containing a value associated with this gene to one to create offspring.
     *
     * @param chromosome1 The chromosome of one parent
     * @param chromosome2 The chromosome of the other parent
     * @return The chromosome of the offspring
     */
    def inherit(chromosome1: IChromosome, chromosome2: IChromosome): IChromosome

    /**
     * An advanced version of inherit(). This wil be used instead of inherit() if this IGene is registered with
     * registerCustomInheritance in IGeneRegistry.
     *
     * @param parent1   All of the chromosomes of one parent.
     * @param parent2   All of the chromosomes of the other parent.
     * @param offspring All of the non-special inheritance chromosomes of the entity that is currently being
     *                  constructed.
     * @return The chromosome of the offspring.
     */
    def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome

    /**
     * Turns off mutations for this IGene.
     * @return This IGene
     */
    def noMutations: IGene

    /**
     * Makes this IGene changeable.
     * @return This IGene
     */
    def makeChangable: IGene

    /**
     * Get whether this IGene is changeable (see IGeneRegistry#changeAlleleValue).
     * @return If this IGene is changeable
     */
    def isChangeable: Boolean
}
