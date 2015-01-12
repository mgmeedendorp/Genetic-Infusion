package seremis.geninfusion.soul.gene.newAI.arrowAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleFloat;

/**
 * The rangedAttackTime of the AIArrowAttack will be multiplied by this value. (if this is 1.5F, the entity will take
 * approx. 1.5 times as long between shooting two different shots.
 */
public class GeneAIArrowAttackRangedAttackTimeModifier implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
