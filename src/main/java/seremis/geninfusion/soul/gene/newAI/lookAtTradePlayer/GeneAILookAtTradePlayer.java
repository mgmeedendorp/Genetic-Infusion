package seremis.geninfusion.soul.gene.newAI.lookAtTradePlayer;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAILookAtTradePlayer extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
