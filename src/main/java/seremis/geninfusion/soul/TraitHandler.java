package seremis.geninfusion.soul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.SoulHelper;

public class TraitHandler {

    public static void entityUpdate(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.onUpdate(entity);
        }
    }

    public static boolean interact(IEntitySoulCustom entity, EntityPlayer player) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            if(trait.interact(entity, player)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.onDeath(entity, source);
        }
    }

    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.onKillEntity(entity, killed);
        }
    }

    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            if(trait.attackEntityFrom(entity, source, damage)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.attackEntity(entity, entityToAttack, distance);
        }
    }

    public static IEntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, IEntityLivingData data) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.onSpawnWithEgg(entity, data);
        }
        return data;
    }

    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.playSound(entity, name, volume, pitch);
        }
    }

    public static void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.damageEntity(entity, source, damage);
        }
    }

    public static void updateAITick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.updateAITick(entity);
        }
    }

    public static void firstTick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.firstTick(entity);
        }
    }

    public static boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            if(trait.attackEntityAsMob(entity, entityToAttack)) {
                flag = true;
            }
        }
        return flag;
    }

    public static Entity findPlayerToAttack(IEntitySoulCustom entity) {
        Entity flag = null;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            Entity tmp = trait.findPlayerToAttack(entity);
            if(tmp != null) {
                flag = tmp;
            }
        }
        return flag;
    }

    public static float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            float tmp = trait.applyArmorCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
        }
        return flag;
    }

    public static float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            float tmp = trait.applyPotionDamageCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
        }
        return flag;
    }

    public static void damageArmor(IEntitySoulCustom entity, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.damageArmor(entity, damage);
        }
    }

    public static void setOnFireFromLava(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.setOnFireFromLava(entity);
        }
    }

    public static float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            float tmp = trait.getBlockPathWeight(entity, x, y, z);
            if(tmp != 0.0F) {
                flag = tmp;
            }
        }
        return flag;
    }

    public static void updateEntityActionState(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.updateEntityActionState(entity);
        }
    }

    public static void updateWanderPath(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.updateWanderPath(entity);
        }
    }

    public static void attackEntityWithRangedAttack(IEntitySoulCustom entity, EntityLivingBase target, float distanceModified) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.attackEntityWithRangedAttack(entity, target, distanceModified);
        }
    }

    public static void render(IEntitySoulCustom entity, float timeModifier, float walkSpeed, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            trait.render(entity, timeModifier, walkSpeed, specialRotation, rotationYawHead, rotationPitch, scale);
        }
    }
}
