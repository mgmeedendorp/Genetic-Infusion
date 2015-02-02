package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleString;

public class GeneAINearestAttackableTargetEntitySelector extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }
}
