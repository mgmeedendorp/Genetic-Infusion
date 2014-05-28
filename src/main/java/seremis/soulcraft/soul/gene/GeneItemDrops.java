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
