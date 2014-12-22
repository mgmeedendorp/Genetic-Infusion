package seremis.geninfusion.soul.gene.newAI.watchClosest2;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleString;

public class GeneAIWatchClosest2Target implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }
}
