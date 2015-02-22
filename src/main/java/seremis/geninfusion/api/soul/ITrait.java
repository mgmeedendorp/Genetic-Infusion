package seremis.geninfusion.api.soul;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;

public interface ITrait {

    public void onUpdate(IEntitySoulCustom entity);

    public boolean interact(IEntitySoulCustom entity, EntityPlayer player);

    public void onDeath(IEntitySoulCustom entity, DamageSource source);

    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed);

    public boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage);

    public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data);

    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch);

    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage);

    public void updateAITick(IEntitySoulCustom entity);

    public void firstTick(IEntitySoulCustom entity);

    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack);

    public void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance);

    public Entity findPlayerToAttack(IEntitySoulCustom entity);

    public float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage);

    public float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage);

    public void damageArmor(IEntitySoulCustom entity, float damage);

    public void setOnFireFromLava(IEntitySoulCustom entity);

    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z);

    public void updateEntityActionState(IEntitySoulCustom entity);

    public void updateWanderPath(IEntitySoulCustom entity);

    public void attackEntityWithRangedAttack(IEntitySoulCustom entity, EntityLivingBase target, float distanceModified);

    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float walkSpeed, float specialRotation, float rotationYawHead, float rotationPitch, float scale);

    public boolean isWithinHomeDistanceCurrentPosition(IEntitySoulCustom entity);

    public boolean isWithinHomeDistance(IEntitySoulCustom entity, int x, int y, int z);

    public ChunkCoordinates getHomePosition(IEntitySoulCustom entity);

    public void setHomeArea(IEntitySoulCustom entity, int x, int y, int z, int maxDistance);

    public float getMaxHomeDistance(IEntitySoulCustom entity);

    public void detachHome(IEntitySoulCustom entity);

    public boolean hasHome(IEntitySoulCustom entity);

    public void writeToNBT(IEntitySoulCustom entity, NBTTagCompound compound);

    public void readFromNBT(IEntitySoulCustom entity, NBTTagCompound compound);

    public String getEntityTexture(IEntitySoulCustom entity);
}
