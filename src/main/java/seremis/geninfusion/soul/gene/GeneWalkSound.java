package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleString;

public class GeneWalkSound implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleString.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleString(true, "mob.zombie.step"), new AlleleString(false, "mob.zombie.step"));
		}
		return null;
	}
}
