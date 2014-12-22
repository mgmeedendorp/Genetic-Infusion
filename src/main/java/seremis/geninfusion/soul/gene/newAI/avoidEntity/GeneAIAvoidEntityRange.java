package seremis.geninfusion.soul.gene.newAI.avoidEntity;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAIAvoidEntityRange implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
