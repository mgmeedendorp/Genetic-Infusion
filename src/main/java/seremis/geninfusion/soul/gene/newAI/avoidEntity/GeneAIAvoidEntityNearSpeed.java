package seremis.geninfusion.soul.gene.newAI.avoidEntity;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.allele.AlleleDoubleArray;
import seremis.geninfusion.soul.gene.newAI.GeneMoveSpeed;

public class GeneAIAvoidEntityNearSpeed extends GeneMoveSpeed {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDoubleArray.class;
    }
}
