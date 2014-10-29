package seremis.geninfusion.soul;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import seremis.geninfusion.api.soul.*;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.allele.AlleleBoolean;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;

import java.lang.reflect.Constructor;

/**
 * @author Seremis
 */
public class InstanceHelper implements IInstanceHelper {

    @Override
    public IEntitySoulCustom getSoulEntityInstance(World world, ISoul soul, double x, double y, double z) {
        IEntitySoulCustom entity = new EntitySoulCustom(world, soul, x, y, z);

        if(((AlleleBoolean)SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IS_CREATURE)).value) {
            entity = new EntitySoulCustomCreature(world, soul, x, y, z);
        }
        return entity;
    }

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
