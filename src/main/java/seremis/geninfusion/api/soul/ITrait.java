package seremis.geninfusion.api.soul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public interface ITrait {
    
    public void onUpdate(IEntitySoulCustom entity);
    
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player);
    
    public void onDeath(IEntitySoulCustom entity, DamageSource source);

    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed);

    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage);

    public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data);
    
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch);

    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage);
    
    public void updateAITick(IEntitySoulCustom entity);

    public void firstTick(IEntitySoulCustom entity);

    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack);
}
