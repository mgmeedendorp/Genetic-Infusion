package seremis.geninfusion.soul.gene.animation;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.util.animation.AnimationPart;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleAnimationPartArray;

public class GeneAnim extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleAnimationPartArray.class;
    }

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleAnimationPartArray allele1 = (AlleleAnimationPartArray) chromosome.getPrimary();
        AlleleAnimationPartArray allele2 = (AlleleAnimationPartArray) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            AnimationPart[] parts = allele1.value;
            for(AnimationPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        } else {
            AnimationPart[] parts = allele2.value;
            for(AnimationPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        }

        return chromosome;
    }
}
