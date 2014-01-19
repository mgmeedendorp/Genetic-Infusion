package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IChromosomeAction {

    void interact(EntityLivingBase entity, EntityPlayer player);
}
