package seremis.geninfusion.soul.traits

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLiving
import net.minecraft.util.{DamageSource, MathHelper}
import net.minecraft.world.WorldServer
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.lib.VariableLib._

class TraitMovement extends Trait {

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        living.prevSwingProgress = living.swingProgress

        entity.getWorld.theProfiler.startSection("entityBaseTick")

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
                living.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + living.worldObj.getBlockMetadata(posX, posY, posZ), living.posX + (entity.getRandom.nextFloat().toDouble - 0.5D) * living.width.toDouble, living.boundingBox.minY + 0.1D, living.posZ + (entity.getRandom.nextFloat().toDouble - 0.5D) * living.width.toDouble, -living.motionX * 4.0D, 1.5D, -living.motionZ * 4.0D)
            }
        }

        living.handleWaterMovement()

        if(living.posY < -64.0D) {
            living.setDead()
        }

        entity.getWorld.theProfiler.endSection()

        living.prevSwingProgress = living.swingProgress

        living.worldObj.theProfiler.startSection("livingEntityBaseTick")

        if(living.isEntityAlive && living.isEntityInsideOpaqueBlock) {
            entity.attackEntityFrom(DamageSource.inWall, 1.0F)
        }

        if(living.isImmuneToFire || living.worldObj.isRemote) {
            living.extinguish()
        }

        entity.updatePotionEffects

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
            entity.setRotation(living.rotationYaw, living.rotationPitch)
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
                    entity.jump
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
            entity.collideWithNearbyEntities
        }

        living.worldObj.theProfiler.endSection()
    }
}