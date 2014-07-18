package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class GeneCreatureAttribute implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleInteger.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal()), new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal()));
		}
        return new Chromosome(new AlleleInteger(true, EnumCreatureAttribute.ARTHROPOD.ordinal()), new AlleleInteger(true, EnumCreatureAttribute.ARTHROPOD.ordinal()));
	}
}
