package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAttackDamage implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleFloat.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleFloat(true, 3.0F), new AlleleFloat(false, 3.0F));
		}
		return null;
	}	
}
