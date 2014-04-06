package seremis.soulcraft.soul.traits;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;

public class TraitHandler {
    
    public static List<Trait> traits = new ArrayList<Trait>();
    
    public static void registerEntityTrait(Trait trait) {
        traits.add(trait);
    }
    
    public static void entityInit(IEntitySoulCustom entity) {
        for(Trait trait : traits) {
            trait.onInit(entity);
        }
    }
    
    public static void entityUpdate(IEntitySoulCustom entity) {
        for(Trait trait : traits) {
            trait.onUpdate(entity);
        }
    }
    
    public static void entityRightClicked(IEntitySoulCustom entity, EntityPlayer player) {
        for(Trait trait : traits) {
            trait.onInteract(entity, player);
        }
    }
    
    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        for(Trait trait : traits) {
            trait.onDeath(entity, source);
        }
    }
    
    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        for(Trait trait : traits) {
            trait.onKillEntity(entity, killed);
        }
    }
    
    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean flag = true;
        for(Trait trait : traits) {
            if(!trait.onEntityAttacked(entity, source, damage)) {
                flag = false;
            }
        }
        return flag;
    }
    
    public static EntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, EntityLivingData data) {
        for(Trait trait : traits) {
            trait.onSpawnWithEgg(entity, data);
        }
        return data;
    }
    
    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        for(Trait trait : traits) {
            trait.playSound(entity, name, volume, pitch);
        }
    }
    
    public static void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        for(Trait trait : traits) {
            trait.damageEntity(entity, source, damage);
        }
    }
}
