package seremis.geninfusion.soul.gene.model;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;

public abstract class GeneModel extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleModelPartArray.class;
    }

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleModelPartArray allele1 = (AlleleModelPartArray) chromosome.getPrimary();
        AlleleModelPartArray allele2 = (AlleleModelPartArray) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            ModelPart[] parts = allele1.value;
            for(ModelPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        } else {
            ModelPart[] parts = allele2.value;
            for(ModelPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        }

        return chromosome;
    }
}
