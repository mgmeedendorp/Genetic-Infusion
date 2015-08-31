package seremis.geninfusion.soul.traits

import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.{Entity, EntityAgeable, EntityLiving, SharedMonsterAttributes}
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper

class TraitChild extends Trait {



    override def firstTick(entity: IEntitySoulCustom) {
        val isChild = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneIsChild)
        val childSpeedBoostModifier = SoulHelper.geneRegistry.getValueFromAllele[Double](entity, Genes.GeneChildSpeedModifier)

        if(canProcreate(entity) && !DataWatcherHelper.isNameRegistered(entity.getDataWatcher_I, DataWatcherGrowingAge))
            DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, 0, DataWatcherGrowingAge)

        val width: Float = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneWidth)
        val height: Float = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneHeight)

        entity.setFloat(EntityAdultWidth, width * (if(isChild) 2.0F else 1.0F))
        entity.setFloat(EntityAdultHeight, height * (if(isChild) 2.0F else 1.0F))

        entity.setScale_I(1.0F)

        entity.setSize_I(width, height)

        entity.setScaleForAge_I(isChild)

        if(childSpeedBoostModifier != 0.0D) {
            entity.setObject(EntityChildSpeedModifier, new AttributeModifier("childSpeedBoostModifier", childSpeedBoostModifier, 1))
            entity.asInstanceOf[EntityLiving].getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(entity.getObject(EntityChildSpeedModifier).asInstanceOf[AttributeModifier])
        }
    }

    override def isChild(entity: IEntitySoulCustom): Boolean = {
        if(canProcreate(entity)) {
            setChild(entity, DataWatcherHelper.getObjectFromDataWatcher(entity.getDataWatcher_I, DataWatcherGrowingAge).asInstanceOf[Int] < 0)
        }

        SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneIsChild)
    }

    def setChild(entity: IEntitySoulCustom, child: Boolean) {
        val living = entity.asInstanceOf[EntityLiving]

        if(entity.getBoolean(EntityPrevIsChild) != child) {
            val childSpeedBoostModifier = SoulHelper.geneRegistry.getValueFromAllele[Double](entity, Genes.GeneChildSpeedModifier)

            SoulHelper.geneRegistry.changeAlleleValue(entity, Genes.GeneIsChild, child, true)
            if(entity.getRandom_I.nextBoolean())
                SoulHelper.geneRegistry.changeAlleleValue(entity, Genes.GeneIsChild, child, false)

            if(childSpeedBoostModifier != 0.0D) {
                living.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(entity.getObject(EntityChildSpeedModifier).asInstanceOf[AttributeModifier])
                if(child) {
                    living.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(entity.getObject(EntityChildSpeedModifier).asInstanceOf[AttributeModifier])
                }
            }

            entity.setBoolean(EntityPrevIsChild, child)
        }
    }

    override def setScale(entity: IEntitySoulCustom, scale: Float) {
        entity.setSize_I(entity.getFloat(EntityAdultWidth) * scale, entity.getFloat(EntityAdultHeight) * scale)
    }

    override def setScaleForAge(entity: IEntitySoulCustom, isChild: Boolean) {
        entity.setScale_I(if(isChild) 0.5F else 1.0F)
    }

    override def setSize(entity: IEntitySoulCustom, width: Float, height: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val adultHeight = entity.getFloat(EntityAdultHeight)
        val adultWidth = entity.getFloat(EntityAdultWidth)

        val flag = adultHeight <= 0.0F && adultWidth <= 0.0F

        if(flag) {
            entity.setFloat(EntityAdultHeight, height)
            entity.setFloat(EntityAdultWidth, width)

            entity.setScale_I(1.0F)
        }


        if(width != living.width || height != living.height) {
            val prevWidth = living.width
            living.width = width
            living.height = height

            living.boundingBox.maxX = living.boundingBox.minX + width
            living.boundingBox.maxY = living.boundingBox.minY + height
            living.boundingBox.maxZ = living.boundingBox.minZ + width

            if(width > prevWidth && !entity.getBoolean(EntityFirstUpdate) && !living.worldObj.isRemote) {
                living.moveEntity(prevWidth - width, 0.0D, prevWidth - width)
            }
        }

        val widthModified = width % 2.0F

        if(widthModified < 0.375D) {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_1)
        } else if(widthModified < 0.75D) {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_2)
        } else if(widthModified < 1.0D) {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_3)
        } else if(widthModified < 1.375D) {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_4)
        } else if(widthModified < 1.75D) {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_5)
        } else {
            entity.setObject(EntityMyEntitySize, Entity.EnumEntitySize.SIZE_6)
        }
    }

    override def getGrowingAge(entity: IEntitySoulCustom): Int = if(canProcreate(entity)) DataWatcherHelper.getObjectFromDataWatcher(entity.getDataWatcher_I, DataWatcherGrowingAge).asInstanceOf[Int] else 0

    override def setGrowingAge(entity: IEntitySoulCustom, growingAge: Int) = {
        if(canProcreate(entity)) {
            DataWatcherHelper.updateObject(entity.getDataWatcher_I, DataWatcherGrowingAge, growingAge)
            entity.setScaleForAge_I(entity.asInstanceOf[EntityLiving].isChild)
        }
    }

    override def addGrowth(entity: IEntitySoulCustom, growth: Int) {
        var growingAge = entity.getGrowingAge_I
        growingAge += growth * 20

        if(growingAge > 0) {
            growingAge = 0
        }

        entity.setGrowingAge_I(growingAge)
    }

    def canProcreate(entity: IEntitySoulCustom): Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneCanProcreate)

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        if(canProcreate(entity) && DataWatcherHelper.isNameRegistered(entity.getDataWatcher_I, DataWatcherGrowingAge))
            DataWatcherHelper.writeObjectToNBT(compound, entity.getDataWatcher_I, DataWatcherGrowingAge)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        if(canProcreate(entity)) {
            if(!DataWatcherHelper.isNameRegistered(entity.getDataWatcher_I, DataWatcherGrowingAge)) DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, 0, DataWatcherGrowingAge)

            DataWatcherHelper.readObjectFromNBT(compound, entity.getDataWatcher_I, DataWatcherGrowingAge)
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        if(entity.getWorld_I.isRemote) {
            entity.setScaleForAge_I(entity.asInstanceOf[EntityLiving].isChild)
        } else {
            val growingAge = entity.getGrowingAge_I

            if(growingAge < 0) {
                entity.setGrowingAge_I(growingAge + 1)
            } else if(growingAge > 0) {
                entity.setGrowingAge_I(growingAge - 1)
            }
        }
    }

    override def createChild(entity: IEntitySoulCustom, ageable: EntityAgeable): Option[EntityAgeable] = {
        val entitySoul = entity.getSoul_I
        val ageableSoul = SoulHelper.standardSoulRegistry.getSoulForEntity(ageable)

        if(ageableSoul.nonEmpty) {
            val offspringSoul = SoulHelper.produceOffspring(entitySoul, ageableSoul.get)
            val offspring = SoulHelper.instanceHelper.getSoulEntityInstance(entity.getWorld_I, offspringSoul.get, 0, 0, 0)

            if(offspring.isInstanceOf[EntityAgeable])
                return Some(offspring.asInstanceOf[EntityAgeable])
        }
        None
    }
}
