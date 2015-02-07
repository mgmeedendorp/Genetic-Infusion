package seremis.geninfusion.soul.gene.newAI.controlledByPlayer;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.gene.newAI.GeneMoveSpeed;

public class GeneAIControlledByPlayerMaxSpeed extends GeneMoveSpeed {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
