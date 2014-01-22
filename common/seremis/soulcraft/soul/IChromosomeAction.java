package seremis.soulcraft.soul;

import net.minecraft.entity.player.EntityPlayer;

public interface IChromosomeAction {

    void init(IChromosome chromosome, IEntitySoulCustom entity);
    
    void entityUpdate(IChromosome chromosome, IEntitySoulCustom entity);
    
    void interact(IChromosome chromosome, IEntitySoulCustom entity, EntityPlayer player);

    void dropItems(IChromosome chromosome, IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel);
}
