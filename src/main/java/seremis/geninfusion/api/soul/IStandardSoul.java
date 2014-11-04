package seremis.geninfusion.api.soul;

/**
 * @author Seremis
 */
public interface IStandardSoul {

    /**
     * This method returns a standard Soul for one entity passed in the StandardSoulRegistry.register() method.
     *
     * @return The soul of the entity.
     */
    public IChromosome getChromosomeFromGene(String gene);
}
