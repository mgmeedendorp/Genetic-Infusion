package seremis.geninfusion.soul.gene.newAI.defendVillage;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIDefendVillage extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
