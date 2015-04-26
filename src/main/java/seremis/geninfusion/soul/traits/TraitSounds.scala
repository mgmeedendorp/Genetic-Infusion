package seremis.geninfusion.soul.traits

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.api.soul.lib.Genes

class TraitSounds extends Trait {

    override def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float) {
        entity.getWorld.playSoundAtEntity(entity.asInstanceOf[Entity], name, volume, pitch)
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        entity.getWorld.theProfiler.startSection("mobBaseTick")

        val livingSoundTime = entity.getInteger("livingSoundTime") + 1
        entity.setInteger("livingSoundTime", livingSoundTime)

        if (!entity.getBoolean("isDead") && entity.getRandom.nextInt(1000) < livingSoundTime) {
            entity.setInteger("livingSoundTime", -entity.getInteger("talkInterval"))
            entity.asInstanceOf[EntityLiving].playLivingSound()
        }

        entity.getWorld.theProfiler.endSection()
    }

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setInteger("talkInterval", SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GENE_TALK_INTERVAL))
    }
}