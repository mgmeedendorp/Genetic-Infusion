package seremis.soulcraft.soul.entity;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import seremis.soulcraft.soul.Soul;
import seremis.soulcraft.soul.SoulHandler;

public class EntitySoulCustom extends EntityLiving implements IEntitySoulCustom {
    
    private Soul soul;
    
    public EntitySoulCustom(World world) {
        super(world);
    }
    
    public EntitySoulCustom(World world, Soul soul, double x, double y, double z) {
        super(world);
        this.soul = soul;
        NBTTagCompound compound = getEntityData();
        soul.writeToNBT(compound);
        setPosition(x, y, z);
        setSize(1, 1);
        SoulHandler.entityInit(this);
    }

    //Modularity stuff//    
    @Override
    public double getPosX() {
        return posX;
    }
    
    @Override
    public double getPosY() {
        return posY;
    }
    
    @Override
    public double getPosZ() {
        return posZ;
    }
    
    @Override
    public float getRotationYaw() {
        return rotationYaw;
    }
    
    @Override
    public void setRotationYaw(float yaw) {
        rotationYaw = yaw;
    }
    
    @Override
    public float getRotationPitch() {
        return rotationPitch;
    }
    
    @Override
    public void setRotationPitch(float pitch) {
        rotationPitch = pitch;
    }
    
    @Override
    public World getWorld() {
        return worldObj;
    }
    
    @Override
    public float getBrightness() {
        return getBrightness(1.0F);
    }
    
    @Override
    public ItemStack getCurrentItemOrArmor(int slot) {
        return super.getCurrentItemOrArmor(slot);
    }
    
    @Override
    public void setArmor(int slot, ItemStack stack) {
        this.setCurrentItemOrArmor(slot+1, stack);
    }
    
    @Override
    public ItemStack getHeldItem() {
        return getCurrentItemOrArmor(0);
    }
    
    @Override
    public void setHeldItem(ItemStack stack) {
        setCurrentItemOrArmor(0, stack);
    }
    
    @Override
    public boolean getCanPickUpItems() {
        return canPickUpLoot();
    }
    
    @Override
    public void setCanPickUpItems(boolean canPickUp) {
        setCanPickUpLoot(canPickUp);
    }
    
    @Override
    public void dropItems(ItemStack stack) {
        this.entityDropItem(stack, getEyeHeight());
    }

    @Override
    public boolean getIsChild() {
        return isChild();
    }

    @Override
    public int getRecentlyHit() {
        return recentlyHit;
    }
    
    @Override
    public String getLivingSound() {
        return "sound.living";
    }
    
    @Override
    public String getHurtSound() {
        return "sound.hurt";
    }
    
    @Override
    public String getDeathSound() {
        return "sound.death";
    }
    
    @Override
    public EntityAITasks getTasks() {
        return tasks;
    }

    @Override
    public EntityAITasks getTargetTasks() {
        return targetTasks;
    }
    
    @Override
    public int getFire() {
        int fire = 0;
        try {
            Field onFire = Entity.class.getDeclaredField("fire");
            onFire.setAccessible(true);
            fire = onFire.getInt(this);
            onFire.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return fire;
    }
   
    //Entity stuff//    
    @Override
    public boolean interact(EntityPlayer player) {
        SoulHandler.entityRightClicked(this, player);
        return true;
    }
    
    @Override
    public void onUpdate() {
        SoulHandler.entityUpdate(this);
    }
    
    @Override
    public void onDeath(DamageSource source) {
        if(ForgeHooks.onLivingDeath(this, source)) return;
        
        if(source.getEntity() != null) {
            source.getEntity().onKillEntity(this);
        }
        dead = true;
        
        SoulHandler.entityDeath(this, source);
        this.worldObj.setEntityState(this, (byte)3);
    }
    
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        SoulHandler.onKillEntity(this, entity);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
       return SoulHandler.attackEntityFrom(this, source, damage);
    }
    
    @Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData data) {
        return SoulHandler.spawnEntityFromEgg(this, data);
    }
    
    @Override
    public void playSound(String name, float volume, float pitch) {
        SoulHandler.playSoundAtEntity(this, name, volume, pitch);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound data = getEntityData();
        soul = new Soul(data);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound data = getEntityData();
        soul.writeToNBT(data);
    }
}
