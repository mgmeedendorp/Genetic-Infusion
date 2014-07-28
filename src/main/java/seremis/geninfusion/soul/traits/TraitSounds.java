package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Trait;
import seremis.geninfusion.soul.allele.AlleleInteger;

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
        entity.setVariable("livingSoundTime", livingSoundTime++);
        if(!entity.getPersistentBoolean("isDead") && entity.getRandom().nextInt(1000) < livingSoundTime) {
            entity.setVariable("livingSoundTime", -entity.getInteger("talkInterval"));
            ((EntityLiving) entity).playLivingSound();
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setVariable("talkInterval", ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_TALK_INTERVAL)).value);
    }
}
