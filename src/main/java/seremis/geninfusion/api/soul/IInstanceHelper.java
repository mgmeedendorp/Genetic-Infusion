package seremis.geninfusion.api.soul;

/**
 * @author Seremis
 */
public interface IInstanceHelper {

    ISoul getISoulInstance(IChromosome[] chromosomes);

    IChromosome getIChromosomeInstance(IAllele allele1, IAllele allele2);

    IAllele getIAlleleInstance(EnumAlleleType alleleType, Object... args);
}
