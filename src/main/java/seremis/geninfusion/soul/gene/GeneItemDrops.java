package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleInventory;

public class GeneItemDrops implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleInventory.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			return new Chromosome(new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.rotten_flesh)}), new AlleleInventory(false, new ItemStack[] {new ItemStack(Items.rotten_flesh)}));
		}
		return null;
	}
}
