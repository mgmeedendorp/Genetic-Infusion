package seremis.geninfusion.soul.gene.newAI.moveTowardsTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAIMoveTowardsTargetMaxDistance implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
