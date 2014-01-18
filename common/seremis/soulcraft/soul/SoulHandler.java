package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SoulHandler {

    public static SoulHandler instance = new SoulHandler();
    
    public void addSoulTo(EntityLivingBase entity) {
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
    
    public Soul getSoulFrom(EntityLivingBase entity) {
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

    public void entityRightClicked(EntityLivingBase entity, EntityPlayer player) {
        Soul soul = getSoulFrom(entity);
        
        for(IChromosome chromosome : soul.getChromosomes()) {
            AlleleRegistry.registry.getAction(chromosome.getPrimary().getName()).interact(entity, player);
            AlleleRegistry.registry.getAction(chromosome.getSecondary().getName()).interact(entity, player);
        }
    }
}
