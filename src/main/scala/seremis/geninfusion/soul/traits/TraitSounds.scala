package seremis.geninfusion.soul.traits

import net.minecraft.entity.{Entity, EntityLiving}
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.lib.reflection.VariableLib
import seremis.geninfusion.api.lib.reflection.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitSounds extends Trait {

    override def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float) {
        if(name != null && !name.equals(""))
            entity.getWorld_I.playSoundAtEntity(entity.asInstanceOf[Entity], name, volume, pitch)
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        entity.getWorld_I.theProfiler.startSection("mobBaseTick")

        val livingSoundTime = entity.getInteger(EntityLivingSoundTime) + 1
        entity.setInteger(EntityLivingSoundTime, livingSoundTime)

        if (!entity.getBoolean(EntityIsDead) && entity.getRandom_I.nextInt(1000) < livingSoundTime) {
            entity.setInteger(EntityLivingSoundTime, -entity.getInteger(EntityTalkInterval))
            entity.asInstanceOf[EntityLiving].playLivingSound()
        }

        entity.getWorld_I.theProfiler.endSection()
    }

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setInteger(EntityTalkInterval, SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GeneTalkInterval))
    }
}