package seremis.geninfusion.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.*;
import seremis.geninfusion.api.soul.util.ModelPart;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class GeneRegistry implements IGeneRegistry {

    public LinkedHashMap<String, IGene> genes = new LinkedHashMap<String, IGene>();
    public LinkedHashMap<IGene, String> genesInv = new LinkedHashMap<IGene, String>();
    public LinkedHashMap<IGene, Integer> ids = new LinkedHashMap<IGene, Integer>();
    public LinkedHashMap<Integer, IGene> idsInv = new LinkedHashMap<Integer, IGene>();

    public LinkedHashMap<String, IMasterGene> masterGenes = new LinkedHashMap<String, IMasterGene>();

    public LinkedList<IGene> customInheritance = new LinkedList<IGene>();

    @Override
    public IGene registerGene(String name, IGene gene) {
        if(!genes.containsKey(name)) {
            genes.put(name, gene);
            genesInv.put(gene, name);
            ids.put(gene, ids.size());
            idsInv.put(idsInv.size(), gene);
        }
        return gene;
    }

    @Override
    public IGene registerGene(String name, EnumAlleleType type) {
        return registerGene(name, new Gene(type));
    }

    @Override
    public IMasterGene registerMasterGene(String name, IMasterGene gene) {
        registerGene(name, gene);
        masterGenes.put(name, gene);
        return gene;
    }

    @Override
    public void registerCustomInheritance(String name) {
        customInheritance.add(getGene(name));
    }

    @Override
    public void registerCustomInheritance(IGene gene) {
        customInheritance.add(gene);
    }

    @Override
    public boolean useNormalInheritance(IGene gene) {
        return !customInheritance.contains(gene);
    }

    @Override
    public boolean useNormalInheritance(String name) {
        return useNormalInheritance(getGene(name));
    }

    @Override
    public List<IGene> getCustomInheritanceGenes() {
        return customInheritance;
    }

    @Override
    public IGene getGene(String name) {
        return genes.get(name);
    }

    @Override
    public String getGeneName(IGene gene) {
        return genesInv.get(gene);
    }

    @Override
    public int getGeneId(String name) {
        return getGeneId(getGene(name));
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

    @Override
    public boolean getValueBoolean(IEntitySoulCustom entity, String name) {
        return ((Allele<Boolean>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public int getValueInteger(IEntitySoulCustom entity, String name) {
        return ((Allele<Integer>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public float getValueFloat(IEntitySoulCustom entity, String name) {
        return ((Allele<Float>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public double getValueDouble(IEntitySoulCustom entity, String name) {
        return ((Allele<Double>) getActiveFor(entity, name)).alleleData();
    }


    @Override
    public String getValueString(IEntitySoulCustom entity, String name) {
        return ((Allele<String>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public ItemStack getValueItemStack(IEntitySoulCustom entity, String name) {
        return ((Allele<ItemStack>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public ModelPart getValueModelPart(IEntitySoulCustom entity, String name) {
        return ((Allele<ModelPart>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public Class getValueClass(IEntitySoulCustom entity, String name) {
        return ((Allele<Class>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public boolean[] getValueBooleanArray(IEntitySoulCustom entity, String name) {
        return ((Allele<boolean[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public int[] getValueIntegerArray(IEntitySoulCustom entity, String name) {
        return ((Allele<int[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public float[] getValueFloatArray(IEntitySoulCustom entity, String name) {
        return ((Allele<float[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public double[] getValueDoubleArray(IEntitySoulCustom entity, String name) {
        return ((Allele<double[]>) getActiveFor(entity, name)).alleleData();
    }


    @Override
    public String[] getValueStringArray(IEntitySoulCustom entity, String name) {
        return ((Allele<String[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public ItemStack[] getValueItemStackArray(IEntitySoulCustom entity, String name) {
        return ((Allele<ItemStack[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public ModelPart[] getValueModelPartArray(IEntitySoulCustom entity, String name) {
        return ((Allele<ModelPart[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public Class[] getValueClassArray(IEntitySoulCustom entity, String name) {
        return ((Allele<Class[]>) getActiveFor(entity, name)).alleleData();
    }

    @Override
    public List<String> getControlledGenes(String masterGeneName) {
        return ((IMasterGene) getGene(masterGeneName)).getControlledGenes();
    }
}
