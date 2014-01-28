package seremis.soulcraft.soul.entity;

import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public interface IEntitySoulCustom {
    
    double getPosX();
    double getPosY();
    double getPosZ();
    
    void setPosition(double x, double y, double z);
    
    float getRotationYaw();
    float getRotationPitch();

    void setRotationYaw(float yaw);
    void setRotationPitch(float pitch);
    
    World getWorld();
    
    float getBrightness();
    
    /*
     * Get the armor from slot 0-3
     */
    ItemStack getCurrentItemOrArmor(int slot);
    void setArmor(int slot, ItemStack stack);
    ItemStack getHeldItem();
    void setHeldItem(ItemStack stack);
    
    void setFire(int timeSec);
    void extinguish();
    
    void playSound(String sound, float volume, float pitch);
    
    float getEyeHeight();
    
    boolean getCanPickUpItems();
    void setCanPickUpItems(boolean canPickUp);
    
    void dropItems(ItemStack item);
    
    AttributeInstance getEntityAttribute(Attribute attribute);
    
    NBTTagCompound getEntityData();
    
    boolean getIsChild();
    
    int getRecentlyHit();
    
    PathNavigate getNavigator();
    EntityAITasks getTasks();
    EntityAITasks getTargetTasks();
}
