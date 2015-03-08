package seremis.geninfusion.soul.gene.model;

import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;
import seremis.geninfusion.soul.entity.animation.AnimationCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneModel extends Gene {

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

    @Override
    public IChromosome inherit(IChromosome chromosome1, IChromosome chromosome2) {
        AlleleModelPartArray allele1 = (AlleleModelPartArray) chromosome1.getPrimary();
        AlleleModelPartArray allele2 = (AlleleModelPartArray) chromosome2.getPrimary();
        AlleleModelPartArray allele3 = (AlleleModelPartArray) chromosome1.getSecondary();
        AlleleModelPartArray allele4 = (AlleleModelPartArray) chromosome2.getSecondary();

        ModelPart[] head1 = AnimationCache.getModelHead(allele1.value);
        ModelPart[] head2 = AnimationCache.getModelHead(allele2.value);
        ModelPart[] head3 = AnimationCache.getModelHead(allele3.value);
        ModelPart[] head4 = AnimationCache.getModelHead(allele4.value);
        ModelPart[] arms1 = AnimationCache.getModelArms(allele1.value);
        ModelPart[] arms2 = AnimationCache.getModelArms(allele2.value);
        ModelPart[] arms3 = AnimationCache.getModelArms(allele3.value);
        ModelPart[] arms4 = AnimationCache.getModelArms(allele4.value);
        ModelPart[] legs1 = AnimationCache.getModelLegs(allele1.value);
        ModelPart[] legs2 = AnimationCache.getModelLegs(allele2.value);
        ModelPart[] legs3 = AnimationCache.getModelLegs(allele3.value);
        ModelPart[] legs4 = AnimationCache.getModelLegs(allele4.value);
        ModelPart[] wings1 = AnimationCache.getModelWings(allele1.value);
        ModelPart[] wings2 = AnimationCache.getModelWings(allele2.value);
        ModelPart[] wings3 = AnimationCache.getModelWings(allele3.value);
        ModelPart[] wings4 = AnimationCache.getModelWings(allele4.value);
        ModelPart[] body1 = new ModelPart[] {AnimationCache.getModelBody(allele1.value)};
        ModelPart[] body2 = new ModelPart[] {AnimationCache.getModelBody(allele2.value)};
        ModelPart[] body3 = new ModelPart[] {AnimationCache.getModelBody(allele3.value)};
        ModelPart[] body4 = new ModelPart[] {AnimationCache.getModelBody(allele4.value)};

        System.out.println(Arrays.toString(arms1));

        ArrayList<ModelPart[]> list1 = new ArrayList<ModelPart[]>();
        ArrayList<ModelPart[]> list2 = new ArrayList<ModelPart[]>();

        list1.add(rand.nextBoolean() ? head1 : head2);
        list1.add(rand.nextBoolean() ? arms1 : arms2);
        list1.add(rand.nextBoolean() ? legs1 : legs2);
        list1.add(rand.nextBoolean() ? wings1 : wings2);
        list1.add(rand.nextBoolean() ? body1 : body2);

        list2.add(rand.nextBoolean() ? head3 : head4);
        list2.add(rand.nextBoolean() ? arms3 : arms4);
        list2.add(rand.nextBoolean() ? legs3 : legs4);
        list2.add(rand.nextBoolean() ? wings3 : wings4);
        list2.add(rand.nextBoolean() ? body3 : body4);

        ArrayList<ModelPart> result1 = new ArrayList<ModelPart>();
        ArrayList<ModelPart> result2 = new ArrayList<ModelPart>();

        for(ModelPart[] array : list1) {
            Collections.addAll(result1, array);
        }

        for(ModelPart[] array : list2) {
            Collections.addAll(result2, array);
        }

        IAllele resultAllele1 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.MODEL_PART_ARRAY, true, result1.toArray(new ModelPart[result1.size()]));
        IAllele resultAllele2 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.MODEL_PART_ARRAY, false, result2.toArray(new ModelPart[result2.size()]));

        return SoulHelper.instanceHelper.getIChromosomeInstance(resultAllele1, resultAllele2);
    }
}
