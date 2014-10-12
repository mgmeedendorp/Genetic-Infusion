package seremis.geninfusion.soul.traits;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.TraitDependencies;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.allele.AlleleBoolean;
import seremis.geninfusion.soul.allele.AlleleInteger;

import java.util.List;
import java.util.Random;

public class TraitMovement extends Trait {

	@Override
	@TraitDependencies(dependencies = "first")
    public void onUpdate(IEntitySoulCustom entity) {
        boolean isImmuneToFire = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value;
        int timeInPortalUntilTeleport = ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_TELEPORT_TIME_IN_PORTAL)).value;
        int portalCooldown = ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_PORTAL_COOLDOWN)).value;

        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");
        
        double motionX = entity.getDouble("motionX");
        double motionY = entity.getDouble("motionY");
        double motionZ = entity.getDouble("motionZ");
        
        float rotationPitch = entity.getFloat("rotationPitch");
        float rotationYaw = entity.getFloat("rotationYaw");

        Entity ridingEntity = entity.getWorld().getEntityByID(entity.getInteger("ridingEntityID"));
  
        boolean inPortal = entity.getBoolean("inPortal");
        int portalCounter = entity.getInteger("portalCounter");
        int timeUntilPortal = entity.getInteger("timeUntilPortal");
        
        boolean isSprinting = entity.getBoolean("isSprinting");
        boolean inWater = entity.getBoolean("inWater");
        float yOffset = entity.getFloat("yOffset");
        float width = entity.getFloat("width");
        float height = entity.getFloat("height");
        float fallDistance = entity.getFloat("fallDistance");
        

        entity.getWorld().theProfiler.startSection("entityBaseTick");
        
        if(ridingEntity != null && ridingEntity.isDead) {
            entity.setInteger("ridingEntityID", 0);
        }
        
        entity.setFloat("prevDistanceWalkedModified", entity.getFloat("distanceWalkedOnStepModified"));
        entity.setDouble("prevPosX", posX);
        entity.setDouble("prevPosY", posY);
        entity.setDouble("prevPosZ", posZ);
        entity.setFloat("prevRotationPitch", rotationPitch);
        entity.setFloat("prevRotationYaw", rotationYaw);
        entity.setFloat("prevRenderYawOffset", entity.getFloat("renderYawOffset"));
        entity.setFloat("prevRotationYawHead", entity.getFloat("rotationYawHead"));
        entity.setFloat("prevCameraPitch", entity.getFloat("cameraPitch"));

        if(CommonProxy.instance.isServerWorld(entity.getWorld()) && entity.getWorld() instanceof WorldServer) {
            entity.getWorld().theProfiler.startSection("portal");
            MinecraftServer minecraftserver = ((WorldServer) entity.getWorld()).func_73046_m();

            if(inPortal) {
                if(minecraftserver.getAllowNether()) {
                    if(ridingEntity == null) {
                        entity.setInteger("portalCounter", portalCounter + 1);

                        if(portalCounter + 1 >= timeInPortalUntilTeleport) {
                        	entity.setInteger("portalCounter", timeInPortalUntilTeleport);

                            entity.setInteger("timeUntilPortal", portalCooldown);

                            byte travelDimensionId;

                            if(entity.getWorld().provider.dimensionId == -1) {
                                travelDimensionId = 0;
                            } else {
                                travelDimensionId = -1;
                            }

                            UtilSoulEntity.travelToDimension(entity, travelDimensionId);
                        }
                    }

                    entity.setBoolean("inPortal", false);
                }
            } else {
                if(portalCounter > 0) {
                	entity.setInteger("portalCounter", portalCounter - 4);
                }

                if(portalCounter < 0) {
                	entity.setInteger("portalCounter", 0);
                }
            }

            if(timeUntilPortal > 0) {
            	entity.setInteger("timeUntilPortal", timeUntilPortal - 1);
            }

            entity.getWorld().theProfiler.endSection();
        }

        if(isSprinting && !inWater) {
            int j = MathHelper.floor_double(posX);
            int i = MathHelper.floor_double(posY - 0.20000000298023224D - yOffset);
            int k = MathHelper.floor_double(posZ);
            Block block = entity.getWorld().getBlock(j, i, k);

            if (block.getMaterial() != Material.air) {
            	Random rand = new Random();
                entity.getWorld().spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + entity.getWorld().getBlockMetadata(j, i, k), posX + ((double)rand.nextFloat() - 0.5D) * (double)width, entity.getBoundingBox().minY + 0.1D, posZ + ((double)rand.nextFloat() - 0.5D) * (double)width, -motionX * 4.0D, 1.5D, -motionZ * 4.0D);
            }
        }

        if(posY < -64.0D) {
            entity.setBoolean("isDead", true);
        }
        entity.getWorld().theProfiler.endSection();
        
        entity.getWorld().theProfiler.startSection("livingEntityBaseTick");

        if(!entity.getBoolean("isDead") && entity.getFloat("health") > 0.0F && UtilSoulEntity.isEntityInsideOpaqueBlock(entity)) {
            entity.attackEntityFrom(DamageSource.inWall, 1.0F);
        }

        if (isImmuneToFire || CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            UtilSoulEntity.extinguish(entity);
        }
        //TODO entity.updatePotionEffects();
        
        entity.getWorld().theProfiler.endSection();
        
        int jumpTicks = entity.getInteger("jumpTicks");
        int newPosRotationIncrements = entity.getInteger("newPosRotationIncrements");
        
        double newPosX = entity.getDouble("newPosX");
        double newPosY = entity.getDouble("newPosY");
        double newPosZ = entity.getDouble("newPosZ");
        
        double newRotationYaw = entity.getDouble("newRotationYaw");
        double newRotationPitch = entity.getDouble("newRotationPitch");
        
        if (jumpTicks > 0) {
            entity.setInteger("jumpTicks", --jumpTicks);
        }

        if (newPosRotationIncrements > 0) {
            double d0 = posX + (newPosX - posX) / (double)newPosRotationIncrements;
            double d1 = posY + (newPosY - posY) / (double)newPosRotationIncrements;
            double d2 = posZ + (newPosZ - posZ) / (double)newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(newRotationYaw - (double)rotationYaw);
            entity.setFloat("rotationYaw", (float)((double)rotationYaw + d3 / (double)newPosRotationIncrements));
            entity.setFloat("rotationPitch", (float)((double)rotationPitch + (newRotationPitch - (double)rotationPitch) / (double)newPosRotationIncrements));
            entity.setInteger("newPosRotationIncrements", newPosRotationIncrements-1);
            UtilSoulEntity.setPosition(entity, d0, d1, d2);
            UtilSoulEntity.setRotation(entity, rotationYaw, rotationPitch);
        } else if (CommonProxy.instance.isServerWorld(entity.getWorld())) {
        	entity.setDouble("motionX", entity.getDouble("motionX") * 0.98D);
        	entity.setDouble("motionY", entity.getDouble("motionY") * 0.98D);
        	entity.setDouble("motionZ", entity.getDouble("motionZ") * 0.98D);
        }
        
        motionX = entity.getDouble("motionX");
        motionY = entity.getDouble("motionY");
        motionZ = entity.getDouble("motionZ");
        
        posX = entity.getDouble("posX");
        posY = entity.getDouble("posY");
        posZ = entity.getDouble("posZ");
        
        rotationPitch = entity.getFloat("rotationPitch");
        rotationYaw = entity.getFloat("rotationYaw");

        if (Math.abs(motionX) < 0.005D) {
        	entity.setDouble("motionX", 0.0D);
        }

        if (Math.abs(motionY) < 0.005D) {
        	entity.setDouble("motionY", 0.0D);
        }

        if (Math.abs(motionZ) < 0.005D) {
        	entity.setDouble("motionZ", 0.0D);
        }

        entity.getWorld().theProfiler.startSection("ai");

        float health = entity.getFloat("health");

        //TODO make a more advanced check for this
        boolean hasAI = entity.getBoolean("aiEnabled");
        
        if (health <= 0.0F) {
            entity.setBoolean("isJumping", false);
            entity.setFloat("moveStrafing", 0.0F);
            entity.setFloat("moveForward", 0.0F);
            entity.setFloat("randomYawVelocity", 0.0F);
        } else if (CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            if (hasAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                this.updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else {
            	entity.getWorld().theProfiler.startSection("oldAi");
                entity.setInteger("entityAge", entity.getInteger("entityAge")+1);
                entity.getWorld().theProfiler.endSection();
                entity.setFloat("rotationYawHead", rotationYaw);
            }
        }

        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("jump");
        
        boolean isJumping = entity.getBoolean("isJumping");
        boolean isInWater = entity.getBoolean("inWater");
        boolean onGround = entity.getBoolean("onGround");

        if (isJumping) {
            if (!isInWater && !UtilSoulEntity.handleLavaMovement(entity)) {
                if (onGround && jumpTicks == 0) {
                    UtilSoulEntity.jump(entity);
                    entity.setInteger("jumpTicks", 10);
                }
            } else {
            	entity.setDouble("motionY", entity.getDouble("motionY") + 0.03999999910593033D);
            }
        } else {
        	entity.setInteger("jumpTicks", 0);
        }

        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("travel");
        entity.setFloat("moveStrafing", entity.getFloat("moveStrafing") * 0.98F);
        entity.setFloat("moveForward", entity.getFloat("moveForward") * 0.98F);
        entity.setFloat("randomYawVelocity", entity.getFloat("randomYawVelocity") * 0.9F);
        ((EntityLiving)entity).moveEntityWithHeading(entity.getFloat("moveStrafing"), entity.getFloat("moveForward"));
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("push");

        if (CommonProxy.instance.isServerWorld(entity.getWorld())) {
            this.collideWithNearbyEntities(entity);
        }

        entity.getWorld().theProfiler.endSection();
	}
	
	public void updateAITasks(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge")+1);
        entity.getWorld().theProfiler.startSection("checkDespawn");
        UtilSoulEntity.despawnEntity(entity);
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("sensing");
        entity.getEntitySenses().clearSensingCache();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("targetSelector");
        entity.getTargetTasks().onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("goalSelector");
        entity.getTasks().onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("navigation");
        entity.getNavigator().onUpdateNavigation();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("mob tick");
        entity.updateAITick();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("controls");
        entity.getWorld().theProfiler.startSection("move");
        entity.getMoveHelper().onUpdateMoveHelper();
        entity.getWorld().theProfiler.endStartSection("look");
        entity.getLookHelper().onUpdateLook();
        entity.getWorld().theProfiler.endStartSection("jump");
        entity.getJumpHelper().doJump();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.endSection();
    }
	
	public void collideWithNearbyEntities(IEntitySoulCustom entity) {
        List list = entity.getWorld().getEntitiesWithinAABBExcludingEntity((Entity) entity, entity.getBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty()) {
            for (Object aList : list) {
                Entity ent = (Entity) aList;

                if (ent.canBePushed()) {
                    ent.applyEntityCollision((Entity) entity);
                }
            }
        }
    }
}
