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

    Random getRandom();

    /*
     * Use this method to set a variable as persistent. This variable will save and load with NBT.
     */
    void makePersistent(String name);
    
    /*
     * Use these methods to get and set a variable in the entity class. This can be used to set position, motion etc but also custom variables, like bar, boo and foo.
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

    void setBooleanArray(String name, boolean[] variable);

    void setByteArray(String name, byte[] variable);

    void setShortArray(String name, short[] variable);

    void setIntegerArray(String name, int[] variable);

    void setFloatArray(String name, float[] variable);

    void setDoubleArray(String name, double[] variable);

    void setLongArray(String name, long[] variable);

    void setStringArray(String name, String[] variable);

    void setItemStackArray(String name, ItemStack[] variable);

    void setNBTArray(String name, NBTTagCompound[] variable);

    void setDataArray(String name, Data[] variable);

    /**
     * Use this to set a variable that already exists in an entity class, this can't be used to set custom variables.
     * @param name
     * @param object
     */
    void setObject(String name, Object object);

    boolean[] getBooleanArray(String name);

    byte[] getByteArray(String name);

    short[] getShortArray(String name);

    int[] getIntegerArray(String name);

    float[] getFloatArray(String name);

    double[] getDoubleArray(String name);

    long[] getLongArray(String name);

    String[] getStringArray(String name);

    ItemStack[] getItemStackArray(String name);

    NBTTagCompound[] getNBTArray(String name);

    Data[] getDataArray(String name);

    /**
     * Use this to get a variable that already exists in an entity class, this can't be used to get custom variables.
     * @param name
     */
    Object getObject(String name);


    NBTTagCompound getEntityData();

    void playSound(String sound, float volume, float pitch);

    boolean attackEntityFrom(DamageSource source, float damage);

    void attackEntity(Entity entity, float distance);

    void setFlag(int id, boolean value);

    boolean getFlag(int id);

    void onDeathUpdate();

    void damageEntity(DamageSource source, float damage);

    void updateAITick();

    boolean canDespawn();

    boolean isMovementCeased();

    Entity findPlayerToAttack();

    void setBeenAttacked();

    String getDeathSound();
    String getHurtSound();

    float getSoundVolume();
    float getSoundPitch();

    float applyArmorCalculations(DamageSource source, float damage);
    float applyPotionDamageCalculations(DamageSource source, float damage);

    void damageArmor(float damage);
}
