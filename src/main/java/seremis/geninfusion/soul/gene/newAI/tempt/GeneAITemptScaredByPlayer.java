package seremis.geninfusion.soul.gene.newAI.tempt;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBoolean;
import seremis.geninfusion.soul.allele.AlleleBooleanArray;

public class GeneAITemptScaredByPlayer extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBooleanArray.class;
    }
}
