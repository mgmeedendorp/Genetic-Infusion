package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;

public class Trait implements ITrait {

	@Override
	public void onUpdate(IEntitySoulCustom entity){}

	@Override
	public void onInteract(IEntitySoulCustom entity, EntityPlayer player){}

	@Override
	public void onDeath(IEntitySoulCustom entity, DamageSource source){}

	@Override
	public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed){}

	@Override
	public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage){return true;}

	@Override
	public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data){}

    @Override
	public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch){}

	@Override
	public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage){}

	@Override
	public void updateAITick(IEntitySoulCustom entity) {}

    @Override
    public void firstTick(IEntitySoulCustom entity) {}

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {return true;}

    @Override
    public void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {}
}
