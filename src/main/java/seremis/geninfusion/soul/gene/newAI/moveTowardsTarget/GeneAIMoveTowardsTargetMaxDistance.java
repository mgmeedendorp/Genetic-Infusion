package seremis.geninfusion.soul.gene.newAI.moveTowardsTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAIMoveTowardsTargetMaxDistance extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
