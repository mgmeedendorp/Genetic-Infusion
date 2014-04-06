package seremis.soulcraft.soul.entity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import seremis.soulcraft.entity.SCEntityLiving;
import seremis.soulcraft.soul.Soul;
import seremis.soulcraft.soul.traits.TraitHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EntitySoulCustom extends SCEntityLiving implements IEntitySoulCustom, IEntityAdditionalSpawnData {
    
    private Soul soul;
    
    public EntitySoulCustom(World world) {
        super(world);
    }
    
    public EntitySoulCustom(World world, Soul soul, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
        setSize(0.8F, 1.7F);
        this.soul = soul;
        TraitHandler.entityInit(this);
    }
    

    @Override
    public void writeSpawnData(ByteArrayDataOutput data) {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        
        byte[] abyte = null;
        try {
            abyte = CompressedStreamTools.compress(compound);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        data.writeShort((short)abyte.length);
        data.write(abyte);
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data) {
        short short1 = data.readShort();
        byte[] abyte = new byte[short1];
        data.readFully(abyte);
        NBTTagCompound compound;
        try {
            compound = CompressedStreamTools.decompress(abyte);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        readFromNBT(compound);
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
    public double getMotionX() {
        return motionX;
    }

    @Override
    public double getMotionY() {
        return motionY;
    }

    @Override
    public double getMotionZ() {
        return motionZ;
    }
    
    @Override
    public void setMotion(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;
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
    
    @Override
    public void setIsJumping(boolean jump) {
        this.isJumping = true;
    }

    @Override
    public boolean getIsJumping() {
        return isJumping;
    }
    
    @Override
    public void setMoveStrafing(float strafe) {
        this.moveStrafing = strafe;        
    }

    @Override
    public float getMoveStrafing() {
        return moveStrafing;
    }

    @Override
    public float getMoveForward() {
        return moveForward;
    }
    

    @Override
    public double getPrevPosX() {
        return prevPosX;
    }

    @Override
    public double getPrevPosY() {
        return prevPosY;
    }

    @Override
    public double getPrevPosZ() {
        return prevPosZ;
    }

    @Override
    public void setPrevPosX(double x) {
        prevPosX = x;
    }

    @Override
    public void setPrevPosY(double y) {
        prevPosY = y;
    }

    @Override
    public void setPrevPosZ(double z) {
        prevPosZ = z;
    }

    @Override
    public float getPrevRotationYaw() {
        return prevRotationYaw;
    }

    @Override
    public float getPrevRotationPitch() {
        return prevRotationPitch;
    }

    @Override
    public void setPrevRotationYaw(float yaw) {
        prevRotationYaw = yaw;
    }

    @Override
    public void setPrevRotationPitch(float pitch) {
        prevRotationPitch = pitch;
    }

    @Override
    public boolean getIsInPortal() {
        return inPortal;
    }

    @Override
    public void setIsInPortal(boolean inPortal) {
        this.inPortal = inPortal;
    }

    @Override
    public int getPortalTimer() {
        return portalCounter;
    }

    @Override
    public void setPortalTimer(int time) {
        portalCounter = time;
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        timeUntilPortal = cooldown;
    }

    @Override
    public int getPortalCooldown() {
        return timeUntilPortal;
    }

    @Override
    public void setRidingEntity(Entity ridingEntity) {
        this.ridingEntity = ridingEntity;
    }

    @Override
    public Entity getRidingEntity() {
        return ridingEntity;
    }
    

    @Override
    public void setPrevDistanceWalkedModified(float prevDistanceWalkedModified) {
        this.prevDistanceWalkedModified = prevDistanceWalkedModified;
    }

    @Override
    public void setDistanceWalkedModified(float distanceWalkedModified) {
        this.distanceWalkedModified = distanceWalkedModified;        
    }

    @Override
    public float getPrevDistanceWalkedModified() {
        return prevDistanceWalkedModified;
    }

    @Override
    public float getDistanceWalkedModified() {
        return distanceWalkedModified;
    }
    
    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setBoundingBox(AxisAlignedBB boundingBox) {
        try {
            Field bounds = ReflectionHelper.findField(Entity.class, new String[] {"boundingBox", "field_70121_D"});
            bounds.setAccessible(true);
            bounds.set(this, boundingBox);
            bounds.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getCameraPitch() {
        return this.cameraPitch;
    }

    @Override
    public void setCameraPitch(float cameraPitch) {
        this.cameraPitch = cameraPitch;
    }

    @Override
    public float getPrevCameraPitch() {
        return this.prevCameraPitch;
    }

    @Override
    public void setPrevCameraPitch(float prevCameraPitch) {
        this.prevCameraPitch = prevCameraPitch;
    }

    @Override
    public void setIsSprinting(boolean sprint) {
        this.setSprinting(sprint);
    }

    @Override
    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    @Override
    public float getFallDistance() {
        return fallDistance;
    }

    @Override
    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public void setRenderYawOffset(float yawOffset) {
        this.renderYawOffset = yawOffset;
    }

    @Override
    public float getRenderYawOffset() {
        return renderYawOffset;
    }

    @Override
    public void setPrevRenderYawOffset(float prevYawOffset) {
        this.prevRenderYawOffset = prevYawOffset;
    }

    @Override
    public float getPrevRenderYawOffset() {
        return prevRenderYawOffset;
    }

    @Override
    public void setPrevRotationYawHead(float yaw) {
        this.prevRotationYawHead = yaw;
    }

    @Override
    public float getPrevRotationYawHead() {
        return prevRotationYawHead;
    }
    
    @Override
    public void updatePotionEffects() {
        super.updatePotionEffects();
    }
    
    //Entity stuff//    
    @Override
    public boolean interact(EntityPlayer player) {
        TraitHandler.entityRightClicked(this, player);
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
        TraitHandler.entityUpdate(this);
    }
    
    @Override
    public void onDeath(DamageSource source) {
        if(ForgeHooks.onLivingDeath(this, source)) return;
        
        if(source.getEntity() != null) {
            source.getEntity().onKillEntity(this);
        }
        
        TraitHandler.entityDeath(this, source);
        worldObj.setEntityState(this, (byte)3);
    }
    
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        TraitHandler.onKillEntity(this, entity);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if(ForgeHooks.onLivingAttack(this, source, damage)) return false;
        return TraitHandler.attackEntityFrom(this, source, damage);
    }
    
    @Override
    public void damageEntity(DamageSource source, float damage) {
        TraitHandler.damageEntity(this, source, damage);
    }
    
    @Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData data) {
        return TraitHandler.spawnEntityFromEgg(this, data);
    }
    
    @Override
    public void playSound(String name, float volume, float pitch) {
        TraitHandler.playSoundAtEntity(this, name, volume, pitch);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        soul = new Soul(compound);
        
        NBTTagList tagList = (NBTTagList) compound.getTag("traitVariables");
        
        NBTTagCompound compoundBoolean = null;
        NBTTagCompound compoundByte = null;
        NBTTagCompound compoundInteger = null;
        NBTTagCompound compoundFloat = null;
        NBTTagCompound compoundDouble = null;
        NBTTagCompound compoundString = null;
        
        for(int i = 0; i < tagList.tagCount(); i++) {
            if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "boolean") {
                compoundBoolean = (NBTTagCompound)tagList.tagAt(i);
            } else if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "byte") {
                compoundByte = (NBTTagCompound)tagList.tagAt(i);
            } else if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "integer") {
                compoundInteger = (NBTTagCompound)tagList.tagAt(i);
            } else if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "float") {
                compoundFloat = (NBTTagCompound)tagList.tagAt(i);
            } else if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "double") {
                compoundDouble = (NBTTagCompound)tagList.tagAt(i);
            } else if(((NBTTagCompound)tagList.tagAt(i)).getString("name") == "string") {
                compoundString = (NBTTagCompound)tagList.tagAt(i);
            }
        }
        
        if(compoundBoolean != null) {
            int size = compoundBoolean.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundBoolean.getString("boolean" + i + "Name");
                boolean value = compoundBoolean.getBoolean("boolean" + i + "Value");
                persistentBoolean.put(name, value);
            }
        }
        
        if(compoundByte != null) {
            int size = compoundByte.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundByte.getString("byte" + i + "Name");
                byte value = compoundByte.getByte("byte" + i + "Value");
                persistentByte.put(name, value);
            }
        }
        
        if(compoundInteger != null) {
            int size = compoundInteger.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundInteger.getString("integer" + i + "Name");
                int value = compoundInteger.getInteger("integer" + i + "Value");
                persistentInteger.put(name, value);
            }
        }
        
        if(compoundFloat != null) {
            int size = compoundFloat.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundFloat.getString("float" + i + "Name");
                float value = compoundFloat.getFloat("float" + i + "Value");
                persistentFloat.put(name, value);
            }
        }
        
        if(compoundDouble != null) {
            int size = compoundDouble.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundDouble.getString("double" + i + "Name");
                double value = compoundDouble.getDouble("double" + i + "Value");
                persistentDouble.put(name, value);
            }
        }
        
        if(compoundString != null) {
            int size = compoundString.getInteger("size");
            for(int i = 0; i < size; i++) {
                String name = compoundString.getString("string" + i + "Name");
                String value = compoundString.getString("string" + i + "Value");
                persistentString.put(name, value);
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        soul.writeToNBT(compound);
        
        NBTTagList tagList = new NBTTagList();
        
        List<String> stringList = new ArrayList<String>();
        stringList.addAll(persistentBoolean.keySet());
        
        NBTTagCompound booleanCompound = new NBTTagCompound();
        for(int i = 0; i < persistentBoolean.size(); i++) {
            booleanCompound.setString("boolean" + i + "Name", stringList.get(i));
            booleanCompound.setBoolean("boolean" + i + "Value", persistentBoolean.get(stringList.get(i)));
        }
        booleanCompound.setString("type", "boolean");
        booleanCompound.setInteger("size", persistentBoolean.size());
        tagList.appendTag(booleanCompound);
        
        stringList.clear();
        stringList.addAll(persistentByte.keySet());
        
        NBTTagCompound byteCompound = new NBTTagCompound();
        for(int i = 0; i < persistentByte.size(); i++) {
            byteCompound.setString("byte" + i + "Name", stringList.get(i));
            byteCompound.setByte("byte" + i + "Value", persistentByte.get(stringList.get(i)));
        }
        byteCompound.setString("type", "byte");
        byteCompound.setInteger("size", persistentByte.size());
        tagList.appendTag(byteCompound);
        
        stringList.clear();
        stringList.addAll(persistentInteger.keySet());
        
        NBTTagCompound integerCompound = new NBTTagCompound();
        for(int i = 0; i < persistentInteger.size(); i++) {
            integerCompound.setString("integer" + i + "Name", stringList.get(i));
            integerCompound.setInteger("integer" + i + "Value", persistentInteger.get(stringList.get(i)));
        }
        integerCompound.setString("type", "integer");
        integerCompound.setInteger("size", persistentInteger.size());
        tagList.appendTag(integerCompound);
        
        stringList.clear();
        stringList.addAll(persistentFloat.keySet());
        
        NBTTagCompound floatCompound = new NBTTagCompound();
        for(int i = 0; i < persistentFloat.size(); i++) {
            floatCompound.setString("float" + i + "Name", stringList.get(i));
            floatCompound.setFloat("float" + i + "Value", persistentFloat.get(stringList.get(i)));
        }
        floatCompound.setString("type", "float");
        floatCompound.setInteger("size", persistentFloat.size());
        
        stringList.clear();
        stringList.addAll(persistentDouble.keySet());
        
        NBTTagCompound doubleCompound = new NBTTagCompound();
        for(int i = 0; i < persistentDouble.size(); i++) {
            doubleCompound.setString("double" + i + "Name", stringList.get(i));
            doubleCompound.setDouble("double" + i + "Value", persistentDouble.get(stringList.get(i)));
        }
        doubleCompound.setString("type", "double");
        doubleCompound.setInteger("size", persistentDouble.size());
        
        stringList.clear();
        stringList.addAll(persistentString.keySet());
        
        NBTTagCompound stringCompound = new NBTTagCompound();
        for(int i = 0; i < persistentString.size(); i++) {
            stringCompound.setString("string" + i + "Name", stringList.get(i));
            stringCompound.setString("string" + i + "Value", persistentString.get(stringList.get(i)));
        }
        stringCompound.setString("type", "string");
        stringCompound.setInteger("size", persistentString.size());
        
        compound.setTag("traitVariables", tagList);
    }
    
    private HashMap<String, Boolean> persistentBoolean = new HashMap<String, Boolean>(); 
    private HashMap<String, Byte> persistentByte = new HashMap<String, Byte>(); 
    private HashMap<String, Integer> persistentInteger = new HashMap<String, Integer>(); 
    private HashMap<String, Float> persistentFloat = new HashMap<String, Float>(); 
    private HashMap<String, Double> persistentDouble = new HashMap<String, Double>(); 
    private HashMap<String, String> persistentString = new HashMap<String, String>(); 
    
    private HashMap<String, Boolean> variableBoolean = new HashMap<String, Boolean>(); 
    private HashMap<String, Byte> variableByte = new HashMap<String, Byte>(); 
    private HashMap<String, Integer> variableInteger = new HashMap<String, Integer>(); 
    private HashMap<String, Float> variableFloat = new HashMap<String, Float>(); 
    private HashMap<String, Double> variableDouble = new HashMap<String, Double>(); 
    private HashMap<String, String> variableString = new HashMap<String, String>(); 
    
    @Override
    public void setPersistentVariable(String name, boolean variable) {
        for(String string : persistentBoolean.keySet()) {
            if(string.equals(name)) {
                persistentBoolean.remove(name);
            }
        }
        persistentBoolean.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, byte variable) {
        for(String string : persistentByte.keySet()) {
            if(string.equals(name)) {
                persistentByte.remove(name);
            }
        }
        persistentByte.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, int variable) {
        for(String string : persistentInteger.keySet()) {
            if(string.equals(name)) {
                persistentInteger.remove(name);
            }
        }
        persistentInteger.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, float variable) {
        for(String string : persistentFloat.keySet()) {
            if(string.equals(name)) {
                persistentFloat.remove(name);
            }
        }
        persistentFloat.put(name, variable);        
    }

    @Override
    public void setPersistentVariable(String name, double variable) {
        for(String string : persistentDouble.keySet()) {
            if(string.equals(name)) {
                persistentDouble.remove(name);
            }
        }
        persistentDouble.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, String variable) {
        for(String string : persistentString.keySet()) {
            if(string.equals(name)) {
                persistentString.remove(name);
            }
        }
        persistentString.put(name, variable);
    }
    
    @Override
    public boolean getPersistentBoolean(String name) {
        return persistentBoolean.get(name);
    }

    @Override
    public byte getPersistentByte(String name) {
        return persistentByte.get(name);
    }

    @Override
    public int getPersistentInteger(String name) {
        return persistentInteger.get(name);
    }

    @Override
    public float getPersistentFloat(String name) {
        return persistentFloat.get(name);
    }

    @Override
    public double getPersistentDouble(String name) {
        return persistentDouble.get(name);
    }

    @Override
    public String getPersistentString(String name) {
        return persistentString.get(name);
    }

    @Override
    public void setVariable(String name, boolean variable) {
        for(String string : variableBoolean.keySet()) {
            if(string.equals(name)) {
                variableBoolean.remove(name);
            }
        }
        variableBoolean.put(name, variable);
    }

    @Override
    public void setVariable(String name, byte variable) {
        for(String string : variableByte.keySet()) {
            if(string.equals(name)) {
                persistentByte.remove(name);
            }
        }
        persistentByte.put(name, variable);
    }

    @Override
    public void setVariable(String name, int variable) {
        for(String string : variableInteger.keySet()) {
            if(string.equals(name)) {
                variableInteger.remove(name);
            }
        }
        variableInteger.put(name, variable);
    }

    @Override
    public void setVariable(String name, float variable) {
        for(String string : variableFloat.keySet()) {
            if(string.equals(name)) {
                variableFloat.remove(name);
            }
        }
        variableFloat.put(name, variable);        
    }

    @Override
    public void setVariable(String name, double variable) {
        for(String string : variableDouble.keySet()) {
            if(string.equals(name)) {
                variableDouble.remove(name);
            }
        }
        variableDouble.put(name, variable);
    }

    @Override
    public void setVariable(String name, String variable) {
        for(String string : variableString.keySet()) {
            if(string.equals(name)) {
                variableString.remove(name);
            }
        }
        variableString.put(name, variable);
    }

    @Override
    public boolean getBoolean(String name) {
        return variableBoolean.get(name);
    }

    @Override
    public byte getByte(String name) {
        return variableByte.get(name);
    }

    @Override
    public int getInteger(String name) {
        return variableInteger.get(name);
    }

    @Override
    public float getFloat(String name) {
        return variableFloat.get(name);
    }

    @Override
    public double getDouble(String name) {
        return variableDouble.get(name);
    }

    @Override
    public String getString(String name) {
        return variableString.get(name);
    }
}
