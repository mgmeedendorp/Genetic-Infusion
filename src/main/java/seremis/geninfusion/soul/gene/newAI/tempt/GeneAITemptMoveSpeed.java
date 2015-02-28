package seremis.geninfusion.soul.gene.newAI.tempt;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleDoubleArray;
import seremis.geninfusion.soul.gene.newAI.GeneMoveSpeed;

public class GeneAITemptMoveSpeed extends GeneMoveSpeed {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDoubleArray.class;
    }
}
