package seremis.soulcraft.soul.handler;

import java.util.Random;

import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class ChromosomeHandlerMovement extends EntityEventHandler {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean isImmuneToFire = ((AlleleBoolean) SoulHandler.getSoulFrom(entity).getChromosomes()[EnumChromosome.IMMUNE_TO_FIRE.ordinal()].getActive()).value;
        
        entity.getWorld().theProfiler.startSection("entityBaseTick");

        if(entity.getRidingEntity() != null && entity.getRidingEntity().isDead) {
            entity.setRidingEntity(null);
        }

        entity.setPrevDistanceWalkedModified(entity.getDistanceWalkedModified());
        entity.setPrevPosX(entity.getPosX());
        entity.setPrevPosY(entity.getPosY());
        entity.setPrevPosZ(entity.getPosZ());
        entity.setPrevRotationPitch(entity.getRotationPitch());
        entity.setPrevRotationYaw(entity.getRotationYaw());
        int timeInPortalUntilTeleport;

        if(CommonProxy.proxy.isServerWorld(entity.getWorld()) && entity.getWorld() instanceof WorldServer) {
            entity.getWorld().theProfiler.startSection("portal");
            MinecraftServer minecraftserver = ((WorldServer) entity.getWorld()).getMinecraftServer();
            timeInPortalUntilTeleport = ((AlleleInteger) SoulHandler.getChromosomeFrom(entity, EnumChromosome.TELEPORT_TIME_IN_PORTAL).getActive()).value;

            if(entity.getIsInPortal()) {
                if(minecraftserver.getAllowNether()) {
                    if(entity.getRidingEntity() == null) {
                        entity.setPortalTimer(entity.getPortalTimer() + 1);
                        if(entity.getPortalTimer() >= timeInPortalUntilTeleport) {
                            entity.setPortalTimer(timeInPortalUntilTeleport);

                            int maxCooldown = ((AlleleInteger) SoulHandler.getChromosomeFrom(entity, EnumChromosome.PORTAL_COOLDOWN).getActive()).value;

                            entity.setPortalCooldown(maxCooldown);

                            byte travelDimensionId;

                            if(entity.getWorld().provider.dimensionId == -1) {
                                travelDimensionId = 0;
                            } else {
                                travelDimensionId = -1;
                            }

                            entity.travelToDimension(travelDimensionId);
                        }
                    }

                    entity.setIsInPortal(false);
                }
            } else {
                if(entity.getPortalTimer() > 0) {
                    entity.setPortalTimer(entity.getPortalTimer() - 4);
                }

                if(entity.getPortalTimer() < 0) {
                    entity.setPortalTimer(0);
                }
            }

            if(entity.getPortalCooldown() > 0) {
                entity.setPortalCooldown(entity.getPortalCooldown() - 1);
            }

            entity.getWorld().theProfiler.endSection();
        }

        if(entity.isSprinting() && !entity.isInWater()) {
            int j = MathHelper.floor_double(entity.getPosX());
            int i = MathHelper.floor_double(entity.getPosY() - 0.20000000298023224D - entity.getYOffset());
            int k = MathHelper.floor_double(entity.getPosZ());
            int l = entity.getWorld().getBlockId(j, i, k);

            if(l > 0) {
                Random rand = new Random();
                entity.getWorld().spawnParticle("tilecrack_" + l + "_" + entity.getWorld().getBlockMetadata(j, i, k), entity.getPosX() + (rand.nextFloat() - 0.5D) * entity.getWidth(), entity.getBoundingBox().minY + 0.1D, entity.getPosZ() + (rand.nextFloat() - 0.5D) * entity.getWidth(), -entity.getMotionX() * 4.0D, 1.5D, -entity.getMotionZ() * 4.0D);
            }
        }

        if(entity.handleLavaMovement()) {
            if(!isImmuneToFire) {
                entity.attackEntityFrom(DamageSource.lava, 4.0F);
                entity.setFire(15);
            }
            entity.setFallDistance(entity.getFallDistance() * 0.5F);
        }

        if(entity.getPosY() < -64.0D) {
            entity.setDead();
        }
        entity.getWorld().theProfiler.endSection();
        
        entity.getWorld().theProfiler.startSection("livingEntityBaseTick");

        if (entity.isEntityAlive() && entity.isEntityInsideOpaqueBlock())
        {
            entity.attackEntityFrom(DamageSource.inWall, 1.0F);
        }

        if (isImmuneToFire || CommonProxy.proxy.isRenderWorld(entity.getWorld()))
        {
            entity.extinguish();
        }
        
        entity.setPrevCameraPitch(entity.getCameraPitch());
        
        entity.updatePotionEffects();
        
        entity.setPrevRenderYawOffset(entity.getRenderYawOffset());
        entity.setPrevRotationYawHead(entity.getRotationYawHead());
        entity.setPrevRotationYaw(entity.getRotationYaw());
        entity.setPrevRotationPitch(entity.getRotationPitch());
        
        entity.getWorld().theProfiler.endSection();
        

        entity.handleWaterMovement();

        entity.collideWithNearbyEntities();
        // if(CommonProxy.proxy.isServerWorld(entity.getWorld())) {
        entity.setMotion(entity.getMotionX() * 0.98, entity.getMotionY() * 0.98, entity.getMotionZ() * 0.98);
        entity.addVelocity(0, -1, 0);
        entity.moveEntity(entity.getMotionX(), entity.getMotionY(), entity.getMotionZ());
        // }
    }
}
