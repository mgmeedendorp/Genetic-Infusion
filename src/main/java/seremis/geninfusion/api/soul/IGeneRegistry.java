package seremis.geninfusion.api.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.util.ModelPart;

import java.util.List;

public interface IGeneRegistry {

    IGene registerGene(String name, IGene gene);
    
    IGene registerGene(String name, EnumAlleleType type);

    IMasterGene registerMasterGene(String name, IMasterGene gene);

    void registerCustomInheritance(String name);

    void registerCustomInheritance(IGene gene);

    boolean useNormalInheritance(IGene gene);

    boolean useNormalInheritance(String name);

    List<IGene> getCustomInheritanceGenes();

    IGene getGene(String name);

    String getGeneName(IGene gene);

    int getGeneId(String name);

    int getGeneId(IGene gene);

    List<IGene> getGenes();

    IGene getGene(int id);

    ISoul getSoulFor(EntityLiving entity);

    IChromosome getChromosomeFor(EntityLiving entity, String name);

    IChromosome getChromosomeFor(IEntitySoulCustom entity, String name);

    IAllele getActiveFor(EntityLiving entity, String name);

    IAllele getActiveFor(IEntitySoulCustom entity, String name);

    boolean getValueBoolean(IEntitySoulCustom entity, String name);

    int getValueInteger(IEntitySoulCustom entity, String name);

    float getValueFloat(IEntitySoulCustom entity, String name);

    double getValueDouble(IEntitySoulCustom entity, String name);

    String getValueString(IEntitySoulCustom entity, String name);

    ItemStack getValueItemStack(IEntitySoulCustom entity, String name);

    ModelPart getValueModelPart(IEntitySoulCustom entity, String name);

    Class getValueClass(IEntitySoulCustom entity, String name);

    boolean[] getValueBooleanArray(IEntitySoulCustom entity, String name);

    int[] getValueIntegerArray(IEntitySoulCustom entity, String name);

    float[] getValueFloatArray(IEntitySoulCustom entity, String name);

    double[] getValueDoubleArray(IEntitySoulCustom entity, String name);

    String[] getValueStringArray(IEntitySoulCustom entity, String name);

    ItemStack[] getValueItemStackArray(IEntitySoulCustom entity, String name);

    ModelPart[] getValueModelPartArray(IEntitySoulCustom entity, String name);

    Class[] getValueClassArray(IEntitySoulCustom entity, String name);

    List<String> getControlledGenes(String masterGeneName);
}
