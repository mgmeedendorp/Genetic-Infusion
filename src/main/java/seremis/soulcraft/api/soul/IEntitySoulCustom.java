package seremis.soulcraft.api.soul;

import java.util.HashMap;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import seremis.soulcraft.soul.Soul;

public interface IEntitySoulCustom {

    Soul getSoul();
    
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
    
    /*
     * Use these methods to set and get a persistent variable. This variable will save and load with NBT.
     */
    
    void setPersistentVariable(String name, boolean variable);
    void setPersistentVariable(String name, byte variable);
    void setPersistentVariable(String name, int variable);
    void setPersistentVariable(String name, float variable);
    void setPersistentVariable(String name, double variable);
    void setPersistentVariable(String name, String variable);
    void setPersistentVariable(String name, ItemStack variable);
    
    boolean getPersistentBoolean(String name);
    byte getPersistentByte(String name);
    int getPersistentInteger(String name);
    float getPersistentFloat(String name);
    double getPersistentDouble(String name);
    String getPersistentString(String name);
    ItemStack getPersistentItemStack(String name);
    
    /*
     * Use these methods to get and set a non-persistent variable. This variable will not sustain over saves.
     */
    
    void setVariable(String name, boolean variable);
    void setVariable(String name, byte variable);
    void setVariable(String name, int variable);
    void setVariable(String name, float variable);
    void setVariable(String name, double variable);
    void setVariable(String name, String variable);
    void setVariable(String name, ItemStack variable);
    
    boolean getBoolean(String name);
    byte getByte(String name);
    int getInteger(String name);
    float getFloat(String name);
    double getDouble(String name);
    String getString(String name);
    ItemStack getItemStack(String name);
    
    /*
     * Forces a synchronization between the standard entity variables and the custom ones
     */
    void forceVariableSync();
    
    
    NBTTagCompound getEntityData();
    
    void playSound(String sound, float volume, float pitch);
    
    boolean attackEntityFrom(DamageSource source, float damage);

    void setFlag(int id, boolean value);
    
    void onDeathUpdate();
    
    void damageEntity(DamageSource source, float damage);
    
    void updateAITick();
}
