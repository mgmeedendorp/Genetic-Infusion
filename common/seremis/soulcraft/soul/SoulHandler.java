package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.allele.AlleleBoolean;

public class SoulHandler {
    
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

    public static void entityInit(IEntitySoulCustom entity) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                EnumChromosome.values()[i].action.init(soul.chromosomes[i], entity);
            }
        }
    }
    
    public static void entityUpdate(IEntitySoulCustom entity) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(int i = 1; i< soul.chromosomes.length; i++) {
                if(EnumChromosome.values()[i].type == EnumChromosomeType.UPDATE) {
                    EnumChromosome.values()[i].action.init(soul.chromosomes[i], entity);
                }
            }
        }
    }
    
    public static void entityRightClicked(IEntitySoulCustom entity, EntityPlayer player) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(!isSoulPreset(soul)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                if(EnumChromosome.values()[i].type == EnumChromosomeType.UPDATE) {
                    EnumChromosome.values()[i].action.interact(soul.chromosomes[i], entity, player);
                }
            }
        }
    }

    public static void entityDropItems(IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {
        Soul soul = getSoulFrom((EntityLiving) entity);
        
        if(!isSoulPreset(soul)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                if(EnumChromosome.values()[i].type == EnumChromosomeType.UPDATE) {
                    EnumChromosome.values()[i].action.dropItems(soul.chromosomes[i], entity, recentlyHit, lootingLevel);
                }
            }
        }
    }
}
