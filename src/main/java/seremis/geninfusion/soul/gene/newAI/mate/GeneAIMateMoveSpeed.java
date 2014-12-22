package seremis.geninfusion.soul.gene.newAI.mate;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleDouble;

public class GeneAIMateMoveSpeed implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDouble.class;
    }
}
