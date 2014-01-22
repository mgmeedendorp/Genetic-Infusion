package seremis.soulcraft.soul.actions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.IChromosomeAction;
import seremis.soulcraft.soul.IEntitySoulCustom;
import seremis.soulcraft.soul.allele.AlleleFloat;

public class ChromosomeMaxHealth implements IChromosomeAction {

    @Override
    public void init(IChromosome chromosome, IEntitySoulCustom entity) {
        entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(((AlleleFloat)chromosome.getPrimary()).value);
    }
    
    @Override
    public void entityUpdate(IChromosome chromosome, IEntitySoulCustom entity) {}

    @Override
    public void interact(IChromosome chromosome, IEntitySoulCustom entity, EntityPlayer player) {}

    @Override
    public void dropItems(IChromosome chromosome, IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {}
}
