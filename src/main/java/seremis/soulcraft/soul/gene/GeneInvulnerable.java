package seremis.soulcraft.soul.gene;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.soulcraft.api.soul.IAllele;
import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.IGene;
import seremis.soulcraft.soul.Chromosome;
import seremis.soulcraft.soul.allele.AlleleBoolean;

public class GeneInvulnerable implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleBoolean.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleBoolean(true, false), new AlleleBoolean(false, false));
		}
		return null;
	}
}
