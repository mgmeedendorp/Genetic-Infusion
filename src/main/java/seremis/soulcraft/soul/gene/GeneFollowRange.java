package seremis.soulcraft.soul.gene;

import net.minecraft.entity.EntityLiving;
import seremis.soulcraft.api.soul.IAllele;
import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.IGene;
import seremis.soulcraft.soul.Chromosome;
import seremis.soulcraft.soul.allele.AlleleFloat;

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
