package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;

public interface IStandardSoul {

    /**
     * This method returns a standard Soul for one entity passed in the StandardSoulRegistry.register() method.
     *
     * @return The IChromosome for the gene.
     */
    IChromosome getChromosomeFromGene(EntityLiving entity, String gene);

    /**
     * This method checks if this IStandardSoul applies to this entity.
     *
     * @param entity The entity this IStandardSoul may be suitable for.
     * @return If this IStandardSoul is suitable for the entity.
     */
    boolean isStandardSoulForEntity(EntityLiving entity);
}
