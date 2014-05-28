package seremis.soulcraft.soul.gene;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import seremis.soulcraft.api.soul.IAllele;
import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.IGene;
import seremis.soulcraft.soul.Chromosome;
import seremis.soulcraft.soul.allele.AlleleBoolean;

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
