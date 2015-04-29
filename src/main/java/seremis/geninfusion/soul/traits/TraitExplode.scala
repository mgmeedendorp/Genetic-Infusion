package seremis.geninfusion.soul.traits

import java.lang.Byte
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.DataWatcherHelper
import seremis.geninfusion.api.soul.{SoulHelper, IEntitySoulCustom}

class TraitExplode extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "fuseState"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], "fuseState")
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "charged"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], "charged")
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "ignited"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], "ignited")

            entity.setInteger("explosionRadius", SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GENE_EXPLOSION_RADIUS))
            entity.setInteger("fuseTime", SoulHelper.geneRegistry.getValueFromAllele[Int](entity, Genes.GENE_FUSE_TIME))

            entity.makePersistent("explosionRadius")
            entity.makePersistent("fuseTime")
        }
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)

        if(explodes) {
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, "fuseState"))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, "fuseState")
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, "charged"))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, "charged")
            if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, "ignited"))
                DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, "ignited")
        }
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)

        if(explodes) {
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "fuseState"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, -1.toByte.asInstanceOf[Byte], "fuseState")
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "charged"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], "charged")
            if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, "ignited"))
                DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, 0.toByte.asInstanceOf[Byte], "ignited")

            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, "fuseState")
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, "charged")
            DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, "ignited")
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)

        if(explodes && living.isEntityAlive) {
            val fuseTime = entity.getInteger("fuseTime")
            var timeSinceIgnited = entity.getInteger("timeSinceIgnited")

            if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, "ignited")) != 0) {
                DataWatcherHelper.updateObject(living.getDataWatcher, "fuseState", 1.toByte.asInstanceOf[Byte])
            }

            val fuseState = DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, "fuseState").asInstanceOf[Byte].toInt

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

                    val explosionRadius = entity.getInteger("explosionRadius")

                    if(living.getDataWatcher.getWatchableObjectByte(DataWatcherHelper.getObjectId(living.getDataWatcher, "charged")) == 1) {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius*2, mobGriefing)
                    } else {
                        entity.getWorld.createExplosion(living, living.posX, living.posY, living.posZ, explosionRadius, mobGriefing)
                    }

                    living.setDead()
                }
            }

            entity.setInteger("timeSinceIgnited", timeSinceIgnited)
        }
    }

    override def interact(entity: IEntitySoulCustom, player: EntityPlayer): Boolean = {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)
        val flintAndSteelIgnites = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_FLINT_AND_STEEL_IGNITE)

        if(explodes && flintAndSteelIgnites) {
            val living = entity.asInstanceOf[EntityLiving]

            val stack = player.getCurrentEquippedItem

            if(stack != null && stack.getItem == Items.flint_and_steel) {
                entity.getWorld.playSoundEffect(living.posX + 0.5D, living.posY + 0.5D, living.posZ + 0.5D, "fire.ignite", 1.0F, entity.getRandom.nextFloat() * 0.4F + 0.8F)

                player.swingItem()

                if(!entity.getWorld.isRemote) {
                    DataWatcherHelper.updateObject(living.getDataWatcher, "ignited", 1.toByte.asInstanceOf[Byte])

                    stack.damageItem(1, player)
                    return true
                }
            }
        }
        false
    }

    override def onStruckByLightning(entity: IEntitySoulCustom, lightningBolt: EntityLightningBolt) {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_EXPLODES)
        val canBeCharged = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_CAN_BE_CHARGED)


        if(explodes && canBeCharged && !entity.getWorld.isRemote) {
            DataWatcherHelper.updateObject(living.getDataWatcher, "charged", 1.toByte.asInstanceOf[Byte])
        }
    }
}
