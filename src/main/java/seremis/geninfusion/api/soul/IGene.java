package seremis.geninfusion.api.soul;

public interface IGene {

    Class<? extends IAllele> possibleAlleles();

    /**
     * Mutate a chromosome containing a value associated with this IGene.
     * @param chromosome The chromosome to mutate
     * @return A mutated version of this chromosome
     */
    IChromosome mutate(IChromosome chromosome);

    /**
     * Combine the chromosomes containing a value associated with this gene to one to create offspring.
     * @param chromosome1 The chromosome of one parent
     * @param chromosome2 The chromosome of the other parent
     * @return The chromosome of the offspring
     */
    IChromosome inherit(IChromosome chromosome1, IChromosome chromosome2);
}
