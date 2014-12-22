package seremis.geninfusion.soul.gene.newAI.beg;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAIBegRange implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
