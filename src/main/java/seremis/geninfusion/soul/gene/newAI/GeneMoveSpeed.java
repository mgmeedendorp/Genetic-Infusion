package seremis.geninfusion.soul.gene.newAI;

import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.allele.AlleleDoubleArray;

public abstract class GeneMoveSpeed extends Gene {

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        if(possibleAlleles().equals(AlleleDouble.class)) {
            AlleleDouble allele1 = (AlleleDouble) chromosome.getPrimary();
            AlleleDouble allele2 = (AlleleDouble) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = allele1.value * ((rand.nextFloat() * 2 * 0.1) + 0.9);
            } else {
                allele2.value = allele2.value * ((rand.nextFloat() * 2 * 0.1) + 0.9);
            }
        } else if(possibleAlleles().equals(AlleleDoubleArray.class)) {
            AlleleDoubleArray allele1 = (AlleleDoubleArray) chromosome.getPrimary();
            AlleleDoubleArray allele2 = (AlleleDoubleArray) chromosome.getSecondary();

            for(int i = 0; i < allele1.value.length; i++) {
                if(rand.nextBoolean()) {
                    allele1.value[i] = allele1.value[i] * ((rand.nextFloat() * 2 * 0.1) + 0.9);
                } else {
                    allele2.value[i] = allele2.value[i] * ((rand.nextFloat() * 2 * 0.1) + 0.9);
                }
            }
        }
        return chromosome;
    }
}
