package seremis.geninfusion.soul.gene.newAI.watchClosest;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleString;
import seremis.geninfusion.soul.gene.newAI.GeneTarget;

public class GeneAIWatchClosestTarget extends GeneTarget {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }
}
