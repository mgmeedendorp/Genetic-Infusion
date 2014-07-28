package seremis.geninfusion.soul;

import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class GeneRegistry implements IGeneRegistry {

	public LinkedHashMap<String, IGene> genes = new LinkedHashMap<String, IGene>();
	public LinkedHashMap<IGene, String> genesInv = new LinkedHashMap<IGene, String>();
	public LinkedHashMap<IGene, Integer> ids = new LinkedHashMap<IGene, Integer>();
    public LinkedHashMap<Integer, IGene> idsInv = new LinkedHashMap<Integer, IGene>();
	
	public void registerGene(String name, IGene gene) {
		if(!genes.containsKey(name)) {
			genes.put(name, gene);
			genesInv.put(gene, name);
			ids.put(gene, ids.size());
            idsInv.put(idsInv.size(), gene);
		}
	}
	
	public IGene getGene(String name) {
		if(genes.containsKey(name))
			return genes.get(name);
		return null;
	}
	
	public String getGeneName(IGene gene) {
		return genesInv.get(gene);
	}
	
	public int getGeneId(String name) {
		return getGene(name) != null ? getGeneId(getGene(name)) : null;
	}
	
	public int getGeneId(IGene gene) {
		return ids.get(gene);
	}
	
	public LinkedList<IGene> getGenes() {
		return new LinkedList<IGene>(genes.values());
	}

    public IGene getGene(int id) {
        return idsInv.get(id);
    }
	
	public ISoul getSoulFor(EntityLiving entity) {
		if(entity instanceof IEntitySoulCustom) {
            return ((IEntitySoulCustom)entity).getSoul();
        } else if(entity != null) {
			return SoulHelper.standardSoulRegistry.getSoulForEntity(entity);
        }
		return null;
	}
	
	public IChromosome getChromosomeFor(EntityLiving entity, String name) {
		return getSoulFor(entity).getChromosomes()[getGeneId(name)];
	}
	
	public IChromosome getChromosomeFor(IEntitySoulCustom entity, String name) {
		return getChromosomeFor((EntityLiving) entity, name);
	}
	
	public IAllele getActiveFor(EntityLiving entity, String name) {
		return getChromosomeFor(entity, name) != null ? getChromosomeFor(entity, name).getActive() : null;
	}
	
	public IAllele getActiveFor(IEntitySoulCustom entity, String name) {
		return getActiveFor((EntityLiving) entity, name);
	}
}
