package seremis.geninfusion.api.soul;

import net.minecraft.world.World;

public interface IInstanceHelper {

    IEntitySoulCustom getSoulEntityInstance(World world, ISoul soul, double x, double y, double z);

    ISoul getISoulInstance(IChromosome[] chromosomes);

    IChromosome getIChromosomeInstance(IAllele allele1, IAllele allele2);

    IAllele getIAlleleInstance(EnumAlleleType alleleType, Object... args);
}
