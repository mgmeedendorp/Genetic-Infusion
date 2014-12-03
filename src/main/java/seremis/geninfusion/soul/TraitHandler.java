package seremis.geninfusion.soul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;

import java.util.LinkedList;

public class TraitHandler {

    static LinkedList<ITrait> entityUpdate = new LinkedList<ITrait>();

    public static void entityUpdate(IEntitySoulCustom entity) {
        for(ITrait trait : entityUpdate) {
            trait.onUpdate(entity);
        }
    }

    static LinkedList<ITrait> interact = new LinkedList<ITrait>();

    public static boolean interact(IEntitySoulCustom entity, EntityPlayer player) {
        boolean flag = false;
        for(ITrait trait : interact) {
            if(trait.interact(entity, player)) {
                flag = true;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> entityDeath = new LinkedList<ITrait>();

    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        for(ITrait trait : entityDeath) {
            trait.onDeath(entity, source);
        }
    }

    static LinkedList<ITrait> onKillEntity = new LinkedList<ITrait>();

    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        for(ITrait trait : onKillEntity) {
            trait.onKillEntity(entity, killed);
        }
    }

    static LinkedList<ITrait> attackEntityFrom = new LinkedList<ITrait>();

    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean flag = false;
        for(ITrait trait : attackEntityFrom) {
            if(trait.attackEntityFrom(entity, source, damage)) {
                flag = true;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> attackEntity = new LinkedList<ITrait>();

    public static void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        for(ITrait trait : attackEntity) {
            trait.attackEntity(entity, entityToAttack, distance);
        }
    }

    static LinkedList<ITrait> spawnEntityFromEgg = new LinkedList<ITrait>();

    public static IEntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, IEntityLivingData data) {
        for(ITrait trait : spawnEntityFromEgg) {
            trait.onSpawnWithEgg(entity, data);
        }
        return data;
    }

    static LinkedList<ITrait> playSoundAtEntity = new LinkedList<ITrait>();

    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        for(ITrait trait : playSoundAtEntity) {
            trait.playSound(entity, name, volume, pitch);
        }
    }

    static LinkedList<ITrait> damageEntity = new LinkedList<ITrait>();

    public static void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        for(ITrait trait : damageEntity) {
            trait.damageEntity(entity, source, damage);
        }
    }

    static LinkedList<ITrait> updateAITick = new LinkedList<ITrait>();

    public static void updateAITick(IEntitySoulCustom entity) {
        for(ITrait trait : updateAITick) {
            trait.updateAITick(entity);
        }
    }

    static LinkedList<ITrait> firstTick = new LinkedList<ITrait>();

    public static void firstTick(IEntitySoulCustom entity) {
        for(ITrait trait : firstTick) {
            trait.firstTick(entity);
        }
    }

    static LinkedList<ITrait> attackEntityAsMob = new LinkedList<ITrait>();

    public static boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        boolean flag = false;
        for(ITrait trait : attackEntityAsMob) {
            if(trait.attackEntityAsMob(entity, entityToAttack)) {
                flag = true;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> findPlayerToAttack = new LinkedList<ITrait>();

    public static Entity findPlayerToAttack(IEntitySoulCustom entity) {
        Entity flag = null;
        for(ITrait trait : findPlayerToAttack) {
            Entity tmp = trait.findPlayerToAttack(entity);
            if(tmp != null) {
                flag = tmp;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> applyArmorCalculations = new LinkedList<ITrait>();

    public static float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : applyArmorCalculations) {
            float tmp = trait.applyArmorCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> applyPotionDamageCalculations = new LinkedList<ITrait>();

    public static float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : applyPotionDamageCalculations) {
            float tmp = trait.applyPotionDamageCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
        }
        return flag;
    }

    static LinkedList<ITrait> damageArmor = new LinkedList<ITrait>();

    public static void damageArmor(IEntitySoulCustom entity, float damage) {
        for(ITrait trait : damageArmor) {
            trait.damageArmor(entity, damage);
        }
    }
}
