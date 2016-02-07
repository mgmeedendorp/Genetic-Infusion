package seremis.geninfusion.soul.traits

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{DamageSource, MathHelper}
import net.minecraft.world.WorldServer
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.lib.reflection.VariableLib
import seremis.geninfusion.api.lib.reflection.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper

class TraitMovement extends Trait {

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        living.prevSwingProgress = living.swingProgress

        entity.getWorld_I.theProfiler.startSection("entityBaseTick")

        if(living.ridingEntity != null && living.ridingEntity.isDead) {
            living.ridingEntity = null
        }

        living.prevDistanceWalkedModified = living.distanceWalkedModified
        living.prevPosX = living.posX
        living.prevPosY = living.posY
        living.prevPosZ = living.posZ
        living.prevRotationPitch = living.rotationPitch
        living.prevRotationYaw = living.rotationYaw

        if(!living.worldObj.isRemote && living.worldObj.isInstanceOf[WorldServer]) {
            living.worldObj.theProfiler.startSection("portal")

            val server = living.worldObj.asInstanceOf[WorldServer].func_73046_m()
            val maxInPortalTime = living.getMaxInPortalTime

            var portalCounter = entity.getInteger(EntityPortalCounter)

            if(entity.getBoolean(EntityInPortal)) {
                if(server.getAllowNether) {
                    portalCounter += 1

                    if(living.ridingEntity == null && portalCounter >= maxInPortalTime) {
                        portalCounter = maxInPortalTime
                        living.timeUntilPortal = living.getPortalCooldown
                        living.travelToDimension(if(living.worldObj.provider.dimensionId == -1) 0 else -1)
                    }
                    entity.setBoolean(EntityInPortal, false)
                }
            } else {
                if(portalCounter > 0) {
                    portalCounter -= 4
                }

                if(portalCounter < 0) {
                    portalCounter = 0
                }
            }
            entity.setInteger(EntityPortalCounter, portalCounter)

            if(living.timeUntilPortal > 0) {
                living.timeUntilPortal -= 1
            }

            living.worldObj.theProfiler.endSection()
        }

        if(living.isSprinting && !living.isInWater) {
            val posX = MathHelper.floor_double(living.posX)
            val posY = MathHelper.floor_double(living.posY - 0.20000000298023224D - living.yOffset.toDouble)
            val posZ = MathHelper.floor_double(living.posZ)
            val block = living.worldObj.getBlock(posX, posY, posZ)
            if(block.getMaterial != Material.air) {
                living.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + living.worldObj.getBlockMetadata(posX, posY, posZ), living.posX + (entity.getRandom_I.nextFloat().toDouble - 0.5D) * living.width.toDouble, living.boundingBox.minY + 0.1D, living.posZ + (entity.getRandom_I.nextFloat().toDouble - 0.5D) * living.width.toDouble, -living.motionX * 4.0D, 1.5D, -living.motionZ * 4.0D)
            }
        }

        living.handleWaterMovement()

        if(living.posY < -64.0D) {
            living.setDead()
        }

        entity.getWorld_I.theProfiler.endSection()

        living.prevSwingProgress = living.swingProgress

        living.worldObj.theProfiler.startSection("livingEntityBaseTick")

        if(living.isEntityAlive && living.isEntityInsideOpaqueBlock) {
            entity.attackEntityFrom_I(DamageSource.inWall, 1.0F)
        }

        if(living.isImmuneToFire || living.worldObj.isRemote) {
            living.extinguish()
        }

        entity.updatePotionEffects_I

        entity.setFloat(EntityPrevMovedDistance, entity.getFloat(EntityMovedDistance))
        living.prevRenderYawOffset = living.renderYawOffset
        living.prevRotationYawHead = living.rotationYawHead
        living.prevRotationYaw = living.rotationYaw
        living.prevRotationPitch = living.rotationPitch

        living.worldObj.theProfiler.endSection()

        if(entity.getInteger(EntityJumpTicks) > 0) {
            entity.setInteger(EntityJumpTicks, entity.getInteger(EntityJumpTicks) - 1)
        }

        var newPosRotationIncrements = entity.getInteger(EntityNewPosRotationIncrements)
        val newPosX = entity.getDouble(EntityNewPosX)
        val newPosY = entity.getDouble(EntityNewPosY)
        val newPosZ = entity.getDouble(EntityNewPosZ)

        if(newPosRotationIncrements > 0) {
            val posX = living.posX + (newPosX - living.posX) / newPosRotationIncrements.toDouble
            val posY = living.posY + (newPosY - living.posY) / newPosRotationIncrements.toDouble
            val posZ = living.posZ + (newPosZ - living.posZ) / newPosRotationIncrements.toDouble
            val deltaRotationYaw = MathHelper.wrapAngleTo180_double(entity.getDouble(EntityNewRotationYaw) - living.rotationYaw.toDouble)

            living.rotationYaw = (living.rotationYaw.toDouble + deltaRotationYaw / newPosRotationIncrements.toDouble).toFloat
            living.rotationPitch = (living.rotationPitch.toDouble + (entity.getFloat(EntityNewRotationPitch) - living.rotationPitch.toDouble) / newPosRotationIncrements.toDouble).toFloat
            newPosRotationIncrements -= 1
            living.setPosition(posX, posY, posZ)
            entity.setRotation_I(living.rotationYaw, living.rotationPitch)
        } else if(living.isServerWorld) {
            living.motionX *= 0.98D
            living.motionY *= 0.98D
            living.motionZ *= 0.98D
        }
        entity.setInteger(EntityNewPosRotationIncrements, newPosRotationIncrements)

        if(Math.abs(living.motionX) < 0.005D) {
            living.motionX = 0.0D
        }

        if(Math.abs(living.motionY) < 0.005D) {
            living.motionY = 0.0D
        }

        if(Math.abs(living.motionZ) < 0.005D) {
            living.motionZ = 0.0D
        }

        living.worldObj.theProfiler.startSection("jump")

        if(entity.getBoolean(EntityIsJumping)) {
            if(!living.isInWater && !living.handleLavaMovement()) {
                if(living.onGround && entity.getInteger(EntityJumpTicks) == 0) {
                    entity.jump_I
                    entity.setInteger(EntityJumpTicks, 10)
                }
            } else {
                living.motionY += 0.03999999910593033D
            }
        } else {
            entity.setInteger(EntityJumpTicks, 0)
        }

        living.worldObj.theProfiler.endSection()

        living.worldObj.theProfiler.startSection("travel")

        living.moveStrafing *= 0.98F
        living.moveForward *= 0.98F

        entity.setFloat(EntityRandomYawVelocity, entity.getFloat(EntityRandomYawVelocity) * 0.9F)
        living.moveEntityWithHeading(living.moveStrafing, living.moveForward)

        living.worldObj.theProfiler.endSection()

        living.worldObj.theProfiler.startSection("push")

        if(!living.worldObj.isRemote) {
            entity.collideWithNearbyEntities_I
        }

        living.worldObj.theProfiler.endSection()

        val canClimbWalls = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanClimbWalls)

        if(canClimbWalls && !entity.getWorld_I.isRemote) {
            DataWatcherHelper.updateObject(entity.getDataWatcher_I, DataWatcherClimbableWall, if(living.isCollidedHorizontally) 1.toByte else 0.toByte)
        }
    }

    override def entityInit(entity: IEntitySoulCustom) = {
        val canClimbWalls = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanClimbWalls)

        if(canClimbWalls && !entity.getWorld_I.isRemote) {
            DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, 0.toByte, DataWatcherClimbableWall)
        }
        DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, 0.toByte, DataWatcherSprinting)
        DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, 0.toByte, DataWatcherSneaking)
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) = {
        val canClimbWalls = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanClimbWalls)

        if(canClimbWalls) {
            DataWatcherHelper.writeObjectToNBT(compound, entity.getDataWatcher_I, DataWatcherClimbableWall)
        }
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) = {
        val canClimbWalls = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanClimbWalls)

        if(canClimbWalls) {
            DataWatcherHelper.readObjectFromNBT(compound, entity.getDataWatcher_I, DataWatcherClimbableWall)
        }
    }

    override def setInWeb(entity: IEntitySoulCustom) = {
        val affectedByWeb = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAffectedByWeb)

        if(affectedByWeb) {
            entity.setBoolean(EntityInWeb, true)
            entity.setFloat(EntityFallDistance, 0.0F)
        }
    }

    override def isOnLadder(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        val canClimbWalls = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanClimbWalls)

        if(!canClimbWalls || !DataWatcherHelper.isNameRegistered(entity.getDataWatcher_I, DataWatcherClimbableWall)) {
            val x = MathHelper.floor_double(living.posX)
            val y = MathHelper.floor_double(living.boundingBox.minY)
            val z = MathHelper.floor_double(living.posZ)

            ForgeHooks.isLivingOnLadder(entity.getWorld_I.getBlock(x, y, z), living.worldObj, x, y, z, living)
        } else {
             DataWatcherHelper.getObjectFromDataWatcher(entity.getDataWatcher_I, DataWatcherClimbableWall).asInstanceOf[Byte] == 1.toByte
        }
    }

    override def isSprinting(entity: IEntitySoulCustom): Boolean = {
        DataWatcherHelper.getObjectFromDataWatcher(entity.getDataWatcher_I, DataWatcherSprinting).asInstanceOf[Byte] == 1.toByte
    }

    override def setSprinting(entity: IEntitySoulCustom, sprinting: Boolean) {
        DataWatcherHelper.updateObject(entity.getDataWatcher_I, DataWatcherSprinting, if(sprinting) 1 else 0)
    }

    override def isSneaking(entity: IEntitySoulCustom): Boolean = {
        DataWatcherHelper.getObjectFromDataWatcher(entity.getDataWatcher_I, DataWatcherSneaking).asInstanceOf[Byte] == 1.toByte
    }

    override def setSneaking(entity: IEntitySoulCustom, sneaking: Boolean) {
        DataWatcherHelper.updateObject(entity.getDataWatcher_I, DataWatcherSneaking, if(sneaking) 1 else 0)
    }
}