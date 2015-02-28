package seremis.geninfusion.soul.gene.newAI.arrowAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleFloat;

/**
 * The rangedAttackTime of the AIArrowAttack will be multiplied by this value. (if this is 1.5F, the entity will take
 * approx. 1.5 times as long between shooting two different shots.
 */
public class GeneAIArrowAttackRangedAttackTimeModifier extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }


    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleFloat allele1 = (AlleleFloat) chromosome.getPrimary();
        AlleleFloat allele2 = (AlleleFloat) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            allele1.value = (float) (allele1.value * ((rand.nextFloat() * 2 * 0.1) + 0.9));
        } else {
            allele2.value = (float) (allele2.value * ((rand.nextFloat() * 2 * 0.1) + 0.9));
        }
        return chromosome;
    }
}
