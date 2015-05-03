package seremis.geninfusion.soul.traits

import java.lang.Byte

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.util.DataWatcherHelper
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitExplode extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_FUSE_STATE))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], ENTITY_FUSE_STATE)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_CHARGED))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], ENTITY_CHARGED)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_IGNITED))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], ENTITY_IGNITED)

            entity.setInteger(ENTITY_EXPLOSION_RADIUS, SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GENE_EXPLOSION_RADIUS))
            entity.setInteger(ENTITY_FUSE_TIME, SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GENE_FUSE_TIME))

            entity.makePersistent(ENTITY_EXPLOSION_RADIUS)
            entity.makePersistent(ENTITY_FUSE_TIME)
        }
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)

        if(explodes) {
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_FUSE_STATE))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, ENTITY_FUSE_STATE)
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_CHARGED))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, ENTITY_CHARGED)
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_IGNITED))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, ENTITY_IGNITED)
        }
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_FUSE_STATE))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], ENTITY_FUSE_STATE)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_CHARGED))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], ENTITY_CHARGED)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, ENTITY_IGNITED))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], ENTITY_IGNITED)

            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, ENTITY_FUSE_STATE)
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, ENTITY_CHARGED)
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, ENTITY_IGNITED)
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)

        if(explodes && living.isEntityAlive) {
            val fuseTime = entity.getInteger(ENTITY_FUSE_TIME)
            var timeSinceIgnited = entity.getInteger(ENTITY_TIME_SINCE_IGNITED)

            if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, ENTITY_IGNITED)) != 0) {
                DataWatcherHelper.updateObject(living.getDataWatcher, ENTITY_FUSE_STATE, 1.toByte.asInstanceOf[Byte])
            }

            val fuseState = DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, ENTITY_FUSE_STATE).asInstanceOf[Byte].toInt

            if(fuseState > 0 && timeSinceIgnited == 0) {
                entity.playSound("creeper.primed", 1.0F, 0.5F)
            }

            timeSinceIgnited += fuseState

            if(timeSinceIgnited < 0) {
                timeSinceIgnited = 0
            }

            if(timeSinceIgnited >= fuseTime) {
                timeSinceIgnited = fuseTime

                if(!entity.getWorld.isRemote) {
                    val mobGriefing = entity.getWorld.getGameRules.getGameRuleBooleanValue("mobGriefing")

                    val explosionRadius = entity.getInteger(ENTITY_EXPLOSION_RADIUS)

                    if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, ENTITY_CHARGED)) == 1) {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius*2, mobGriefing)
                    } else {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius, mobGriefing)
                    }
                    living.setDead()
                }
            }

            entity.setInteger(ENTITY_TIME_SINCE_IGNITED, timeSinceIgnited)
        }
    }

    override def interact(entity: IEntitySoulCustom, player: EntityPlayer): Boolean = {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)
        val flintAndSteelIgnites = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_FLINT_AND_STEEL_IGNITE)

        if(explodes && flintAndSteelIgnites) {
            val living = entity.asInstanceOf[EntityLiving]

            val stack = player.getCurrentEquippedItem

            if(stack != null && stack.getItem == Items.flint_and_steel) {
                entity.getWorld.playSoundEffect(living.posX + 0.5D, living.posY + 0.5D, living.posZ + 0.5D, "fire.ignite", 1.0F, entity.getRandom.nextFloat() * 0.4F + 0.8F)

                player.swingItem()

                if(!entity.getWorld.isRemote) {
                    DataWatcherHelper.updateObject(living.getDataWatcher, ENTITY_IGNITED, 1.toByte.asInstanceOf[Byte])

                    stack.damageItem(1, player)
                    return true
                }
            }
        }
        false
    }

    override def onStruckByLightning(entity: IEntitySoulCustom, lightningBolt: EntityLightningBolt) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_CREEPER_SWELL)
        val canBeCharged = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_CAN_BE_CHARGED)


        if(explodes && canBeCharged && !entity.getWorld.isRemote) {
            DataWatcherHelper.updateObject(living.getDataWatcher, ENTITY_CHARGED, 1.toByte.asInstanceOf[Byte])
        }
    }
}
