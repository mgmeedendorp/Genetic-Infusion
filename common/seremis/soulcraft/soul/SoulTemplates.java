package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleIntArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;

public class SoulTemplates {

    public static Soul getSoulPreset(EntityLiving entity) {
        if(entity instanceof EntityZombie)
            return getZombieSoul();
        return null;
    }
    
    public static Soul getZombieSoul() {
        Chromosome[] chromosomes = new Chromosome[2];
        
        IAllele allele1;
        IAllele allele2;
        
        allele1 = new AlleleBoolean(true, true);
        allele2 = new AlleleBoolean(true, true);
        
        chromosomes[EnumChromosome.IS_TEMPLATE_GENOME.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloat(true, 10);
        allele2 = new AlleleFloat(true, 10);
        
        chromosomes[EnumChromosome.MAX_HEALTH.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(false, true);
        allele2 = new AlleleBoolean(false, true);
        
        chromosomes[EnumChromosome.BURNS_IN_DAYLIGHT.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.rottenFlesh)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.rottenFlesh)});
        
        chromosomes[EnumChromosome.ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleIntArray(true, new int[] {});
        allele2 = new AlleleIntArray(true, new int[] {});
        
        chromosomes[EnumChromosome.EQUIPMENT_DROP_CHANCES.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.ingotIron), new ItemStack(Item.carrot), new ItemStack(Item.potato)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.ingotIron), new ItemStack(Item.carrot), new ItemStack(Item.potato)});
        
        chromosomes[EnumChromosome.RARE_ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 3);
        allele2 = new AlleleInteger(true, 3);
        
        chromosomes[EnumChromosome.RARE_ITEM_DROP_CHANCE.ordinal()] = new Chromosome(allele1, allele2);
        
        return new Soul(chromosomes);
    }
}
