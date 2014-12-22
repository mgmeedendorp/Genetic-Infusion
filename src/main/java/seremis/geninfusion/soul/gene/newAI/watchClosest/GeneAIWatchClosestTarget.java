package seremis.geninfusion.soul.gene.newAI.watchClosest;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleString;

public class GeneAIWatchClosestTarget implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }
}
