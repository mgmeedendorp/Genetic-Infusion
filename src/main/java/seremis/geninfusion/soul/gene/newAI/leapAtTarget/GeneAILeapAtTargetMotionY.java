package seremis.geninfusion.soul.gene.newAI.leapAtTarget;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAILeapAtTargetMotionY extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
