package seremis.geninfusion.soul.traits

import net.minecraft.entity.{Entity, EntityLiving}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitSounds extends Trait {

    override def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float) {
        if(name != null && !name.equals(""))
            entity.getWorld.playSoundAtEntity(entity.asInstanceOf[Entity], name, volume, pitch)
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        entity.getWorld.theProfiler.startSection("mobBaseTick")

        val livingSoundTime = entity.getInteger(EntityLivingSoundTime) + 1
        entity.setInteger(EntityLivingSoundTime, livingSoundTime)

        if (!entity.getBoolean(EntityIsDead) && entity.getRandom.nextInt(1000) < livingSoundTime) {
            entity.setInteger(EntityLivingSoundTime, -entity.getInteger(EntityTalkInterval))
            entity.asInstanceOf[EntityLiving].playLivingSound()
        }

        entity.getWorld.theProfiler.endSection()
    }

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setInteger(EntityTalkInterval, SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GeneTalkInterval))
    }
}