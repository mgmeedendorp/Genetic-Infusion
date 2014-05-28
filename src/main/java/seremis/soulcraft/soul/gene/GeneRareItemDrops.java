package seremis.soulcraft.soul.gene;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.api.soul.IAllele;
import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.IGene;
import seremis.soulcraft.soul.Chromosome;
import seremis.soulcraft.soul.allele.AlleleInventory;

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
