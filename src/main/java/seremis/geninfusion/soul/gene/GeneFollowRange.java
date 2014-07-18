package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleFloat;

/**
 * @author Seremis
 */
public class GeneFollowRange implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }

    @Override
    public IChromosome getStandardForEntity(EntityLiving entity) {
        return new Chromosome(new AlleleFloat(true, 16), new AlleleFloat(false, 16));
    }
}
