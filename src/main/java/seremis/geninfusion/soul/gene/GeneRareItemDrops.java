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

public class GeneRareItemDrops implements IGene {

	@Override
	public Class<? extends IAllele> possibleAlleles() {
		return AlleleInventory.class;
	}

	@Override
	public IChromosome getStandardForEntity(EntityLiving entity) {
		if(entity instanceof EntityZombie) {
			ItemStack[] array = new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)};
			return new Chromosome(new AlleleInventory(true, array), new AlleleInventory(false, array));
		}
		return null;
	}
}
