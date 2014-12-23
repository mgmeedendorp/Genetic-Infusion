package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Seremis
 */
public interface IGeneRegistry {

    public void registerGene(String name, IGene gene);

    public void registerMasterGene(String name, IMasterGene gene);

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

    public boolean getValueBoolean(IEntitySoulCustom entity, String name);
    public int getValueInteger(IEntitySoulCustom entity, String name);
    public float getValueFloat(IEntitySoulCustom entity, String name);
    public double getValueDouble(IEntitySoulCustom entity, String name);
    public String getValueString(IEntitySoulCustom entity, String name);
    public ItemStack getValueItemStack(IEntitySoulCustom entity, String name);

    public boolean[] getValueBooleanArray(IEntitySoulCustom entity, String name);
    public int[] getValueIntegerArray(IEntitySoulCustom entity, String name);
    public float[] getValueFloatArray(IEntitySoulCustom entity, String name);
    public double[] getValueDoubleArray(IEntitySoulCustom entity, String name);
    public String[] getValueStringArray(IEntitySoulCustom entity, String name);
    public ItemStack[] getValueItemStackArray(IEntitySoulCustom entity, String name);

    public List<IGene> getControlledGenes(String masterGeneName);
}
