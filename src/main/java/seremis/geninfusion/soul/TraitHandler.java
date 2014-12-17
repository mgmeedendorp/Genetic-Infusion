package seremis.geninfusion.soul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitHandler;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.soul.entity.EntitySoulCustom;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TraitHandler implements ITraitHandler {

    public static HashMap<IEntitySoulCustom, String> map = new HashMap<IEntitySoulCustom, String>();

    @Override
    public Object callSuperTrait(IEntitySoulCustom entity, Object... args) {
        Object result = null;
        if(entity != null && map.containsKey(entity)) {
            String[] keys = map.get(entity).split("///");
            ITrait trait = SoulHelper.traitRegistry.getTrait(keys[0]);
            String method = keys[1];

            if(SoulHelper.traitRegistry.isOverridden(trait)) {
                ArrayList<ITrait> supers = SoulHelper.traitRegistry.getOverriding(trait);

                for(ITrait sup : supers) {
                    try {
                        Method field = sup.getClass().getMethod(method);

                        map.put(entity, SoulHelper.traitRegistry.getName(sup) + "///" + method);
                        result = field.invoke(sup, args);
                        map.put(entity, trait + "///" + method);

                    } catch(Exception e) {
                        System.out.println("It looks like something went wrong while doing a super call on a trait. The call didn't work.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static void entityUpdate(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onUpdate");
            trait.onUpdate(entity);
            map.remove(entity);
        }
    }

    public static boolean interact(IEntitySoulCustom entity, EntityPlayer player) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///interact");
            if(trait.interact(entity, player)) {
                flag = true;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onDeath");
            trait.onDeath(entity, source);
            map.remove(entity);
        }
    }

    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onKillEntity");
            trait.onKillEntity(entity, killed);
            map.remove(entity);
        }
    }

    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntityFrom");
            if(trait.attackEntityFrom(entity, source, damage)) {
                flag = true;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntity");
            trait.attackEntity(entity, entityToAttack, distance);
            map.remove(entity);
        }
    }

    public static IEntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, IEntityLivingData data) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onSpawnWithEgg");
            trait.onSpawnWithEgg(entity, data);
            map.remove(entity);
        }
        return data;
    }

    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///playSound");
            trait.playSound(entity, name, volume, pitch);
            map.remove(entity);
        }
    }

    public static void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///damageEntity");
            trait.damageEntity(entity, source, damage);
            map.remove(entity);
        }
    }

    public static void updateAITick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateAITick");
            trait.updateAITick(entity);
            map.remove(entity);
        }
    }

    public static void firstTick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///firstTick");
            trait.firstTick(entity);
            map.remove(entity);
        }
    }

    public static boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntityAsMob");
            if(trait.attackEntityAsMob(entity, entityToAttack)) {
                flag = true;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static Entity findPlayerToAttack(IEntitySoulCustom entity) {
        Entity flag = null;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///findPlayerToAttack");
            Entity tmp = trait.findPlayerToAttack(entity);
            if(tmp != null) {
                flag = tmp;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///applyArmorCalculations");
            float tmp = trait.applyArmorCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///applyPotionDamageCalculations");
            float tmp = trait.applyPotionDamageCalculations(entity, source, damage);
            if(tmp != 0.0F) {
                flag = tmp;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static void damageArmor(IEntitySoulCustom entity, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///damageArmor");
            trait.damageArmor(entity, damage);
            map.remove(entity);
        }
    }

    public static void setOnFireFromLava(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///setOnFireFromLava");
            trait.setOnFireFromLava(entity);
            map.remove(entity);
        }
    }

    public static float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        float flag = 0.0F;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///getBlockPathWeight");
            float tmp = trait.getBlockPathWeight(entity, x, y, z);
            if(tmp != 0.0F) {
                flag = tmp;
            }
            map.remove(entity);
        }
        return flag;
    }

    public static void updateEntityActionState(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateEntityActionState");
            trait.updateEntityActionState(entity);
            map.remove(entity);
        }
    }

    public static void updateWanderPath(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateWanderPath");
            trait.updateWanderPath(entity);
            map.remove(entity);
        }
    }
}
