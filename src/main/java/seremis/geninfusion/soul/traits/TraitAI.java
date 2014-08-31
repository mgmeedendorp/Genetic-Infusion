package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.allele.AlleleBoolean;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class TraitAI extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {

    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean useNewAI = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_NEW_AI)).value;
        boolean useOldAI = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_OLD_AI)).value;
        boolean isCreature = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IS_CREATURE)).value;

        entity.getWorld().theProfiler.startSection("ai");

        if(entity.getPersistentBoolean("isDead")) {
            entity.setVariable("isJumping", false);
            entity.setVariable("moveForward", 0.0F);
            entity.setVariable("moveStrafing", 0.0F);
            entity.setVariable("randomYawVelocity", 0.0F);
        } else if(CommonProxy.instance.isServerWorld(entity.getWorld())) {
            if(useNewAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                this.updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else if(useOldAI) {
                entity.getWorld().theProfiler.startSection("oldAi");
                //TODO this
                if(isCreature) {
                    updateEntityCreatureActionState(entity);
                } else {
                    updateEntityActionState(entity);
                }
                entity.getWorld().theProfiler.endSection();
                entity.setVariable("rotationYawHead", entity.getPersistentFloat("rotationYaw"));
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

    private void updateEntityCreatureActionState(IEntitySoulCustom entity) {
        entity.getWorld().theProfiler.startSection("ai");

        boolean hasAttacked = entity.getBoolean("hasAttacked");

        int fleeingTick = entity.getInteger("fleeingTick");
        int entityAge = entity.getInteger("entityAge");

        float rotationYaw = entity.getPersistentFloat("rotationYaw");
        float moveForward = entity.getFloat("moveForward");
        float moveStrafing = entity.getFloat("moveStrafing");

        double posX = entity.getPersistentDouble("posX");
        double posZ = entity.getPersistentDouble("posZ");

        PathEntity pathToEntity = null;
        Entity entityToAttack = entity.getWorld().getEntityByID(entity.getInteger("entityToAttack"));

        if(fleeingTick > 0 && --fleeingTick == 0) {
            fleeingTick--;
            IAttributeInstance iattributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            iattributeinstance.removeModifier(EntityCreature.field_110181_i);
        }

        hasAttacked = false;
        float f4 = 16.0F;

        if(entityToAttack == null) {
            entityToAttack = this.findPlayerToAttack();

            if(entityToAttack != null) {
                pathToEntity = entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true);
            }
        } else if(entityToAttack.isEntityAlive()) {
            float f = entityToAttack.getDistanceToEntity((Entity) entity);

            if(UtilSoulEntity.canEntityBeSeen(entity, entityToAttack)) {
                entity.attackEntity(entityToAttack, f);
            }
        } else {
            entityToAttack = null;
        }

        if(entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP) entityToAttack).theItemInWorldManager.isCreative()) {
            entityToAttack = null;
        }

        entity.getWorld().theProfiler.endSection();

        if(!hasAttacked && entityToAttack != null && (pathToEntity == null || entity.getRandom().nextInt(20) == 0)) {
            pathToEntity = entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true);
        } else if(!hasAttacked && (pathToEntity == null && entity.getRandom().nextInt(180) == 0 || entity.getRandom().nextInt(120) == 0 || fleeingTick > 0) && entityAge < 100) {
            this.updateWanderPath(entity);
        }

        int i = MathHelper.floor_double(entity.getBoundingBox().minY + 0.5D);
        boolean flag = entity.getBoolean("inWater");
        boolean flag1 = UtilSoulEntity.handleLavaMovement(entity);
        entity.setPersistentVariable("rotationPitch", 0.0F);

        boolean flag3 = pathToEntity != null && entity.getRandom().nextInt(100) != 0;

        if(flag3) {
            entity.getWorld().theProfiler.startSection("followpath");
            Vec3 vec3 = pathToEntity.getPosition((Entity) entity);
            double d0 = (double) (entity.getFloat("width") * 2.0F);

            while(vec3 != null && vec3.squareDistanceTo(posX, vec3.yCoord, posZ) < d0 * d0) {
                pathToEntity.incrementPathIndex();

                if(pathToEntity.isFinished()) {
                    vec3 = null;
                    pathToEntity = null;
                } else {
                    vec3 = pathToEntity.getPosition((Entity) entity);
                }
            }

            entity.setVariable("isJumping", false);

            if(vec3 != null) {
                double d1 = vec3.xCoord - posX;
                double d2 = vec3.zCoord - posZ;
                double d3 = vec3.yCoord - (double) i;

                float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f1 - rotationYaw);
                moveForward = (float) entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();

                if(f2 > 30.0F) {
                    f2 = 30.0F;
                }

                if(f2 < -30.0F) {
                    f2 = -30.0F;
                }

                rotationYaw += f2;

                if(hasAttacked && entityToAttack != null) {
                    double d4 = entityToAttack.posX - posX;
                    double d5 = entityToAttack.posZ - posZ;
                    float f3 = rotationYaw;
                    rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f2 = (f3 - rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
                    moveStrafing = -MathHelper.sin(f2) * moveForward * 1.0F;
                    moveForward = MathHelper.cos(f2) * moveForward * 1.0F;
                }

                if(d3 > 0.0D) {
                    entity.setVariable("isJumping", true);
                }
            }

            if(entityToAttack != null) {
                UtilSoulEntity.faceEntity(entity, entityToAttack, 30.0F, 30.0F);
            }

            if(entity.getBoolean("isCollidedHorizontally") && pathToEntity == null) {
                entity.setVariable("isJumping", true);
            }

            if(entity.getRandom().nextFloat() < 0.8F && (flag || flag1)) {
                entity.setVariable("isJumping", true);
            }

            entity.getWorld().theProfiler.endSection();
        }

        entity.setVariable("fleeingTick", fleeingTick);
        entity.setVariable("entityAge", entityAge);
        entity.setPersistentVariable("rotationYaw", rotationYaw);
        entity.setVariable("moveForward", moveForward);
        entity.setVariable("moveStrafing", moveStrafing);
        entity.setPersistentVariable("posX", posX);
        entity.setPersistentVariable("posZ", posZ);
        entity.setVariable("entityToAttack", entityToAttack != null ? entityToAttack.getEntityId() : 0);
        //TODO save pathEntity

        if(!flag3) {
            updateEntityActionState(entity);
            pathToEntity = null;
        }
    }

    private void updateWanderPath(IEntitySoulCustom entity) {

    }

    private Entity findPlayerToAttack() {
        return null;
    }

    private void updateEntityActionState(IEntitySoulCustom entity) {
        entity.setVariable("entityAge", entity.getInteger("entityAge") + 1);
        entity.setVariable("moveForward", 0.0F);
        entity.setVariable("moveStrafing", 0.0F);
        UtilSoulEntity.despawnEntity(entity);
        float f = 8.0F;

        if(entity.getRandom().nextFloat() < 0.02F) {
            EntityPlayer entityplayer = entity.getWorld().getClosestPlayerToEntity((Entity) entity, (double) f);

            if(entityplayer != null) {
                entity.setVariable("currentTarget", entityplayer.getEntityId());
                entity.setVariable("numTicksToChaseTarget", 10 + entity.getRandom().nextInt(20));
            } else {
                entity.setVariable("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }
        }

        Entity currentTarget = entity.getWorld().getEntityByID(entity.getInteger("currentTarget"));

        if(currentTarget != null) {
            UtilSoulEntity.faceEntity(entity, currentTarget, 10.0F, (float) ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_VERTICAL_FACE_SPEED)).value);

            int numTicksToChaseTarget = entity.getInteger("numTicksToChaseTarget");

            entity.setVariable("numTicksToChaseTarget", numTicksToChaseTarget - 1);
            if(numTicksToChaseTarget - 1 <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity((Entity) entity) > (double) (f * f)) {
                entity.setVariable("currentTarget", 0);
            }
        } else {
            if(entity.getRandom().nextFloat() < 0.05F) {
                entity.setVariable("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }

            entity.setPersistentVariable("rotationYaw", entity.getPersistentFloat("rotationYaw") + entity.getFloat("randomYawVelocity"));
            entity.setPersistentVariable("rotationPitch", entity.getFloat("defaultPitch"));
        }

        boolean flag1 = entity.getBoolean("inWater");
        boolean flag = UtilSoulEntity.handleLavaMovement(entity);

        if(flag1 || flag) {
            entity.setVariable("isJumping", entity.getRandom().nextFloat() < 0.8F);
        }
    }

    private void updateAITasks(IEntitySoulCustom entity) {
        entity.setVariable("entityAge", entity.getInteger("entityAge") + 1);
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
}
