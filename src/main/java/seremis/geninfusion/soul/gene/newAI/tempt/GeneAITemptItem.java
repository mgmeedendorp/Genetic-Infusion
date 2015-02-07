package seremis.geninfusion.soul.gene.newAI.tempt;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleItemStack;

public class GeneAITemptItem extends Gene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleItemStack.class;
    }
}
