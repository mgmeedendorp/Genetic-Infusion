package seremis.soulcraft.soul.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;

public abstract class EntityEventHandler {

    public abstract void onInit(IEntitySoulCustom entity);
    
    public abstract void onUpdate(IEntitySoulCustom entity);
    
    public abstract void onInteract(IEntitySoulCustom entity, EntityPlayer player);
    
    public abstract void onDeath(IEntitySoulCustom entity, DamageSource source);

    public abstract void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed);

    public abstract boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage);

    public abstract void onSpawnWithEgg(IEntitySoulCustom entity, EntityLivingData data);
}
