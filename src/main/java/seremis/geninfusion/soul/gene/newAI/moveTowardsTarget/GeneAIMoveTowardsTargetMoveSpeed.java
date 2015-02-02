package seremis.geninfusion.soul.gene.newAI.moveTowardsTarget;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.gene.newAI.GeneMoveSpeed;

public class GeneAIMoveTowardsTargetMoveSpeed extends GeneMoveSpeed {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDouble.class;
    }
}
