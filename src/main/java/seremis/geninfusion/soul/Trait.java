package seremis.geninfusion.soul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;

public class Trait implements ITrait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {}

    @Override
    public boolean interact(IEntitySoulCustom entity, EntityPlayer player) {return false;}

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {}

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {}

    @Override
    public boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {return true;}

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data) {}

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {}

    @Override
    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {}

    @Override
    public void updateAITick(IEntitySoulCustom entity) {}

    @Override
    public void firstTick(IEntitySoulCustom entity) {}

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {return true;}

    @Override
    public void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {}

    @Override
    public Entity findPlayerToAttack(IEntitySoulCustom entity) {return null;}

    @Override
    public float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {return 0;}

    @Override
    public float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {return 0;}

    @Override
    public void damageArmor(IEntitySoulCustom entity, float damage) {}

    @Override
    public void setOnFireFromLava(IEntitySoulCustom entity) {}

    @Override
    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {return 0;}

    @Override
    public void updateEntityActionState(IEntitySoulCustom entity) {}

    @Override
    public void updateWanderPath(IEntitySoulCustom entity) {}

    @Override
    public void attackEntityWithRangedAttack(IEntitySoulCustom entity, EntityLivingBase target, float distanceModified) {}
}
