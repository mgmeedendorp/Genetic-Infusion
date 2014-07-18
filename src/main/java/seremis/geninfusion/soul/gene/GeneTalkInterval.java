package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class GeneTalkInterval implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }

    @Override
    public IChromosome getStandardForEntity(EntityLiving entity) {
        return new Chromosome(new AlleleInteger(true, 80), new AlleleInteger(false, 80));
    }
}
