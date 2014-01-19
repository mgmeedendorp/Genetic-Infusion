package seremis.soulcraft.soul;

import java.util.logging.Level;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.lib.DefaultProps;
import seremis.soulcraft.soul.allele.AlleleBoolean;

public class SoulHandler {

    public static void addSoulTo(EntityLiving entity) {
        NBTTagCompound compound = entity.getEntityData();
        
        if(!hasSoul(entity)) {
            
            Soul soul = SoulTemplates.getSoulPreset(entity);
            
            if(soul != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                soul.writeToNBT(compound1);
            
                compound.setCompoundTag("soul", compound1);
            }
        } else {
            SoulCraft.logger.log(Level.INFO, "A mod added a mob that is not compatible with " + DefaultProps.name + ", entity: " + entity.getEntityName());
        }
    }
    
    public static Soul getSoulFrom(EntityLiving entity) {
        Soul soul = null;
        
        NBTTagCompound compound = entity.getEntityData();
        
        if(hasSoul(entity)) {
            NBTTagCompound compound1 = compound.getCompoundTag("soul");
            
            soul = new Soul(compound1);
        } else {
            addSoulTo(entity);
            compound = entity.getEntityData();
            
            if(hasSoul(entity)) {
                NBTTagCompound compound1 = compound.getCompoundTag("soul");
                soul = new Soul(compound1);
            }
        }
        return soul;
    }
    
    public static boolean hasSoul(EntityLiving entity) {
        NBTTagCompound compound = entity.getEntityData();
        return compound.hasKey("soul");
    }
    
    public static boolean isSoulPreset(Soul soul) {
        return ((AlleleBoolean)soul.chromosomes[EnumChromosome.IS_TEMPLATE_GENOME.ordinal()].getActive()).value;
    }
    
    public static boolean isSoulEntity(EntityLiving entity) {
        return entity instanceof IEntitySoulCustom;
    }

    public static void entityInit(EntityLiving entity) {
        Soul soul = getSoulFrom(entity);
        
        if(soul != null && !isSoulPreset(soul) && isSoulEntity(entity)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                EnumChromosome.values()[i].action.init(soul.chromosomes[i], (IEntitySoulCustom) entity);
            }
        }
    }
    
    public static void entityRightClicked(EntityLiving entity, EntityPlayer player) {
        Soul soul = getSoulFrom(entity);
        
        if(!isSoulPreset(soul)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                if(EnumChromosome.values()[i].type == EnumChromosomeType.UPDATE) {
                    EnumChromosome.values()[i].action.interact(soul.chromosomes[i], (IEntitySoulCustom) entity, player);
                }
            }
        }
    }
}
