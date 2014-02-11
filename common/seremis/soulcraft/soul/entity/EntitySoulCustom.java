package seremis.soulcraft.soul.entity;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.CombatTracker;
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
        this.soul.writeToNBT(compound);
        setPosition(x, y, z);
        setSize(1, 1);
        SoulHandler.entityInit(this);
    }
    
    public Soul getSoul() {
        return soul;
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
            Field onFire = ReflectionHelper.findField(Entity.class, new String[] {"fire", "field_70151_c"});
            onFire.setAccessible(true);
            fire = onFire.getInt(this);
            onFire.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return fire;
    }
    
    @Override
    public void setFire(int time) {
        super.setFire(time);
    }
    
    @Override
    public void setFireNew(int fire) {
        try {
            Field onFire = ReflectionHelper.findField(Entity.class, new String[] {"fire", "field_70151_c"});
            onFire.setAccessible(true);
            onFire.setInt(this, fire);
            onFire.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    
    @Override
    public void setAge(int age) {
        entityAge = age;
    }
    
    @Override
    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }
    
    @Override
    public void setLimbSwingAmount(float amount) {
        limbSwingAmount = amount;
    }
    
    @Override
    public float getPrevHealth() {
        return prevHealth;
    }

    @Override
    public void setPrevHealth(float lastHealth) {
        prevHealth = lastHealth;
    }

    @Override
    public int getHurtTime() {
        return hurtTime;
    }

    @Override
    public void setHurtTime(int time) {
        hurtTime = time;
    }

    @Override
    public int getHurtResistantTime() {
        return hurtResistantTime;
    }

    @Override
    public void setHurtResistantTime(int time) {
        hurtResistantTime = time;
    }

    @Override
    public float getLastDamage() {
        return lastDamage;
    }

    @Override
    public void setLastDamage(float damage) {
        lastDamage = damage;
    }
    
    @Override
    public EntityPlayer getRevengePlayer() {
        return attackingPlayer;
    }

    @Override
    public void setRevengePlayer(EntityPlayer player) {
        attackingPlayer = player;
    }

    @Override
    public int getRecentlyHit() {
        return recentlyHit;
    }
    
    @Override
    public void setRecentlyHit(int time) {
        recentlyHit = time;
    }

    @Override
    public void setAttackedAtYaw(float yaw) {
        attackedAtYaw = yaw;
    }

    @Override
    public float getAttackedAtYaw() {
        return attackedAtYaw;
    }

    @Override
    public void setBeenAttacked() {
        super.setBeenAttacked();
    }
    
    @Override
    public CombatTracker getCombatTracker() {
        return super.func_110142_aN();
    }
    
    @Override
    public float applyArmorCalculations(DamageSource source, float damage) {
        return super.applyArmorCalculations(source, damage);
    }
    
    @Override
    public float applyPotionDamageCalculations(DamageSource source, float damage) {
        return super.applyPotionDamageCalculations(source, damage);
    }
    
    @Override
    public void onDeathUpdate() {
        super.onDeathUpdate();
    }
    
    @Override
    public float getSoundPitch() {
        return super.getSoundPitch();
    }
    
    @Override
    public void setAttackTime(int time) {
        attackTime = time;
    }
    
    @Override
    public int getAttackTime() {
        return attackTime;
    }
    
    private EnumCreatureAttribute creatureAttribute;
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return creatureAttribute;
    }
    
    @Override
    public void setCreatureAttribute(EnumCreatureAttribute attribute) {
        creatureAttribute = attribute;
    }
    
    @Override
    public void collideWithNearbyEntities() {
        super.collideWithNearbyEntities();
    }

    @Override
    public void setFlag(int id, boolean value) {
        super.setFlag(id, value);
    }
    //Entity stuff//    
    @Override
    public boolean interact(EntityPlayer player) {
        SoulHandler.entityRightClicked(this, player);
        return true;
    }
    
    @Override
    public void onLivingUpdate(){}
    
    @Override
    public void onEntityUpdate(){}
    
    @Override
    public void onUpdate() {
        if (ForgeHooks.onLivingUpdate(this))return;
        //isDead = true;
        SoulHandler.entityUpdate(this);
    }
    
    @Override
    public void onDeath(DamageSource source) {
        if(ForgeHooks.onLivingDeath(this, source)) return;
        
        if(source.getEntity() != null) {
            source.getEntity().onKillEntity(this);
        }
        
        SoulHandler.entityDeath(this, source);
        this.worldObj.setEntityState(this, (byte)3);
    }
    
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        SoulHandler.onKillEntity(this, entity);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if(ForgeHooks.onLivingAttack(this, source, damage)) return false;
        return SoulHandler.attackEntityFrom(this, source, damage);
    }
    
    @Override
    public void damageEntity(DamageSource source, float damage) {
        SoulHandler.damageEntity(this, source, damage);
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
        soul = new Soul(compound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        soul.writeToNBT(compound);
    }
}
