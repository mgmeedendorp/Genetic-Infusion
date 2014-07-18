package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.soul.Soul;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class GeneRegistry {

	public static LinkedHashMap<String, IGene> genes = new LinkedHashMap<String, IGene>();
	public static LinkedHashMap<IGene, String> genesInv = new LinkedHashMap<IGene, String>();
	public static LinkedHashMap<IGene, Integer> ids = new LinkedHashMap<IGene, Integer>();
	
	public static void registerGene(String name, IGene gene) {
		if(!genes.containsKey(name)) {
			genes.put(name, gene);
			genesInv.put(gene, name);
			ids.put(gene, ids.size());
		}
	}
	
	public static IGene getGene(String name) {
		if(genes.containsKey(name))
			return genes.get(name);
		return null;
	}
	
	public static String getGeneName(IGene gene) {
		return genesInv.get(gene);
	}
	
	public static int getGeneId(String name) {
		return getGene(name) != null ? getGeneId(getGene(name)) : null;
	}
	
	public static int getGeneId(IGene gene) {
		return ids.get(gene);
	}
	
	public static LinkedList<IGene> getGenes() {
		return new LinkedList<IGene>(genes.values());
	}
	
	public static Soul getSoulFor(EntityLiving entity) {
		if(entity instanceof IEntitySoulCustom) {
            return ((IEntitySoulCustom)entity).getSoul();
        } else if(entity != null) {
			IChromosome[] chromosomes = new IChromosome[genes.size()];
			for(IGene gene : getGenes()) {
				chromosomes[getGeneId(gene)] = gene.getStandardForEntity(entity);
			}
			return new Soul(chromosomes);
        }
		return null;
	}
	
	public static IChromosome getChromosomeFor(EntityLiving entity, String name) {
		return entity instanceof IEntitySoulCustom ? getSoulFor(entity).getChromosomes()[getGeneId(name)] : getGene(name).getStandardForEntity(entity);
	}
	
	public static IChromosome getChromosomeFor(IEntitySoulCustom entity, String name) {
		return getChromosomeFor((EntityLiving) entity, name);
	}
	
	public static IAllele getActiveFor(EntityLiving entity, String name) {
		return getChromosomeFor(entity, name) != null ? getChromosomeFor(entity, name).getActive() : null;
	}
	
	public static IAllele getActiveFor(IEntitySoulCustom entity, String name) {
		return getActiveFor((EntityLiving) entity, name);
	}
}
