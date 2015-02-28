package seremis.geninfusion.soul.gene.newAI;

import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleDouble;

public abstract class GeneMoveSpeed extends Gene {

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleDouble allele1 = (AlleleDouble) chromosome.getPrimary();
        AlleleDouble allele2 = (AlleleDouble) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            allele1.value = allele1.value * ((rand.nextFloat() * 2 * 0.1) + 0.9);
        } else {
            allele2.value = allele2.value * ((rand.nextFloat() * 2 * 0.1) + 0.9);
        }
        return chromosome;
    }
}
