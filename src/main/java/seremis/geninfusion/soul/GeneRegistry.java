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

    @Override
    public void registerGene(String name, IGene gene) {
        if(!genes.containsKey(name)) {
            genes.put(name, gene);
            genesInv.put(gene, name);
            ids.put(gene, ids.size());
            idsInv.put(idsInv.size(), gene);
        }
    }

    @Override
    public IGene getGene(String name) {
        if(genes.containsKey(name)) {
            return genes.get(name);
        }
        return null;
    }

    @Override
    public String getGeneName(IGene gene) {
        return genesInv.get(gene);
    }

    @Override
    public int getGeneId(String name) {
        return getGene(name) != null ? getGeneId(getGene(name)) : null;
    }

    @Override
    public int getGeneId(IGene gene) {
        return ids.get(gene);
    }

    @Override
    public LinkedList<IGene> getGenes() {
        return new LinkedList<IGene>(genes.values());
    }

    @Override
    public IGene getGene(int id) {
        return idsInv.get(id);
    }

    @Override
    public ISoul getSoulFor(EntityLiving entity) {
        if(entity instanceof IEntitySoulCustom) {
            return ((IEntitySoulCustom) entity).getSoul();
        } else if(entity != null) {
            return SoulHelper.standardSoulRegistry.getSoulForEntity(entity);
        }
        return null;
    }

    @Override
    public IChromosome getChromosomeFor(EntityLiving entity, String name) {
        return getSoulFor(entity).getChromosomes()[getGeneId(name)];
    }

    @Override
    public IChromosome getChromosomeFor(IEntitySoulCustom entity, String name) {
        return getChromosomeFor((EntityLiving) entity, name);
    }

    @Override
    public IAllele getActiveFor(EntityLiving entity, String name) {
        return getChromosomeFor(entity, name) != null ? getChromosomeFor(entity, name).getActive() : null;
    }

    @Override
    public IAllele getActiveFor(IEntitySoulCustom entity, String name) {
        return getChromosomeFor(entity, name) != null ? getChromosomeFor(entity, name).getActive() : null;
    }
}
