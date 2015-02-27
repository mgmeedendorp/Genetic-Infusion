package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;

public interface IStandardSoul {

    /**
     * This method returns a standard Soul for one entity passed in the StandardSoulRegistry.register() method.
     *
     * @return The IChromosome for the gene.
     */
    public IChromosome getChromosomeFromGene(EntityLiving entity, String gene);
}
