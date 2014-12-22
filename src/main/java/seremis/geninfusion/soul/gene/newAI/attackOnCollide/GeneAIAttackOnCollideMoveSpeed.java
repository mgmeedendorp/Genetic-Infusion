package seremis.geninfusion.soul.gene.newAI.attackOnCollide;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleDouble;

public class GeneAIAttackOnCollideMoveSpeed implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDouble.class;
    }
}
