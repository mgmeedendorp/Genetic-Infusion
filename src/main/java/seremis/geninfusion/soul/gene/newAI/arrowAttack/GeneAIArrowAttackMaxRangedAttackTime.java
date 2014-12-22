package seremis.geninfusion.soul.gene.newAI.arrowAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class GeneAIArrowAttackMaxRangedAttackTime implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }
}
