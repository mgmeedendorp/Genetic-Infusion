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
}
