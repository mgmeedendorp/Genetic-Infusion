package seremis.geninfusion.soul.traits;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

public class TraitMovement extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        EntityLiving living = (EntityLiving) entity;

        living.prevSwingProgress = living.swingProgress;

        entity.getWorld().theProfiler.startSection("entityBaseTick");

        if(living.ridingEntity != null && living.ridingEntity.isDead) {
            living.ridingEntity = null;
        }

        living.prevDistanceWalkedModified = living.distanceWalkedModified;
        living.prevPosX = living.posX;
        living.prevPosY = living.posY;
        living.prevPosZ = living.posZ;
        living.prevRotationPitch = living.rotationPitch;
        living.prevRotationYaw = living.rotationYaw;
        int i;

        if(!living.worldObj.isRemote && living.worldObj instanceof WorldServer) {
            living.worldObj.theProfiler.startSection("portal");
            MinecraftServer minecraftserver = ((WorldServer) living.worldObj).func_73046_m();
            i = living.getMaxInPortalTime();

            int portalCounter = entity.getInteger("portalCounter");

            if(entity.getBoolean("inPortal")) {
                if(minecraftserver.getAllowNether()) {
                    if(living.ridingEntity == null && portalCounter++ >= i) {
                        portalCounter = i;
                        living.timeUntilPortal = living.getPortalCooldown();
                        byte b0;

                        if(living.worldObj.provider.dimensionId == -1) {
                            b0 = 0;
                        } else {
                            b0 = -1;
                        }

                        living.travelToDimension(b0);
                    }

                    entity.setBoolean("inPortal", false);
                }
            } else {
                if(portalCounter > 0) {
                    portalCounter -= 4;
                }

                if(portalCounter < 0) {
                    portalCounter = 0;
                }
            }

            entity.setInteger("portalCounter", portalCounter);

            if(living.timeUntilPortal > 0) {
                --living.timeUntilPortal;
            }

            living.worldObj.theProfiler.endSection();
        }

        if(living.isSprinting() && !living.isInWater()) {
            int j = MathHelper.floor_double(living.posX);
            i = MathHelper.floor_double(living.posY - 0.20000000298023224D - (double) living.yOffset);
            int k = MathHelper.floor_double(living.posZ);
            Block block = living.worldObj.getBlock(j, i, k);

            if(block.getMaterial() != Material.air) {
                living.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + living.worldObj.getBlockMetadata(j, i, k), living.posX + ((double) entity.getRandom().nextFloat() - 0.5D) * (double) living.width, living.boundingBox.minY + 0.1D, living.posZ + ((double) entity.getRandom().nextFloat() - 0.5D) * (double) living.width, -living.motionX * 4.0D, 1.5D, -living.motionZ * 4.0D);
            }
        }

        living.handleWaterMovement();


        if(living.posY < -64.0D) {
            living.setDead();
        }
        entity.getWorld().theProfiler.endSection();

        living.prevSwingProgress = living.swingProgress;

        living.worldObj.theProfiler.startSection("livingEntityBaseTick");

        if(living.isEntityAlive() && living.isEntityInsideOpaqueBlock()) {
            entity.attackEntityFrom(DamageSource.inWall, 1.0F);
        }

        if(living.isImmuneToFire() || living.worldObj.isRemote) {
            living.extinguish();
        }

        entity.updatePotionEffects();

        entity.setFloat("field_70763_ax", entity.getFloat("field_70764_aw"));
        living.prevRenderYawOffset = living.renderYawOffset;
        living.prevRotationYawHead = living.rotationYawHead;
        living.prevRotationYaw = living.rotationYaw;
        living.prevRotationPitch = living.rotationPitch;
        living.worldObj.theProfiler.endSection();

        if(entity.getInteger("jumpTicks") > 0) {
            entity.setInteger("jumpTicks", entity.getInteger("jumpTicks") - 1);
        }

        int newPosRotationIncrements = entity.getInteger("newPosRotationIncrements");
        double newPosX = entity.getDouble("newPosX");
        double newPosY = entity.getDouble("newPosY");
        double newPosZ = entity.getDouble("newPosZ");

        if(newPosRotationIncrements > 0) {
            double d0 = living.posX + (newPosX - living.posX) / (double) newPosRotationIncrements;
            double d1 = living.posY + (newPosY - living.posY) / (double) newPosRotationIncrements;
            double d2 = living.posZ + (newPosZ - living.posZ) / (double) newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(entity.getDouble("newRotationYaw") - (double) living.rotationYaw);
            living.rotationYaw = (float) ((double) living.rotationYaw + d3 / (double) newPosRotationIncrements);
            living.rotationPitch = (float) ((double) living.rotationPitch + (entity.getDouble("newRotationPitch") - (double) living.rotationPitch) / (double) newPosRotationIncrements);
            --newPosRotationIncrements;
            living.setPosition(d0, d1, d2);
            entity.setRotation(living.rotationYaw, living.rotationPitch);
        } else if(!living.isClientWorld()) {
            living.motionX *= 0.98D;
            living.motionY *= 0.98D;
            living.motionZ *= 0.98D;
        }

        entity.setInteger("newPosRotationIncrements", newPosRotationIncrements);

        if(Math.abs(living.motionX) < 0.005D) {
            living.motionX = 0.0D;
        }

        if(Math.abs(living.motionY) < 0.005D) {
            living.motionY = 0.0D;
        }

        if(Math.abs(living.motionZ) < 0.005D) {
            living.motionZ = 0.0D;
        }

        living.worldObj.theProfiler.startSection("jump");

        if(entity.getBoolean("isJumping")) {
            if(!living.isInWater() && !living.handleLavaMovement()) {
                if(living.onGround && entity.getInteger("jumpTicks") == 0) {
                    entity.jump();
                    entity.setInteger("jumpTicks", 10);
                }
            } else {
                living.motionY += 0.03999999910593033D;
            }
        } else {
            entity.setInteger("jumpTicks", 0);
        }

        living.worldObj.theProfiler.endSection();
        living.worldObj.theProfiler.startSection("travel");
        living.moveStrafing *= 0.98F;
        living.moveForward *= 0.98F;
        entity.setFloat("randomYawVelocity", entity.getFloat("randomYawVelocity") * 0.9F);
        living.moveEntityWithHeading(living.moveStrafing, living.moveForward);
        living.worldObj.theProfiler.endSection();
        living.worldObj.theProfiler.startSection("push");

        if(!living.worldObj.isRemote) {
            entity.collideWithNearbyEntities();
        }

        living.worldObj.theProfiler.endSection();
    }
}
