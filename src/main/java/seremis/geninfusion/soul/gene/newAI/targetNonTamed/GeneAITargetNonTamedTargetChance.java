package seremis.geninfusion.soul.gene.newAI.targetNonTamed;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class GeneAITargetNonTamedTargetChance implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }
}
