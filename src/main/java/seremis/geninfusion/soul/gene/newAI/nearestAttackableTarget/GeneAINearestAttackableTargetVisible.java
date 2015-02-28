package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBooleanArray;

public class GeneAINearestAttackableTargetVisible extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBooleanArray.class;
    }
}
