package seremis.soulcraft.soul.gene;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.soulcraft.api.soul.IAllele;
import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.IGene;
import seremis.soulcraft.soul.Chromosome;
import seremis.soulcraft.soul.allele.AlleleFloatArray;

public class GeneRareItemDropChances implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleFloatArray.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			float[] array = new float[] {0.33F, 0.33F, 0.33F};
			return new Chromosome(new AlleleFloatArray(true, array), new AlleleFloatArray(false, array));
		}
		return null;
	}
}
