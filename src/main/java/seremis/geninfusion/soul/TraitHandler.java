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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class TraitHandler implements ITraitHandler {

    public static WeakHashMap<IEntitySoulCustom, String> map = new WeakHashMap<IEntitySoulCustom, String>();

    @Override
    public Object callSuperTrait(IEntitySoulCustom entity, Object... args) {
        Object result = null;
        if(entity != null && !map.isEmpty() && map.containsKey(entity)) {
            try {
                String[] keys = map.get(entity).split("///");
                ITrait trait = SoulHelper.traitRegistry.getTrait(keys[0]);
                String method = keys[1];

                if(SoulHelper.traitRegistry.isOverriding(trait)) {
                    ArrayList<ITrait> supers = SoulHelper.traitRegistry.getOverridden(trait);

                    for(ITrait sup : supers) {
                        Class[] classes = new Class[args.length + 1];
                        Object[] arguments = new Object[args.length + 1];

                        classes[0] = IEntitySoulCustom.class;
                        arguments[0] = entity;

                        for(int i = 0; i < args.length; i++) {
                            classes[i + 1] = args[i].getClass();
                            if(classes[i + 1] == Boolean.class) classes[i + 1] = boolean.class;
                            if(classes[i + 1] == Byte.class) classes[i + 1] = byte.class;
                            if(classes[i + 1] == Short.class) classes[i + 1] = short.class;
                            if(classes[i + 1] == Integer.class) classes[i + 1] = int.class;
                            if(classes[i + 1] == Float.class) classes[i + 1] = float.class;
                            if(classes[i + 1] == Double.class) classes[i + 1] = double.class;
                            if(classes[i + 1] == Long.class) classes[i + 1] = long.class;
                            arguments[i + 1] = args[i];
                        }

                        Method field = sup.getClass().getMethod(method, classes);

                        map.put(entity, SoulHelper.traitRegistry.getName(sup) + "///" + method);
                        result = field.invoke(sup, arguments);
                        map.put(entity, trait + "///" + method);

                    }
                }
            } catch(Exception e) {
                //                System.out.println("It looks like something went wrong while doing a super call on a trait. The call didn't work.");
                //                System.out.println("Entity: " + entity);
                //                if(map.containsKey(entity)) {
                //                    System.out.println("Trait: " + map.get(entity).split("///")[0]);
                //                    System.out.println("Method: " + map.get(entity).split("///")[1]);
                //                }
                //                e.printStackTrace();
            }
        }
        return result;
    }

    public static void entityUpdate(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onUpdate");
            trait.onUpdate(entity);
        }
    }

    public static boolean interact(IEntitySoulCustom entity, EntityPlayer player) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///interact");
            if(trait.interact(entity, player)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onDeath");
            trait.onDeath(entity, source);
        }
    }

    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onKillEntity");
            trait.onKillEntity(entity, killed);
        }
    }

    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntityFrom");
            if(trait.attackEntityFrom(entity, source, damage)) {
                flag = true;
            }
        }
        return flag;
    }

    public static void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntity");
            trait.attackEntity(entity, entityToAttack, distance);
        }
    }

    public static IEntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, IEntityLivingData data) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///onSpawnWithEgg");
            trait.onSpawnWithEgg(entity, data);
        }
        return data;
    }

    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///playSound");
            trait.playSound(entity, name, volume, pitch);
        }
    }

    public static void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///damageEntity");
            trait.damageEntity(entity, source, damage);
        }
    }

    public static void updateAITick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateAITick");
            trait.updateAITick(entity);
        }
    }

    public static void firstTick(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///firstTick");
            trait.firstTick(entity);
        }
    }

    public static boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        boolean flag = false;
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntityAsMob");
            if(trait.attackEntityAsMob(entity, entityToAttack)) {
                flag = true;
            }
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
        }
        return flag;
    }

    public static void damageArmor(IEntitySoulCustom entity, float damage) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///damageArmor");
            trait.damageArmor(entity, damage);
        }
    }

    public static void setOnFireFromLava(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///setOnFireFromLava");
            trait.setOnFireFromLava(entity);
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
        }
        return flag;
    }

    public static void updateEntityActionState(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateEntityActionState");
            trait.updateEntityActionState(entity);
        }
    }

    public static void updateWanderPath(IEntitySoulCustom entity) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///updateWanderPath");
            trait.updateWanderPath(entity);
        }
    }

    public static void attackEntityWithRangedAttack(IEntitySoulCustom entity, EntityLivingBase target, float distanceModified) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///attackEntityWithRangedAttack");
            trait.attackEntityWithRangedAttack(entity, target, distanceModified);
        }
    }

    public static void render(IEntitySoulCustom entity, float timeModifier, float walkSpeed, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        for(ITrait trait : SoulHelper.traitRegistry.getOrderedTraits()) {
            map.put(entity, SoulHelper.traitRegistry.getName(trait) + "///render");
            trait.render(entity, timeModifier, walkSpeed, specialRotation, rotationYawHead, rotationPitch, scale);
        }
    }
}
