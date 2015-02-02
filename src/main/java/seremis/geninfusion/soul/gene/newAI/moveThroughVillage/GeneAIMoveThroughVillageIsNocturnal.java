package seremis.geninfusion.soul.gene.newAI.moveThroughVillage;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMoveThroughVillageIsNocturnal extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
