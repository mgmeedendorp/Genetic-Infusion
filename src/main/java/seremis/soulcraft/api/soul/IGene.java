package seremis.soulcraft.api.soul;

import java.util.List;

import net.minecraft.entity.EntityLiving;

public interface IGene {
	
	Class<? extends IAllele> possibleAlleles();
	
	/*
	 * This gets the standard IChromosome for the passed entity. If entity is not specified in method return null (see @link{GeneInvulnerable.java} for example)
	 */
	IChromosome getStandardForEntity(EntityLiving entity);
}
