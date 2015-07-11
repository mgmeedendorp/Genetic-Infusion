package seremis.geninfusion.soul.traits

import java.lang.Byte

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper

class TraitExplode extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityFuseState))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], EntityFuseState)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityCharged))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], EntityCharged)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityIgnited))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], EntityIgnited)

            entity.setInteger(EntityExplosionRadius, SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GeneExplosionRadius))
            entity.setInteger(EntityFuseTime, SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GeneFuseTime))

            entity.makePersistent(EntityExplosionRadius)
            entity.makePersistent(EntityFuseTime)
        }
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes) {
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityFuseState))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, EntityFuseState)
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityCharged))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, EntityCharged)
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityIgnited))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, EntityIgnited)
        }
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityFuseState))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], EntityFuseState)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityCharged))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], EntityCharged)
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, EntityIgnited))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], EntityIgnited)

            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, EntityFuseState)
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, EntityCharged)
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, EntityIgnited)
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes && living.isEntityAlive) {
            val fuseTime = entity.getInteger(EntityFuseTime)
            var timeSinceIgnited = entity.getInteger(EntityTimeSinceIgnited)

            if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, EntityIgnited)) != 0) {
                DataWatcherHelper.updateObject(living.getDataWatcher, EntityFuseState, 1.toByte.asInstanceOf[Byte])
            }

            val fuseState = DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, EntityFuseState).asInstanceOf[Byte].toInt

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

                    val explosionRadius = entity.getInteger(EntityExplosionRadius)

                    if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, EntityCharged)) == 1) {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius*2, mobGriefing)
                    } else {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius, mobGriefing)
                    }
                    living.setDead()
                }
            }

            entity.setInteger(EntityTimeSinceIgnited, timeSinceIgnited)
        }
    }

    override def interact(entity: IEntitySoulCustom, player: EntityPlayer): Boolean = {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)
        val flintAndSteelIgnites = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneFlintAndSteelIgnite)

        if(explodes && flintAndSteelIgnites) {
            val living = entity.asInstanceOf[EntityLiving]

            val stack = player.getCurrentEquippedItem

            if(stack != null && stack.getItem == Items.flint_and_steel) {
                entity.getWorld.playSoundEffect(living.posX + 0.5D, living.posY + 0.5D, living.posZ + 0.5D, "fire.ignite", 1.0F, entity.getRandom.nextFloat() * 0.4F + 0.8F)

                player.swingItem()

                if(!entity.getWorld.isRemote) {
                    DataWatcherHelper.updateObject(living.getDataWatcher, EntityIgnited, 1.toByte.asInstanceOf[Byte])

                    stack.damageItem(1, player)
                    return true
                }
            }
        }
        false
    }

    override def onStruckByLightning(entity: IEntitySoulCustom, lightningBolt: EntityLightningBolt) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)
        val canBeCharged = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanBeCharged)


        if(explodes && canBeCharged && !entity.getWorld.isRemote) {
            DataWatcherHelper.updateObject(living.getDataWatcher, EntityCharged, 1.toByte.asInstanceOf[Byte])
        }
    }
}
