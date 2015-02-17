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
            IGene gene = SoulHelper.geneRegistry.getGene(i);
            String name = SoulHelper.geneRegistry.getGeneName(gene);
            chromosomes[i] = getStandardSoulForEntity(entity).getChromosomeFromGene(name);
            if(chromosomes[i] == null) {
                throw new NullPointerException("There seems to be a Gene (" + name + ") without an associated Chromosome for Entity: " + entity + ".");
            } else if(!EnumAlleleType.getForClass(gene.possibleAlleles()).equals(EnumAlleleType.getForClass(chromosomes[i].getPrimary().getClass()))) {
                throw new ClassCastException("Someone associated a gene (" + name + ") with an Allele (" + chromosomes[i].getPrimary() + ") that isn't allowed for this gene. It should be: " + gene.possibleAlleles());
            } else if(!EnumAlleleType.getForClass(gene.possibleAlleles()).equals(EnumAlleleType.getForClass(chromosomes[i].getPrimary().getClass()))) {
                throw new ClassCastException("Someone associated a gene (" + name + ") with an Allele (" + chromosomes[i].getSecondary() + ") that isn't allowed for this gene. It should be: " + gene.possibleAlleles());
            }
        }
        return new Soul(chromosomes);
    }
}
