package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.soul.allele.*;

import java.util.Random;

public abstract class Gene implements IGene {

    protected Random rand = new Random();

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        if(possibleAlleles() == AlleleBoolean.class) {
            AlleleBoolean allele1 = (AlleleBoolean) chromosome.getPrimary();
            AlleleBoolean allele2 = (AlleleBoolean) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = !allele1.value;
            } else {
                allele2.value = !allele2.value;
            }
        } else if(possibleAlleles() == AlleleInteger.class) {
            AlleleInteger allele1 = (AlleleInteger) chromosome.getPrimary();
            AlleleInteger allele2 = (AlleleInteger) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = (int) (allele1.value * (rand.nextFloat() * 2));
            } else {
                allele2.value = (int) (allele2.value * (rand.nextFloat() * 2));
            }
        } else if(possibleAlleles() == AlleleFloat.class) {
            AlleleFloat allele1 = (AlleleFloat) chromosome.getPrimary();
            AlleleFloat allele2 = (AlleleFloat) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = allele1.value * (rand.nextFloat() * 2);
            } else {
                allele2.value = allele2.value * (rand.nextFloat() * 2);
            }
        } else if(possibleAlleles() == AlleleDouble.class) {
            AlleleDouble allele1 = (AlleleDouble) chromosome.getPrimary();
            AlleleDouble allele2 = (AlleleDouble) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = allele1.value * (rand.nextFloat() * 2);
            } else {
                allele2.value = allele2.value * (rand.nextFloat() * 2);
            }
        } else if(possibleAlleles() == AlleleBooleanArray.class) {
            AlleleBooleanArray allele1 = (AlleleBooleanArray) chromosome.getPrimary();
            AlleleBooleanArray allele2 = (AlleleBooleanArray) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                int index = rand.nextInt(allele1.value.length);
                allele1.value[index] = !allele1.value[index];
            } else {
                int index = rand.nextInt(allele2.value.length);
                allele2.value[index] = !allele2.value[index];
            }
        } else if(possibleAlleles() == AlleleIntArray.class) {
            AlleleIntArray allele1 = (AlleleIntArray) chromosome.getPrimary();
            AlleleIntArray allele2 = (AlleleIntArray) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                int index = rand.nextInt(allele1.value.length);
                allele1.value[index] = (int) (allele1.value[index] * (rand.nextFloat() * 2));
            } else {
                int index = rand.nextInt(allele2.value.length);
                allele2.value[index] = (int) (allele2.value[index] * (rand.nextFloat() * 2));
            }
        } else if(possibleAlleles() == AlleleFloatArray.class) {
            AlleleFloatArray allele1 = (AlleleFloatArray) chromosome.getPrimary();
            AlleleFloatArray allele2 = (AlleleFloatArray) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                int index = rand.nextInt(allele1.value.length);
                allele1.value[index] = allele1.value[index] * (rand.nextFloat() * 2);
            } else {
                int index = rand.nextInt(allele2.value.length);
                allele2.value[index] = allele2.value[index] * (rand.nextFloat() * 2);
            }
        } else if(possibleAlleles() == AlleleDoubleArray.class) {
            AlleleDoubleArray allele1 = (AlleleDoubleArray) chromosome.getPrimary();
            AlleleDoubleArray allele2 = (AlleleDoubleArray) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                int index = rand.nextInt(allele1.value.length);
                allele1.value[index] = allele1.value[index] * (rand.nextFloat() * 2);
            } else {
                int index = rand.nextInt(allele2.value.length);
                allele2.value[index] = allele2.value[index] * (rand.nextFloat() * 2);
            }
        }

        return chromosome;
    }

    @Override
    public IChromosome inherit(IChromosome chromosome1, IChromosome chromosome2) {
        IAllele allele1 = rand.nextBoolean() ? chromosome1.getPrimary() : chromosome1.getSecondary();
        IAllele allele2 = rand.nextBoolean() ? chromosome2.getPrimary() : chromosome2.getSecondary();

        return SoulHelper.instanceHelper.getIChromosomeInstance(allele1, allele2);
    }

    @Override
    public IChromosome advancedInherit(IChromosome[] parent1, IChromosome[] parent2, IChromosome[] offspring) {
        int geneId = SoulHelper.geneRegistry.getGeneId(this);

        IAllele allele1 = rand.nextBoolean() ? parent1[geneId].getPrimary() : parent1[geneId].getSecondary();
        IAllele allele2 = rand.nextBoolean() ? parent2[geneId].getPrimary() : parent2[geneId].getSecondary();

        return SoulHelper.instanceHelper.getIChromosomeInstance(allele1, allele2);
    }
}
