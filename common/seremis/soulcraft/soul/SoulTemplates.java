package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SoulTemplates {

    public static Soul getSoulPreset(EntityLiving entity) {
        if(entity instanceof EntityZombie)
            return getZombieSoul();
        return null;
    }
    
    public static Soul getZombieSoul() {
        Chromosome[] chromosomes = new Chromosome[EnumChromosome.values().length];
        
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
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.rotten_flesh)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.rotten_flesh)});
        
        chromosomes[EnumChromosome.ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloatArray(true, new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F});
        allele2 = new AlleleFloatArray(true, new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F});
        
        chromosomes[EnumChromosome.EQUIPMENT_DROP_CHANCES.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)});
        allele2 = new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)});
        
        chromosomes[EnumChromosome.RARE_ITEM_DROPS.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleString(true, "mob.zombie.say");
        allele2 = new AlleleString(true, "mob.zombie.say");
        
        chromosomes[EnumChromosome.LIVING_SOUND.ordinal()] = new Chromosome(allele1, allele2);

        allele1 = new AlleleString(true, "mob.zombie.hurt");
        allele2 = new AlleleString(true, "mob.zombie.hurt");
        
        chromosomes[EnumChromosome.HURT_SOUND.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleString(true, "mob.zombie.death");
        allele2 = new AlleleString(true, "mob.zombie.death");
        
        chromosomes[EnumChromosome.DEATH_SOUND.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleFloat(true, 1F);
        allele2 = new AlleleFloat(true, 1F);
        
        chromosomes[EnumChromosome.SOUND_VOLUME.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal());
        allele2 = new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal());
        
        chromosomes[EnumChromosome.CREATURE_ATTRIBUTE.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 1);
        allele2 = new AlleleInteger(true, 1);
        
        chromosomes[EnumChromosome.TELEPORT_TIME_IN_PORTAL.ordinal()] = new Chromosome(allele1, allele2);
        
        allele1 = new AlleleInteger(true, 900);
        allele2 = new AlleleInteger(true, 900);
        
        chromosomes[EnumChromosome.PORTAL_COOLDOWN.ordinal()] = new Chromosome(allele1, allele2);
        
        return new Soul(chromosomes);
    }
}
