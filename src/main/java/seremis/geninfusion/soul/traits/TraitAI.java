package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
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
        boolean useNewAI = false;
        //((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_NEW_AI)).value;
        boolean useOldAI = true;
        //((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_OLD_AI)).value;
        boolean isCreature = true;
        //((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IS_CREATURE)).value;

        entity.getWorld().theProfiler.startSection("ai");

        if(entity.getBoolean("isDead")) {
            entity.setBoolean("isJumping", false);
            entity.setFloat("moveForward", 0.0F);
            entity.setFloat("moveStrafing", 0.0F);
            entity.setFloat("randomYawVelocity", 0.0F);
        } else if(CommonProxy.instance.isServerWorld(entity.getWorld())) {
            if(useNewAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                this.updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else if(useOldAI) {
                entity.getWorld().theProfiler.startSection("oldAi");
                if(isCreature) {
                //    updateEntityCreatureActionState(entity);
                } else {
                    updateEntityActionState(entity);
                }
                entity.getWorld().theProfiler.endSection();
                entity.setFloat("rotationYawHead", entity.getFloat("rotationYaw"));
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

    private void updateEntityCreatureActionState(IEntitySoulCustom entity) {
        entity.getWorld().theProfiler.startSection("ai");

        boolean hasAttacked = entity.getBoolean("hasAttacked");

        int fleeingTick = entity.getInteger("fleeingTick");
        int entityAge = entity.getInteger("entityAge");

        float rotationYaw = entity.getFloat("rotationYaw");
        float moveForward = entity.getFloat("moveForward");
        float moveStrafing = entity.getFloat("moveStrafing");

        double posX = entity.getDouble("posX");
        double posZ = entity.getDouble("posZ");

        PathEntity pathToEntity = null;
        Entity entityToAttack = entity.getWorld().getEntityByID(entity.getInteger("entityToAttack"));

        if(fleeingTick > 0 && --fleeingTick == 0) {
            fleeingTick--;
            IAttributeInstance iattributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
            iattributeinstance.removeModifier(EntityCreature.field_110181_i);
        }

        hasAttacked = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CEASE_AI_MOVEMENT)).value;
        float f4 = 16.0F;

        if(entityToAttack == null) {
            entityToAttack = findPlayerToAttack(entity);

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

//        if(entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP) entityToAttack).theItemInWorldManager.isCreative()) {
//            entityToAttack = null;
//        }

        entity.getWorld().theProfiler.endSection();

        if(!hasAttacked && entityToAttack != null && (pathToEntity == null || entity.getRandom().nextInt(20) == 0)) {
            pathToEntity = entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true);
        } else if(!hasAttacked && (pathToEntity == null && entity.getRandom().nextInt(180) == 0 || entity.getRandom().nextInt(120) == 0 || fleeingTick > 0) && entityAge < 100) {
            this.updateWanderPath(entity);
        }

        int i = MathHelper.floor_double(entity.getBoundingBox().minY + 0.5D);
        boolean flag = entity.getBoolean("inWater");
        boolean flag1 = UtilSoulEntity.handleLavaMovement(entity);
        entity.setFloat("rotationPitch", 0.0F);

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

            entity.setBoolean("isJumping", false);

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
                    entity.setBoolean("isJumping", true);
                }
            }

            if(entityToAttack != null) {
                entity.setFloat("rotationYaw", rotationYaw);
                entity.setFloat("moveForward", moveForward);
                entity.setFloat("moveStrafing", moveStrafing);

                UtilSoulEntity.faceEntity(entity, entityToAttack, 30.0F, 30.0F);

                rotationYaw = entity.getFloat("rotationYaw");
                moveForward = entity.getFloat("moveForward");
                moveStrafing = entity.getFloat("moveStrafing");
            }

            if(entity.getBoolean("isCollidedHorizontally") && pathToEntity == null) {
                entity.setBoolean("isJumping", true);
            }

            if(entity.getRandom().nextFloat() < 0.8F && (flag || flag1)) {
                entity.setBoolean("isJumping", true);
            }

            entity.getWorld().theProfiler.endSection();
        }

        entity.setInteger("fleeingTick", fleeingTick);
        entity.setInteger("entityAge", entityAge);
        entity.setFloat("rotationYaw", rotationYaw);
        entity.setFloat("moveForward", moveForward);
        entity.setFloat("moveStrafing", moveStrafing);
        entity.setDouble("posX", posX);
        entity.setDouble("posZ", posZ);
        entity.setInteger("entityToAttack", entityToAttack != null ? entityToAttack.getEntityId() : 0);

        System.out.println("moveForward " + moveForward);
        System.out.println("moveStrafing " + moveStrafing);

        UtilSoulEntity.writePathEntity(entity, pathToEntity, "pathToEntity");

        if(!flag3) {
            updateEntityActionState(entity);
            UtilSoulEntity.writePathEntity(entity, null, "pathToEntity");
        }
    }

    private void updateWanderPath(IEntitySoulCustom entity) {
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        entity.getWorld().theProfiler.startSection("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for(int l = 0; l < 10; ++l) {
            int i1 = MathHelper.floor_double(posX + (double) entity.getRandom().nextInt(13) - 6.0D);
            int j1 = MathHelper.floor_double(posY + (double) entity.getRandom().nextInt(7) - 3.0D);
            int k1 = MathHelper.floor_double(posZ + (double) entity.getRandom().nextInt(13) - 6.0D);
            float f1 = this.getBlockPathWeight(entity, i1, j1, k1);

            if(f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if(flag) {
            UtilSoulEntity.writePathEntity(entity, entity.getWorld().getEntityPathToXYZ((Entity) entity, i, j, k, 10.0F, true, false, false, true), "pathToEntity");
        }
        entity.getWorld().theProfiler.endSection();
    }

    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        //todo this properly
        return 0.5F - entity.getWorld().getLightBrightness(x, y, z);
    }

    private Entity findPlayerToAttack(IEntitySoulCustom entity) {
        //todo this properly
        return entity.getWorld().getClosestPlayerToEntity((Entity) entity, 50);
    }

    private void updateEntityActionState(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
        entity.setFloat("moveForward", 0.0F);
        entity.setFloat("moveStrafing", 0.0F);
        UtilSoulEntity.despawnEntity(entity);
        float f = 8.0F;

        if(entity.getRandom().nextFloat() < 0.02F) {
            entity.forceVariableSync(new String[]{"posX", "posY", "posZ"});
            EntityPlayer entityplayer = entity.getWorld().getClosestPlayerToEntity((Entity) entity, (double) f);

            if(entityplayer != null) {
                entity.setInteger("currentTarget", entityplayer.getEntityId());
                entity.setInteger("numTicksToChaseTarget", 10 + entity.getRandom().nextInt(20));
            } else {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }
        }

        Entity currentTarget = entity.getInteger("currentTarget") != 0 ? entity.getWorld().getEntityByID(entity.getInteger("currentTarget")) : null;

        if(currentTarget != null) {
            UtilSoulEntity.faceEntity(entity, currentTarget, 10.0F, (float) ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_VERTICAL_FACE_SPEED)).value);

            int numTicksToChaseTarget = entity.getInteger("numTicksToChaseTarget");

            entity.setInteger("numTicksToChaseTarget", numTicksToChaseTarget - 1);
            if(numTicksToChaseTarget - 1 <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity((Entity) entity) > (double) (f * f)) {
                entity.setInteger("currentTarget", 0);
            }
        } else {
            if(entity.getRandom().nextFloat() < 0.05F) {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }

            entity.setFloat("rotationYaw", entity.getFloat("rotationYaw") + entity.getFloat("randomYawVelocity"));
            entity.setFloat("rotationPitch", entity.getFloat("defaultPitch"));
        }

        boolean flag1 = entity.getBoolean("inWater");
        boolean flag = UtilSoulEntity.handleLavaMovement(entity);

        if(flag1 || flag) {
            entity.setBoolean("isJumping", entity.getRandom().nextFloat() < 0.8F);
        }
    }

    private void updateAITasks(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
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
