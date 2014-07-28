package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneImmuneToFire implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleBoolean.class;
	}
}
