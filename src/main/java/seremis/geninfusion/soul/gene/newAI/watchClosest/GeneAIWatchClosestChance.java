package seremis.geninfusion.soul.gene.newAI.watchClosest;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleFloatArray;

public class GeneAIWatchClosestChance extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloatArray.class;
    }
}
