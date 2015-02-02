package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class GeneCreatureAttribute extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        return chromosome;
    }
}
