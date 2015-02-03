package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.SoulHelper;

public class Trait implements ITrait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public boolean interact(IEntitySoulCustom entity, EntityPlayer player) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, player);
        return obj != null && (Boolean) obj;
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        SoulHelper.traitHandler.callSuperTrait(entity, source);
    }

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        SoulHelper.traitHandler.callSuperTrait(entity, killed);
    }

    @Override
    public boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, source, damage);
        return obj != null && (Boolean) obj;
    }

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, IEntityLivingData data) {
        SoulHelper.traitHandler.callSuperTrait(entity, data);
    }

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {
        SoulHelper.traitHandler.callSuperTrait(entity, name, volume, pitch);
    }

    @Override
    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        SoulHelper.traitHandler.callSuperTrait(entity, source, damage);
    }

    @Override
    public void updateAITick(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, entityToAttack);
        return obj != null && (Boolean) obj;
    }

    @Override
    public void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        SoulHelper.traitHandler.callSuperTrait(entity, entityToAttack, distance);
    }

    @Override
    public Entity findPlayerToAttack(IEntitySoulCustom entity) {
        return (Entity) SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, source, damage);
        return obj != null ? (Float) obj : 0.0F;
    }

    @Override
    public float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, source, damage);
        return obj != null ? (Float) obj : 0.0F;
    }

    @Override
    public void damageArmor(IEntitySoulCustom entity, float damage) {
        SoulHelper.traitHandler.callSuperTrait(entity, damage);
    }

    @Override
    public void setOnFireFromLava(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        Object obj = SoulHelper.traitHandler.callSuperTrait(entity, x, y, z);
        return obj != null ? (Float) obj : 0.0F;
    }

    @Override
    public void updateEntityActionState(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    @Override
    public void updateWanderPath(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }

    //TODO implement this in TraitAttack
    @Override
    public void attackEntityWithRangedAttack(IEntitySoulCustom entity, EntityLivingBase target, float distanceModified) {
        SoulHelper.traitHandler.callSuperTrait(entity, target, distanceModified);
    }

    @Override
    public void render(IEntitySoulCustom entity) {
        SoulHelper.traitHandler.callSuperTrait(entity);
    }
}
