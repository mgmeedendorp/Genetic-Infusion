package seremis.geninfusion.api.soul

import net.minecraft.entity.EntityLiving

trait IStandardSoul {

    /**
     * This method returns a standard Soul for one entity passed in the StandardSoulRegistry.register() method.
     *
     * @return The IChromosome for the gene.
     */
    def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome

    /**
     * This method checks if this IStandardSoul applies to this entity.
     *
     * @param entity The entity this IStandardSoul may be suitable for.
     * @return If this IStandardSoul is suitable for the entity.
     */
    def isStandardSoulForEntity(entity: EntityLiving): Boolean
}
