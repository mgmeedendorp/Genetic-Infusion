package seremis.geninfusion.soul.gene.newAI.tempt;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleItemStack;

public class GeneAITemptItem implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleItemStack.class;
    }
}
