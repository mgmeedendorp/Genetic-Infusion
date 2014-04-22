package seremis.soulcraft.soul.traits;

import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class Trait {
    
    public void onInit(IEntitySoulCustom entity){}
    
    public void onUpdate(IEntitySoulCustom entity){}
    
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player){}
    
    public void onDeath(IEntitySoulCustom entity, DamageSource source){}

    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed){}

    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage){return true;}

    public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data){}
    
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch){}

    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage){}
}
