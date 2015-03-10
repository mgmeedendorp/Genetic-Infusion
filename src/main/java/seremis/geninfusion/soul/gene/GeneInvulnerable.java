package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneInvulnerable extends Gene {

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        return chromosome;
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
