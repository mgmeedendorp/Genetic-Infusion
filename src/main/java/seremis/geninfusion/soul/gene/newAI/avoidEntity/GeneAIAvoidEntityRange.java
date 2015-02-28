package seremis.geninfusion.soul.gene.newAI.avoidEntity;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleFloatArray;

public class GeneAIAvoidEntityRange extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloatArray.class;
    }
}
