package seremis.geninfusion.soul.gene.newAI.attackOnCollide;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIAttackOnCollideLongMemory implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
