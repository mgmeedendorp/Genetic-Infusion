package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SoulHandler {

    public static void addSoulTo(EntityLivingBase entity) {
        NBTTagCompound compound = entity.getEntityData();
        if(!compound.hasKey("soul")) {
            
            Soul soul = SoulPresets.getStandardSoulFromEntity(entity);
            
            if(soul != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                soul.writeToNBT(compound1);
            
                compound.setCompoundTag("soul", compound1);
            }
        }
    }
    
    public static Soul getSoulFrom(EntityLivingBase entity) {
        Soul soul = null;
        
        NBTTagCompound compound = entity.getEntityData();
        
        if(compound.hasKey("soul")) {
            NBTTagCompound compound1 = compound.getCompoundTag("soul");
            
            soul = new Soul(compound1);
        } else {
            addSoulTo(entity);
            compound = entity.getEntityData();
            NBTTagCompound compound1 = compound.getCompoundTag("soul");
            
            soul = new Soul(compound1);
        }
        return soul;
    }
    
    public static boolean isSoulPreset(Soul soul) {
        return ((AlleleBoolean)soul.chromosomes[EnumChromosome.IS_TEMPLATE_GENOME.ordinal()].getActive()).value;
    }

    public static void entityRightClicked(EntityLivingBase entity, EntityPlayer player) {
        Soul soul = getSoulFrom(entity);
        
        if(!isSoulPreset(soul)) {
            for(int i = 1; i < soul.chromosomes.length; i++) {
                EnumChromosome.values()[i].action.interact(entity, player);;
            }
        }
    }
}
