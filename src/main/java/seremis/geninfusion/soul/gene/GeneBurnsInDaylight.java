package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneBurnsInDaylight implements IGene {

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityMob) {
			return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
		}
		return null;
	}

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleBoolean.class;
	}
}
