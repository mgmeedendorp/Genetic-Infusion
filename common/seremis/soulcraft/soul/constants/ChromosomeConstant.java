package seremis.soulcraft.soul.constants;

import net.minecraft.entity.player.EntityPlayer;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.IChromosomeAction;
import seremis.soulcraft.soul.IEntitySoulCustom;

public abstract class ChromosomeConstant implements IChromosomeAction {

    @Override
    public void interact(IChromosome chromosome, IEntitySoulCustom entity, EntityPlayer player) {}

}
