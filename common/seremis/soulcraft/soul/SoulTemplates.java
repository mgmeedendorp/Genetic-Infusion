package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;

public class SoulTemplates {

    public static Soul getSoulPreset(EntityLiving entity) {
        if(entity instanceof EntityZombie)
            return getZombieSoul();
        return null;
    }
    
    public static Soul getZombieSoul() {
        Chromosome[] chromosomes = new Chromosome[EnumChromosome.values().length];
        
        System.out.println(chromosomes.length);
        
        IAllele allele1;
        IAllele allele2;
        
        allele1 = new AlleleFloat(true, 10);
        allele2 = new AlleleFloat(true, 10);
        
        chromosomes[EnumChromosome.MAX_HEALTH.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(true, false);
        allele2 = new AlleleBoolean(true, false);
        
        chromosomes[EnumChromosome.INVULNERABLE.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloat(true, 3);
        allele2 = new AlleleFloat(true, 3);
        
        chromosomes[EnumChromosome.ATTACK_DAMAGE.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloat(true, 0.23F);
        allele2 = new AlleleFloat(true, 0.23F);
        
        chromosomes[EnumChromosome.MOVEMENT_SPEED.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(false, true);
        allele2 = new AlleleBoolean(false, true);
        
        chromosomes[EnumChromosome.BURNS_IN_DAYLIGHT.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(true, true);
        allele2 = new AlleleBoolean(true, true);
        
        chromosomes[EnumChromosome.DROWNS_IN_WATER.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(true, true);
        allele2 = new AlleleBoolean(true, true);
        
        chromosomes[EnumChromosome.DROWNS_IN_AIR.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleBoolean(true, false);
        allele2 = new AlleleBoolean(true, false);
        
        chromosomes[EnumChromosome.IMMUNE_TO_FIRE.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 10);
        allele2 = new AlleleInteger(true, 10);
        
        chromosomes[EnumChromosome.MAX_HURT_TIME.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 20);
        allele2 = new AlleleInteger(true, 20);
        
        chromosomes[EnumChromosome.MAX_HURT_RESISTANT_TIME.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.rottenFlesh)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.rottenFlesh)});
        
        chromosomes[EnumChromosome.ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloatArray(true, new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F});
        allele2 = new AlleleFloatArray(true, new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F});
        
        chromosomes[EnumChromosome.EQUIPMENT_DROP_CHANCES.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.ingotIron), new ItemStack(Item.carrot), new ItemStack(Item.potato)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Item.ingotIron), new ItemStack(Item.carrot), new ItemStack(Item.potato)});
        
        chromosomes[EnumChromosome.RARE_ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 3);
        allele2 = new AlleleInteger(true, 3);
        
        chromosomes[EnumChromosome.RARE_ITEM_DROP_CHANCE.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleString(true, "mob.zombie.say");
        allele2 = new AlleleString(true, "mob.zombie.say");
        
        chromosomes[EnumChromosome.LIVING_SOUND.ordinal()] = new Chromosome(allele1, allele2);

        allele1 = new AlleleString(true, "mob.zombie.hurt");
        allele2 = new AlleleString(true, "mob.zombie.hurt");
        
        chromosomes[EnumChromosome.HURT_SOUND.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleString(true, "mob.zombie.death");
        allele2 = new AlleleString(true, "mob.zombie.death");
        
        chromosomes[EnumChromosome.DEATH_SOUND.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloat(true, 1);
        allele2 = new AlleleFloat(true, 1);
        
        chromosomes[EnumChromosome.SOUND_VOLUME.ordinal()] = new Chromosome(allele1, allele2);
        
        return new Soul(chromosomes);
    }
}
