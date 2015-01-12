package seremis.geninfusion.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.*;
import seremis.geninfusion.soul.allele.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class GeneRegistry implements IGeneRegistry {

    public LinkedHashMap<String, IGene> genes = new LinkedHashMap<String, IGene>();
    public LinkedHashMap<IGene, String> genesInv = new LinkedHashMap<IGene, String>();
    public LinkedHashMap<IGene, Integer> ids = new LinkedHashMap<IGene, Integer>();
    public LinkedHashMap<Integer, IGene> idsInv = new LinkedHashMap<Integer, IGene>();
    public LinkedHashMap<String, IMasterGene> masterGenes = new LinkedHashMap<String, IMasterGene>();

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
    public void registerMasterGene(String name, IMasterGene gene) {
        registerGene(name, gene);
        masterGenes.put(name, gene);
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

    @Override
    public boolean getValueBoolean(IEntitySoulCustom entity, String name) {
        return ((AlleleBoolean) getActiveFor(entity, name)).value;
    }

    @Override
    public int getValueInteger(IEntitySoulCustom entity, String name) {
        return ((AlleleInteger) getActiveFor(entity, name)).value;
    }

    @Override
    public float getValueFloat(IEntitySoulCustom entity, String name) {
        return ((AlleleFloat) getActiveFor(entity, name)).value;
    }

    @Override
    public double getValueDouble(IEntitySoulCustom entity, String name) {
        return ((AlleleDouble) getActiveFor(entity, name)).value;
    }


    @Override
    public String getValueString(IEntitySoulCustom entity, String name) {
        return ((AlleleString) getActiveFor(entity, name)).value;
    }

    @Override
    public ItemStack getValueItemStack(IEntitySoulCustom entity, String name) {
        return ((AlleleItemStack) getActiveFor(entity, name)).stack;
    }

    @Override
    public boolean[] getValueBooleanArray(IEntitySoulCustom entity, String name) {
        return ((AlleleBooleanArray) getActiveFor(entity, name)).value;
    }

    @Override
    public int[] getValueIntegerArray(IEntitySoulCustom entity, String name) {
        return ((AlleleIntArray) getActiveFor(entity, name)).value;
    }

    @Override
    public float[] getValueFloatArray(IEntitySoulCustom entity, String name) {
        return ((AlleleFloatArray) getActiveFor(entity, name)).value;
    }

    @Override
    public double[] getValueDoubleArray(IEntitySoulCustom entity, String name) {
        return ((AlleleDoubleArray) getActiveFor(entity, name)).value;
    }


    @Override
    public String[] getValueStringArray(IEntitySoulCustom entity, String name) {
        return ((AlleleStringArray) getActiveFor(entity, name)).value;
    }

    @Override
    public ItemStack[] getValueItemStackArray(IEntitySoulCustom entity, String name) {
        return ((AlleleInventory) getActiveFor(entity, name)).inventory.getItemStacks();
    }

    @Override
    public List<IGene> getControlledGenes(String masterGeneName) {
        return ((IMasterGene) getGene(masterGeneName)).getControlledGenes();
    }
}
