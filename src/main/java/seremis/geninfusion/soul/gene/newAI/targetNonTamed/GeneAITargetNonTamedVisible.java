package seremis.geninfusion.soul.gene.newAI.targetNonTamed;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBooleanArray;

public class GeneAITargetNonTamedVisible extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBooleanArray.class;
    }
}
