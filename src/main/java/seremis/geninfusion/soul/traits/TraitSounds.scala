package seremis.geninfusion.soul.traits

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.lib.Genes

class TraitSounds extends Trait {

    override def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float) {
        if(name != null && !name.equals(""))
            entity.getWorld.playSoundAtEntity(entity.asInstanceOf[Entity], name, volume, pitch)
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        entity.getWorld.theProfiler.startSection("mobBaseTick")

        val livingSoundTime = entity.getInteger(ENTITY_LIVING_SOUND_TIME) + 1
        entity.setInteger(ENTITY_LIVING_SOUND_TIME, livingSoundTime)

        if (!entity.getBoolean(ENTITY_IS_DEAD) && entity.getRandom.nextInt(1000) < livingSoundTime) {
            entity.setInteger(ENTITY_LIVING_SOUND_TIME, -entity.getInteger(ENTITY_TALK_INTERVAL))
            entity.asInstanceOf[EntityLiving].playLivingSound()
        }

        entity.getWorld.theProfiler.endSection()
    }

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setInteger(ENTITY_TALK_INTERVAL, SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GENE_TALK_INTERVAL))
    }
}