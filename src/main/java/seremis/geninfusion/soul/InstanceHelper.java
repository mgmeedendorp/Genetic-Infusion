package seremis.geninfusion.soul;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.*;

import java.lang.reflect.Constructor;

/**
 * @author Seremis
 */
public class InstanceHelper implements IInstanceHelper {

    @Override
    public ISoul getISoulInstance(IChromosome[] chromosomes) {
        return new Soul(chromosomes);
    }

    @Override
    public IChromosome getIChromosomeInstance(IAllele allele1, IAllele allele2) {
        return new Chromosome(allele1, allele2);
    }

    @Override
    public IAllele getIAlleleInstance(EnumAlleleType alleleType, Object... args) {
        try {
            Constructor<?> ctor = alleleType.clazz.getConstructor(NBTTagCompound.class);
            Object object = ctor.newInstance(new Object[] { args });

            return (IAllele) object;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
