package seremis.soulcraft.soul.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import seremis.soulcraft.soul.Soul;

public interface IEntitySoulCustom {

    Soul getSoul();
    
    double getPosX();
    double getPosY();
    double getPosZ();
    
    void setPosition(double x, double y, double z);
    
    double getMotionX();
    double getMotionY();
    double getMotionZ();
    
    /**
     * SETS the motion on the entity
     * @param motionX
     * @param motionY
     * @param motionZ
     */
    void setMotion(double x, double y, double z);
    /**
     * ADDS to the current velocity (motionX/Y/Z) of the entity
     * @param motionX
     * @param motionY
     * @param motionZ
     */
    void addVelocity(double x, double y, double z);
    
    double getPrevPosX();
    double getPrevPosY();
    double getPrevPosZ();
    
    void setPrevPosX(double x);
    void setPrevPosY(double y);
    void setPrevPosZ(double z);
    
    float getRotationYaw();
    float getRotationPitch();

    void setRotationYaw(float yaw);
    void setRotationPitch(float pitch);
    
    float getPrevRotationYaw();
    float getPrevRotationPitch();
    
    void setPrevRotationYaw(float yaw);
    void setPrevRotationPitch(float pitch);
    
    void setPrevDistanceWalkedModified(float prevDistanceWalkedModified);
    void setDistanceWalkedModified(float distanceWalkedModified);
    
    float getPrevDistanceWalkedModified();
    float getDistanceWalkedModified();
    
    World getWorld();
    
    float getBrightness();
    
    boolean getIsInPortal();
    void setIsInPortal(boolean inPortal);
    
    int getPortalTimer();
    void setPortalTimer(int time);
    
    int getPortalCooldown();
    void setPortalCooldown(int cooldown);
    
    float getWidth();
    void setWidth(float width);
    
    float getHeight();
    void setHeight(float height);
    
    AxisAlignedBB getBoundingBox();
    void setBoundingBox(AxisAlignedBB boundingBox);
    
    float getCameraPitch();
    void setCameraPitch(float cameraPitch);
    
    float getPrevCameraPitch();
    void setPrevCameraPitch(float prevCameraPitch);
    
    /**
     * Get the armor from slot 0-3
     */
    ItemStack getCurrentItemOrArmor(int slot);
    void setArmor(int slot, ItemStack stack);
    ItemStack getHeldItem();
    void setHeldItem(ItemStack stack);
    
    /**
     * Good ol' Entity.class setFire();
     * @param timeSec
     */
    void setFire(int timeSec);
    /**
     * Watch out! Time in ticks, not in seconds like standard entity method!
     * @param timeTicks
     */
    void setFireNew(int timeTicks);
    /**
     * Get entity fire time in ticks
     * @return
     */
    int getFire();
    void extinguish();
    
    void playSound(String sound, float volume, float pitch);
    
    float getEyeHeight();
    
    boolean getCanPickUpItems();
    void setCanPickUpItems(boolean canPickUp);
    
    void dropItems(ItemStack item);
    
    AttributeInstance getEntityAttribute(Attribute attribute);
    
    NBTTagCompound getEntityData();
    
    boolean getIsChild();
    
    PathNavigate getNavigator();
    EntityAITasks getTasks();
    EntityAITasks getTargetTasks();
    
    int getAir();
    void setAir(int air);
    
    boolean isEntityAlive();
    boolean isInWater();
    boolean handleWaterMovement();
    boolean handleLavaMovement();
    
    boolean attackEntityFrom(DamageSource source, float damage);
    BaseAttributeMap getAttributeMap();
    
    int getAge();
    void setAge(int age);
    
    float getHealth();
    void setHealth(float health);
    float getPrevHealth();
    void setPrevHealth(float lastHealth);
    
    int getHurtTime();
    void setHurtTime(int time);
    
    boolean isPotionActive(Potion potion);
    
    float getLimbSwingAmount();
    void setLimbSwingAmount(float amount);
    
    int getHurtResistantTime();
    void setHurtResistantTime(int time);
    
    float getLastDamage();
    void setLastDamage(float damage);
    
    void damageEntity(DamageSource source, float damage);
    void onDeath(DamageSource source);
    
    EntityLivingBase getAITarget();
    void setRevengeTarget(EntityLivingBase target);
    EntityPlayer getRevengePlayer();
    void setRevengePlayer(EntityPlayer player);
    int getRecentlyHit();
    void setRecentlyHit(int time);
    void setBeenAttacked();
    void setAttackedAtYaw(float yaw);
    float getAttackedAtYaw();
    void setAttackTime(int timer);
    int getAttackTime();    
    
    float applyArmorCalculations(DamageSource source, float damage);
    float applyPotionDamageCalculations(DamageSource source, float damage);
    
    float getAbsorptionAmount();
    void setAbsorptionAmount(float amount);
    
    CombatTracker getCombatTracker();
    
    void knockBack(Entity entity, float unused, double x, double y);
    
    void onDeathUpdate();
    
    float getSoundPitch();
    
    void setCreatureAttribute(EnumCreatureAttribute attribute);
    EnumCreatureAttribute getCreatureAttribute();
    
    void collideWithNearbyEntities();

    void setFlag(int id, boolean value);
    
    void setIsSprinting(boolean sprint);
    boolean isSprinting();
    
    void setIsJumping(boolean jump);
    boolean getIsJumping();
    
    void setFallDistance(float fallDistance);
    float getFallDistance();
    
    void setDead();
    
    void moveEntity(double posX, double posY, double posZ);
    void moveEntityWithHeading(float strafe, float forward);
    void setMoveStrafing(float strafe);
    float getMoveStrafing();
    void setMoveForward(float forward);
    float getMoveForward();
    
    void setRidingEntity(Entity ridingEntity);
    Entity getRidingEntity();
    
    void travelToDimension(int dimId);
    
    double getYOffset();
    void setYOffset(float yOffset);
    
    boolean isEntityInsideOpaqueBlock();
    
    void updatePotionEffects();
    
    void setRenderYawOffset(float yawOffset);
    float getRenderYawOffset();
    void setPrevRenderYawOffset(float prevYawOffset);
    float getPrevRenderYawOffset();
    
    void setRotationYawHead(float yaw);
    float getRotationYawHead();
    void setPrevRotationYawHead(float yaw);
    float getPrevRotationYawHead();
    
    
}
