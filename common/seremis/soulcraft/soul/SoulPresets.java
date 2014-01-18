package seremis.soulcraft.soul;

import seremis.soulcraft.core.lib.AlleleNames;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;

public class SoulPresets {

    public static Soul getStandardSoulFromEntity(EntityLivingBase entity) {
        if(entity instanceof EntityZombie)
            return getZombieSoul();
        return null;
    }
    
    public static Soul getZombieSoul() {
        Chromosome[] chromosomes = new Chromosome[1];
        
        IAllele allele1 = new Allele(true, AlleleNames.BURNS_IN_DAYLIGHT);
        IAllele allele2 = new Allele(true, AlleleNames.BURNS_IN_DAYLIGHT);
        
        chromosomes[0] = new Chromosome(allele1, allele2);
        
        return new Soul(chromosomes);
    }
}
