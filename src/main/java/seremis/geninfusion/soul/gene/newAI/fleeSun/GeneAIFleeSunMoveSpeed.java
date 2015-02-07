package seremis.geninfusion.soul.gene.newAI.fleeSun;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.gene.newAI.GeneMoveSpeed;

public class GeneAIFleeSunMoveSpeed extends GeneMoveSpeed {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDouble.class;
    }
}
