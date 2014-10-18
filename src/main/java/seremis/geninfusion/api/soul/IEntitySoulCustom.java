package seremis.geninfusion.api.soul;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import seremis.geninfusion.api.soul.util.Data;

import java.util.HashMap;
import java.util.Random;

public interface IEntitySoulCustom {

    ISoul getSoul();
    
    World getWorld();
    
    AxisAlignedBB getBoundingBox();
    
    int getEntityId();
    
    BaseAttributeMap getAttributeMap();
    
    CombatTracker getCombatTracker();
    
    HashMap<Integer, PotionEffect> getActivePotionsMap();
    
    DataWatcher getDataWatcher();
    
    EntityLookHelper getLookHelper();
    EntityMoveHelper getMoveHelper();
    EntityJumpHelper getJumpHelper();
    PathNavigate getNavigator();
    EntitySenses getEntitySenses();
    
    EntityAITasks getTasks();
    EntityAITasks getTargetTasks();
    
    NBTTagCompound getLeashedCompound();
    
    Random getRandom();
    
    /*
     * Use this method to set a variable as persistent. This variable will save and load with NBT.
     */
    void makePersistent(String name);
    
    /*
     * Use these methods to get and set a non-persistent variable. This variable will not sustain over saves.
     */
    
    void setBoolean(String name, boolean variable);
    void setByte(String name, byte variable);
    void setShort(String name, short variable);
    void setInteger(String name, int variable);
    void setFloat(String name, float variable);
    void setDouble(String name, double variable);
    void setString(String name, String variable);
    void setLong(String name, long variable);
    void setItemStack(String name, ItemStack variable);
    void setNBT(String name, NBTTagCompound variable);
    void setData(String name, Data variable);

    boolean getBoolean(String name);
    byte getByte(String name);
    short getShort(String name);
    int getInteger(String name);
    float getFloat(String name);
    double getDouble(String name);
    long getLong(String name);
    String getString(String name);
    ItemStack getItemStack(String name);
    NBTTagCompound getNBT(String name);
    Data getData(String name);
    
    /*
     * Forces a synchronization between the standard entity variables and the custom ones
     */
    void forceVariableSync();
    
    
    NBTTagCompound getEntityData();
    
    void playSound(String sound, float volume, float pitch);
    
    boolean attackEntityFrom(DamageSource source, float damage);

    void attackEntity(Entity entity, float distance);

    void setFlag(int id, boolean value);

    boolean getFlag(int id);
    
    void onDeathUpdate();
    
    void damageEntity(DamageSource source, float damage);
    
    void updateAITick();
}
