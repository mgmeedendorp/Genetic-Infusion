package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

/**
 * @author Seremis
 */
public class TraitSounds extends Trait {

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {
        entity.getWorld().playSoundAtEntity((Entity) entity, name, volume, pitch);
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        entity.getWorld().theProfiler.startSection("mobBaseTick");

        int livingSoundTime = entity.getInteger("livingSoundTime");
        entity.setInteger("livingSoundTime", livingSoundTime++);
        if(!entity.getBoolean("isDead") && entity.getRandom().nextInt(1000) < livingSoundTime) {
            entity.setInteger("livingSoundTime", -entity.getInteger("talkInterval"));
            ((EntityLiving) entity).playLivingSound();
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setInteger("talkInterval", SoulHelper.geneRegistry().getValueInteger(entity, Genes.GENE_TALK_INTERVAL));
    }
}
