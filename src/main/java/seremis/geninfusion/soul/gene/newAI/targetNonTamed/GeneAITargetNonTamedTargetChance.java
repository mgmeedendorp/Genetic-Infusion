package seremis.geninfusion.soul.gene.newAI.targetNonTamed;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleIntArray;

public class GeneAITargetNonTamedTargetChance extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleIntArray.class;
    }
}
