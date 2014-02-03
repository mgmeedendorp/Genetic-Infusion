package seremis.soulcraft.soul;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class SoulHandler {
    
    public static List<EntityEventHandler> eventHandlers = new ArrayList<EntityEventHandler>();
    
    public static void registerHandler(EntityEventHandler handler) {
        eventHandlers.add(handler);
    }
    
    public static Soul getSoulFrom(EntityLiving entity) {
        Soul soul = null;
        
        NBTTagCompound compound = entity.getEntityData();
        
        if(entity instanceof IEntitySoulCustom) {
            NBTTagCompound compound1 = compound.getCompoundTag("soul");
            
            soul = new Soul(compound1);
        } else {
            soul = SoulTemplates.getSoulPreset(entity);
        }
        return soul;
    }
    
    public static boolean hasSoul(IEntitySoulCustom entity) {
        NBTTagCompound compound = entity.getEntityData();
        return compound.hasKey("soul");
    }
    
    public static boolean isSoulPreset(Soul soul) {
        return ((AlleleBoolean)soul.chromosomes[EnumChromosome.IS_TEMPLATE_GENOME.ordinal()].getActive()).value;
    }
    
    public static boolean isSoulEntity(IEntitySoulCustom entity) {
        return entity instanceof IEntitySoulCustom;
    }

    public static IChromosome getChromosomeFrom(EntityLiving entity, EnumChromosome chromosome) {
        return getSoulFrom(entity).chromosomes[chromosome.ordinal()];
    }
    
    
    
    public static void entityInit(IEntitySoulCustom entity) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onInit(entity);
            }
        }
    }
    
    public static void entityUpdate(IEntitySoulCustom entity) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onUpdate(entity);
            }
        }
    }
    
    public static void entityRightClicked(IEntitySoulCustom entity, EntityPlayer player) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onInteract(entity, player);
            }
        }
    }

    public static void entityDeath(IEntitySoulCustom entity, DamageSource source) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onDeath(entity, source);
            }
        }
    }

    public static void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onKillEntity(entity, killed);
            }
        }
    }

    public static boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onEntityAttacked(entity, source, damage);
            }
        }
        return true;
    }

    public static EntityLivingData spawnEntityFromEgg(IEntitySoulCustom entity, EntityLivingData data) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.onSpawnWithEgg(entity, data);
            }
        }
        return data;
    }

    public static void playSoundAtEntity(IEntitySoulCustom entity, String name, float volume, float pitch) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(EntityEventHandler handler : eventHandlers) {
                handler.playSound(entity, name, volume, pitch);
            }
        }
    }
}
