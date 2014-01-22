package seremis.soulcraft.soul.actions;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.IChromosomeAction;
import seremis.soulcraft.soul.IEntitySoulCustom;
import seremis.soulcraft.soul.allele.AlleleItemStack;

public class ChromosomeItemDrops implements IChromosomeAction {

    private int id;
    
    public ChromosomeItemDrops(int id) {
        this.id = id;
    }
    
    @Override
    public void init(IChromosome chromosome, IEntitySoulCustom entity) {}

    @Override
    public void entityUpdate(IChromosome chromosome, IEntitySoulCustom entity) {}

    @Override
    public void interact(IChromosome chromosome, IEntitySoulCustom entity, EntityPlayer player) {}

    @Override
    public void dropItems(IChromosome chromosome, IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {
        if(id == 0) {
            entity.dropItems(((AlleleItemStack)chromosome.getActive()).stack);
        }
        if(id == 1) {
           // if(new Random().nextInt())
            entity.dropItems(((AlleleItemStack)chromosome.getActive()).stack);
            //TODO make a way to store one chromosome and use it's value for another action
        }
    }

}
