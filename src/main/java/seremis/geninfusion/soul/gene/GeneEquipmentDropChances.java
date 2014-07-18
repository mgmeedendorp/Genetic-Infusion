package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleFloatArray;

public class GeneEquipmentDropChances implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleFloatArray.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		float[] array = new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F};
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleFloatArray(true, array), new AlleleFloatArray(false, array));
		}
		return null;
	}
}
