package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class GeneAINearestAttackableTargetTargetChance implements IGene {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }
}
