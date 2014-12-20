package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;

public class TraitAICreature extends Trait {

    @Override
    public void updateEntityActionState(IEntitySoulCustom entity) {
        if(entity instanceof EntityCreature) {
            entity.getWorld().theProfiler.startSection("ai");

            int fleeingTick = entity.getInteger("fleeingTick");

            if(fleeingTick > 0 && --fleeingTick == 0) {
                IAttributeInstance iattributeinstance = ((EntityLiving) entity).getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                iattributeinstance.removeModifier(EntityCreature.field_110181_i);
            }

            entity.setInteger("fleeingTick", fleeingTick);

            entity.setBoolean("hasAttacked", entity.isMovementCeased());
            float f4 = 16.0F;

            Entity entityToAttack = (Entity) entity.getObject("entityToAttack");

            if(entityToAttack == null) {
                entityToAttack = entity.findPlayerToAttack();

                if(entityToAttack != null) {
                    entity.setObject("pathToEntity", entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true));
                }
            } else if(entityToAttack.isEntityAlive()) {
                float f = entityToAttack.getDistanceToEntity((Entity) entity);

                if(((EntityLiving) entity).canEntityBeSeen(entityToAttack)) {
                    entity.attackEntity(entityToAttack, f);
                }
            } else {
                entityToAttack = null;
            }

            if(entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP) entityToAttack).theItemInWorldManager.isCreative()) {
                entityToAttack = null;
            }

            entity.setObject("entityToAttack", entityToAttack);

            entity.getWorld().theProfiler.endSection();

            if(!entity.getBoolean("hasAttacked") && entityToAttack != null && (entity.getObject("pathToEntity") == null || entity.getRandom().nextInt(20) == 0)) {
                entity.setObject("pathToEntity", entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true));
            } else if(!entity.getBoolean("hasAttacked") && (entity.getObject("pathToEntity") == null && entity.getRandom().nextInt(180) == 0 || entity.getRandom().nextInt(120) == 0 || entity.getInteger("fleeingTick") > 0) && entity.getInteger("entityAge") < 100) {
                entity.updateWanderPath();
            }

            int i = MathHelper.floor_double(entity.getBoundingBox().minY + 0.5D);
            boolean flag = entity.getBoolean("inWater");
            boolean flag1 = UtilSoulEntity.handleLavaMovement(entity);
            entity.setFloat("rotationPitch", 0.0F);

            PathEntity pathToEntity = (PathEntity) entity.getObject("pathToEntity");

            if(pathToEntity != null && entity.getRandom().nextInt(100) != 0) {
                entity.getWorld().theProfiler.startSection("followpath");
                Vec3 vec3 = pathToEntity.getPosition((Entity) entity);
                double d0 = (double) (entity.getFloat("width") * 2.0F);

                double posX = entity.getDouble("posX");
                double posZ = entity.getDouble("posZ");
                float rotationYaw = entity.getFloat("rotationYaw");
                float moveForward = entity.getFloat("moveForward");
                float moveStrafing = entity.getFloat("moveStrafing");

                while(vec3 != null && vec3.squareDistanceTo(posX, vec3.yCoord, posZ) < d0 * d0) {
                    pathToEntity.incrementPathIndex();

                    if(pathToEntity.isFinished()) {
                        vec3 = null;
                        pathToEntity = null;
                    } else {
                        vec3 = pathToEntity.getPosition((Entity) entity);
                    }
                }

                entity.setBoolean("isJumping", false);

                if(vec3 != null) {
                    double d1 = vec3.xCoord - posX;
                    double d2 = vec3.zCoord - posZ;
                    double d3 = vec3.yCoord - (double) i;
                    float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                    float f2 = MathHelper.wrapAngleTo180_float(f1 - rotationYaw);
                    moveForward = (float) ((EntityLiving) entity).getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();

                    if(f2 > 30.0F) {
                        f2 = 30.0F;
                    }

                    if(f2 < -30.0F) {
                        f2 = -30.0F;
                    }

                    rotationYaw += f2;

                    if(entity.getBoolean("hasAttacked") && entityToAttack != null) {
                        double d4 = entityToAttack.posX - posX;
                        double d5 = entityToAttack.posZ - posZ;
                        float f3 = rotationYaw;
                        rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                        f2 = (f3 - rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
                        moveStrafing = -MathHelper.sin(f2) * moveForward * 1.0F;
                        moveForward = MathHelper.cos(f2) * moveForward * 1.0F;
                    }

                    if(d3 > 0.0D) {
                        entity.setBoolean("isJumping", true);
                    }
                }

                if(entityToAttack != null) {
                    UtilSoulEntity.faceEntity(entity, entityToAttack, 30.0F, 30.0F);
                }

                if(entity.getBoolean("isCollidedHorizontally") && pathToEntity == null) {
                    entity.setBoolean("isJumping", true);
                }

                if(entity.getRandom().nextFloat() < 0.8F && (flag || flag1)) {
                    entity.setBoolean("isJumping", true);
                }

                entity.setFloat("rotationYaw", rotationYaw);
                entity.setFloat("moveForward", moveForward);
                entity.setFloat("moveStrafing", moveStrafing);

                entity.getWorld().theProfiler.endSection();
            } else {
                super.updateEntityActionState(entity);
                pathToEntity = null;
            }
            entity.setObject("pathToEntity", pathToEntity);
        } else {
            super.updateEntityActionState(entity);
        }
    }
}
