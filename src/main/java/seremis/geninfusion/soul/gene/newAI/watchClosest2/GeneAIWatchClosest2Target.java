package seremis.geninfusion.soul.gene.newAI.watchClosest2;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleClassArray;
import seremis.geninfusion.soul.gene.newAI.GeneTarget;

public class GeneAIWatchClosest2Target extends GeneTarget {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleClassArray.class;
    }
}
