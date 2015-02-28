package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleStringArray;

public class GeneAINearestAttackableTargetEntitySelector extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleStringArray.class;
    }
}
