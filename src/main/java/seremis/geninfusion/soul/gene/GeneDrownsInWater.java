package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneDrownsInWater implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleBoolean.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
	}
}
