package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;

import java.util.LinkedList;

/**
 * @author Seremis
 */
public interface IGeneRegistry {

    public void registerGene(String name, IGene gene);

    public IGene getGene(String name);

    public String getGeneName(IGene gene);

    public int getGeneId(String name);

    public int getGeneId(IGene gene);

    public LinkedList<IGene> getGenes();

    public IGene getGene(int id);

    public ISoul getSoulFor(EntityLiving entity);

    public IChromosome getChromosomeFor(EntityLiving entity, String name);

    public IChromosome getChromosomeFor(IEntitySoulCustom entity, String name);

    public IAllele getActiveFor(EntityLiving entity, String name);

    public IAllele getActiveFor(IEntitySoulCustom entity, String name);
}
