package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import seremis.soulcraft.soul.allele.AlleleBoolean;

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
        
        allele1 = new AlleleBoolean(true, true);
        allele2 = new AlleleBoolean(true, true);
        
        chromosomes[EnumChromosome.BURNS_IN_DAYLIGHT.ordinal()] = new Chromosome(allele1, allele2);
        
        return new Soul(chromosomes);
    }
}
