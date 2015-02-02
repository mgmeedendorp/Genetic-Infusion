package seremis.geninfusion.soul.gene.newAI.watchClosest2;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAIWatchClosest2Range extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
