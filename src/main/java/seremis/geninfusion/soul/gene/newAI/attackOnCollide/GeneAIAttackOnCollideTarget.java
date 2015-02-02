package seremis.geninfusion.soul.gene.newAI.attackOnCollide;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.gene.newAI.GeneTarget;
import seremis.geninfusion.soul.allele.AlleleString;

public class GeneAIAttackOnCollideTarget extends GeneTarget {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }
}
