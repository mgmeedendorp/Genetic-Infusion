package seremis.soulcraft.soul.traits;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import seremis.soulcraft.api.soul.GeneRegistry;
import seremis.soulcraft.api.soul.IEntitySoulCustom;
import seremis.soulcraft.api.soul.TraitDependencies;
import seremis.soulcraft.api.soul.lib.Genes;
import seremis.soulcraft.api.soul.util.UtilSoulEntity;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.Trait;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleInteger;

public class TraitMovement extends Trait {

	@Override
	@TraitDependencies(dependencies = "first")
    public void onUpdate(IEntitySoulCustom entity) {
        boolean isImmuneToFire = ((AlleleBoolean) GeneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value;
        int timeInPortalUntilTeleport = ((AlleleInteger) GeneRegistry.getActiveFor(entity, Genes.GENE_TELEPORT_TIME_IN_PORTAL)).value;
        int portalCooldown = ((AlleleInteger) GeneRegistry.getActiveFor(entity, Genes.GENE_PORTAL_COOLDOWN)).value;

        double posX = entity.getPersistentDouble("posX");
        double posY = entity.getPersistentDouble("posY");
        double posZ = entity.getPersistentDouble("posZ");
        
        double motionX = entity.getPersistentDouble("motionX");
        double motionY = entity.getPersistentDouble("motionY");
        double motionZ = entity.getPersistentDouble("motionZ");
        
        float rotationPitch = entity.getPersistentFloat("rotationPitch");
        float rotationYaw = entity.getPersistentFloat("rotationYaw");

        Entity ridingEntity = entity.getWorld().getEntityByID(entity.getPersistentInteger("ridingEntityID"));
  
        boolean inPortal = entity.getBoolean("inPortal");
        int portalCounter = entity.getInteger("portalCounter");
        int timeUntilPortal = entity.getPersistentInteger("timeUntilPortal");
        
        boolean isSprinting = entity.getBoolean("isSprinting");
        boolean inWater = entity.getBoolean("inWater");
        float yOffset = entity.getFloat("yOffset");
        float width = entity.getFloat("width");
        float height = entity.getFloat("height");
        float fallDistance = entity.getPersistentFloat("fallDistance");
        

        entity.getWorld().theProfiler.startSection("entityBaseTick");
        
        if(ridingEntity != null && ridingEntity.isDead) {
            entity.setPersistentVariable("ridingEntityID", 0);
        }
        
        entity.setVariable("prevDistanceWalkedModified", entity.getFloat("distanceWalkedOnStepModified"));
        entity.setVariable("prevPosX", posX);
        entity.setVariable("prevPosY", posY);
        entity.setVariable("prevPosZ", posZ);
        entity.setVariable("prevRotationPitch", rotationPitch);
        entity.setVariable("prevRotationYaw", rotationYaw);
        entity.setVariable("prevRenderYawOffset", entity.getFloat("renderYawOffset"));
        entity.setVariable("prevRotationYawHead", entity.getFloat("rotationYawHead"));
        entity.setVariable("prevCameraPitch", entity.getFloat("cameraPitch"));

        if(CommonProxy.instance.isServerWorld(entity.getWorld()) && entity.getWorld() instanceof WorldServer) {
            entity.getWorld().theProfiler.startSection("portal");
            MinecraftServer minecraftserver = ((WorldServer) entity.getWorld()).func_73046_m();

            if(inPortal) {
                if(minecraftserver.getAllowNether()) {
                    if(ridingEntity == null) {
                        entity.setVariable("portalCounter", portalCounter + 1);

                        if(portalCounter + 1 >= timeInPortalUntilTeleport) {
                        	entity.setVariable("portalCounter", timeInPortalUntilTeleport);

                            entity.setPersistentVariable("timeUntilPortal", portalCooldown);

                            byte travelDimensionId;

                            if(entity.getWorld().provider.dimensionId == -1) {
                                travelDimensionId = 0;
                            } else {
                                travelDimensionId = -1;
                            }

                            UtilSoulEntity.travelToDimension(entity, travelDimensionId);
                        }
                    }

                    entity.setVariable("inPortal", false);
                }
            } else {
                if(portalCounter > 0) {
                	entity.setVariable("portalCounter", portalCounter - 4);
                }

                if(portalCounter < 0) {
                	entity.setVariable("portalCounter", 0);
                }
            }

            if(timeUntilPortal > 0) {
            	entity.setPersistentVariable("timeUntilPortal", timeUntilPortal - 1);
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
            entity.setPersistentVariable("isDead", true);
        }
        entity.getWorld().theProfiler.endSection();
        
        entity.getWorld().theProfiler.startSection("livingEntityBaseTick");

        if (!entity.getPersistentBoolean("isDead") && entity.getFloat("health") > 0.0F && UtilSoulEntity.isEntityInsideOpaqueBlock(entity)) {
            entity.attackEntityFrom(DamageSource.inWall, 1.0F);
        }

        if (isImmuneToFire || CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            UtilSoulEntity.extinguish(entity);
        }
        //entity.updatePotionEffects();
        
        entity.getWorld().theProfiler.endSection();
        
        int jumpTicks = entity.getInteger("jumpTicks");
        int newPosRotationIncrements = entity.getInteger("newPosRotationIncrements");
        
        double newPosX = entity.getDouble("newPosX");
        double newPosY = entity.getDouble("newPosY");
        double newPosZ = entity.getDouble("newPosZ");
        
        double newRotationYaw = entity.getDouble("newRotationYaw");
        double newRotationPitch = entity.getDouble("newRotationPitch");
        
        if (jumpTicks > 0) {
            entity.setVariable("jumpTicks", --jumpTicks);
        }

        if (newPosRotationIncrements > 0) {
            double d0 = posX + (newPosX - posX) / (double)newPosRotationIncrements;
            double d1 = posY + (newPosY - posY) / (double)newPosRotationIncrements;
            double d2 = posZ + (newPosZ - posZ) / (double)newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(newRotationYaw - (double)rotationYaw);
            entity.setPersistentVariable("rotationYaw", (float)((double)rotationYaw + d3 / (double)newPosRotationIncrements));
            entity.setPersistentVariable("rotationPitch", (float)((double)rotationPitch + (newRotationPitch - (double)rotationPitch) / (double)newPosRotationIncrements));
            entity.setVariable("newPosRotationIncrements", newPosRotationIncrements-1);
            UtilSoulEntity.setPosition(entity, d0, d1, d2);
            UtilSoulEntity.setRotation(entity, rotationYaw, rotationPitch);
        } else if (CommonProxy.instance.isServerWorld(entity.getWorld())) {
        	entity.setPersistentVariable("motionX", entity.getPersistentDouble("motionX") * 0.98D);
        	entity.setPersistentVariable("motionY", entity.getPersistentDouble("motionY") * 0.98D);
        	entity.setPersistentVariable("motionZ", entity.getPersistentDouble("motionZ") * 0.98D);
        }
        
        motionX = entity.getPersistentDouble("motionX");
        motionY = entity.getPersistentDouble("motionY");
        motionZ = entity.getPersistentDouble("motionZ");
        
        posX = entity.getPersistentDouble("posX");
        posY = entity.getPersistentDouble("posY");
        posZ = entity.getPersistentDouble("posZ");
        
        rotationPitch = entity.getPersistentFloat("rotationPitch");
        rotationYaw = entity.getPersistentFloat("rotationYaw");

        if (Math.abs(motionX) < 0.005D) {
        	entity.setPersistentVariable("motionX", 0.0D);
        }

        if (Math.abs(motionY) < 0.005D) {
        	entity.setPersistentVariable("motionY", 0.0D);
        }

        if (Math.abs(motionZ) < 0.005D) {
        	entity.setPersistentVariable("motionZ", 0.0D);
        }

        entity.getWorld().theProfiler.startSection("ai");

        float health = entity.getPersistentFloat("health");
        
        //TODO check for this
        boolean hasAI = true;
        
        if (health <= 0.0F) {
            entity.setVariable("isJumping", false);
            entity.setVariable("moveStrafing", 0.0F);
            entity.setVariable("moveForward", 0.0F);
            entity.setVariable("randomYawVelocity", 0.0F);
        } else if (CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            if (hasAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                this.updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else {
            	entity.getWorld().theProfiler.startSection("oldAi");
                entity.setVariable("entityAge", entity.getInteger("entityAge")+1);
                entity.getWorld().theProfiler.endSection();
                entity.setVariable("rotationYawHead", rotationYaw);
            }
        }

        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("jump");
        
        boolean isJumping = entity.getBoolean("isJumping");
        boolean isInWater = entity.getBoolean("inWater");
        boolean onGround = entity.getPersistentBoolean("onGround");

        if (isJumping) {
            if (!isInWater && !UtilSoulEntity.handleLavaMovement(entity)) {
                if (onGround && jumpTicks == 0) {
                    UtilSoulEntity.jump(entity);
                    entity.setVariable("jumpTicks", 10);
                }
            } else {
            	entity.setPersistentVariable("motionY", entity.getPersistentDouble("motionY") + 0.03999999910593033D);
            }
        } else {
        	entity.setVariable("jumpTicks", 0);
        }

        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("travel");
        entity.setVariable("moveStrafing", entity.getFloat("moveStrafing") * 0.98F);
        entity.setVariable("moveForward", entity.getFloat("moveForward") * 0.98F);
        entity.setVariable("randomYawVelocity", entity.getFloat("randomYawVelocity") * 0.9F);
        ((EntityLiving)entity).moveEntityWithHeading(entity.getFloat("moveStrafing"), entity.getFloat("moveForward"));
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("push");

        if (CommonProxy.instance.isServerWorld(entity.getWorld())) {
            this.collideWithNearbyEntities(entity);
        }

        entity.getWorld().theProfiler.endSection();
	}
	
	public void updateAITasks(IEntitySoulCustom entity) {
        entity.setVariable("entityAge", entity.getInteger("entityAge")+1);
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
            for (int i = 0; i < list.size(); ++i) {
                Entity ent = (Entity)list.get(i);

                if (ent.canBePushed()) {
                    ent.applyEntityCollision((Entity) entity);
                }
            }
        }
    }
}
