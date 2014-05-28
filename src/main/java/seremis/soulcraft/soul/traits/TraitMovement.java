package seremis.soulcraft.soul.traits;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import seremis.soulcraft.api.soul.GeneRegistry;
import seremis.soulcraft.api.soul.IEntitySoulCustom;
import seremis.soulcraft.api.soul.lib.Genes;
import seremis.soulcraft.api.soul.util.UtilSoulEntity;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.Trait;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.entity.EntitySoulCustom;

public class TraitMovement extends Trait {

	@Override
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

        if(CommonProxy.proxy.isServerWorld(entity.getWorld()) && entity.getWorld() instanceof WorldServer) {
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

                            travelToDimension(entity, travelDimensionId);
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

        if (isImmuneToFire || CommonProxy.proxy.isRenderWorld(entity.getWorld())) {
            UtilSoulEntity.extinguish(entity);
        }
        
        
        //entity.updatePotionEffects();
        
        entity.getWorld().theProfiler.endSection();
        
        //entity.handleWaterMovement();

        //entity.collideWithNearbyEntities();
        // if(CommonProxy.proxy.isServerWorld(entity.getWorld())) {
//        entity.setMotion(motionX * 0.98, motionY * 0.98, motionZ * 0.98);
//        entity.addVelocity(0, -1, 0);
//        entity.moveEntity(motionX, motionY, motionZ);
        // }
	}
	
	public void travelToDimension(IEntitySoulCustom entity, int dimensionId) {
		boolean isDead = entity.getPersistentBoolean("isDead");
		
        if (CommonProxy.proxy.isServerWorld(entity.getWorld()) && !isDead) {
            entity.getWorld().theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int currentDimension = entity.getPersistentInteger("dimension");
            WorldServer worldserver = minecraftserver.worldServerForDimension(currentDimension);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            entity.setPersistentVariable("dimension", dimensionId);

            if (currentDimension == 1 && dimensionId == 1) {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                entity.setPersistentVariable("dimension", 0);
            }

            if (entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")).mountEntity((Entity)null);
            }

            if (entity.getWorld().getEntityByID(entity.getPersistentInteger("ridingEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getEntityId()).mountEntity((Entity)null);
            }
            entity.getWorld().theProfiler.startSection("reposition");            
            minecraftserver.getConfigurationManager().transferEntityToWorld((Entity) entity, currentDimension, worldserver, worldserver1);
            entity.getWorld().theProfiler.endStartSection("reloading");
            Entity ent = EntityList.createEntityByName(EntityList.getEntityString((Entity) entity), worldserver1);
            
            if (ent != null) {
                ent.copyDataFrom((Entity) entity, true);

                if (currentDimension == 1 && dimensionId == 1)
                {
                    ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                    chunkcoordinates.posY = entity.getWorld().getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    ent.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, ent.rotationYaw, ent.rotationPitch);
                }

                worldserver1.spawnEntityInWorld(ent);
            }

            entity.setPersistentVariable("isDead", true);
            entity.getWorld().theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            entity.getWorld().theProfiler.endSection();
        }
    }
}
