package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * @author Seremis
 */
public class GeneIsCreature extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        return chromosome;
    }
}
