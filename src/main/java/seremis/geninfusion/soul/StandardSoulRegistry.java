package seremis.geninfusion.soul;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.*;

import java.util.HashMap;

/**
 * @author Seremis
 */
public class StandardSoulRegistry implements IStandardSoulRegistry {

    public HashMap<IStandardSoul, Class<? extends EntityLiving>> entities = new HashMap<IStandardSoul, Class<? extends EntityLiving>>();
    public HashMap<Class<? extends EntityLiving>, IStandardSoul> standards = new HashMap<Class<? extends EntityLiving>, IStandardSoul>();

    public void register(IStandardSoul standard, Class<? extends EntityLiving> entity) {
        entities.put(standard, entity);
        standards.put(entity, standard);
    }

    public IStandardSoul getStandardSoulForEntity(EntityLiving entity) {
        return getStandardSoulForEntity(entity.getClass());
    }

    public IStandardSoul getStandardSoulForEntity(Class<? extends EntityLiving> entity) {
        return standards.get(entity);
    }

    public ISoul getSoulForEntity(EntityLiving entity) {
        IChromosome[] chromosomes = new IChromosome[SoulHelper.geneRegistry.getGenes().size()];
        for(int i = 0; i < chromosomes.length; i++) {
            String name = SoulHelper.geneRegistry.getGeneName(SoulHelper.geneRegistry.getGene(i));
            chromosomes[i] = getStandardSoulForEntity(entity).getChromosomeFromGene(name);
        }
        return new Soul(chromosomes);
    }
}
