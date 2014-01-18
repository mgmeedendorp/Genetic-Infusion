package seremis.soulcraft.soul;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IAlleleAction {

    void interact(EntityLivingBase entity, EntityPlayer player);
}
