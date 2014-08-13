package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.Trait;
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
            //TODO if (entity.getBoolean("aiEnabled") && useNewAI) {
            if(useNewAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                this.updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else if(useOldAI) {
                entity.getWorld().theProfiler.startSection("oldAi");
                //TODO this
//                if(isCreature) {
//                    updateEntityCreatureActionState(entity);
//                } else {
                    updateEntityActionState(entity);
//                }
                entity.getWorld().theProfiler.endSection();
                entity.setVariable("rotationYawHead", entity.getPersistentFloat("rotationYaw"));
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

//    private void updateEntityCreatureActionState(IEntitySoulCustom entity) {
//        entity.getWorld().theProfiler.startSection("ai");
//
//        int fleeingTick = entity.getInteger("fleeingTick");
//
//        if(fleeingTick > 0 && --fleeingTick == 0) {
//            entity.setVariable("fleeingTick", entity.getInteger("fleeingTick") - 1);
//            IAttributeInstance iattributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
//            iattributeinstance.removeModifier(EntityCreature.field_110181_i);
//        }
//
//        entity.setVariable("hasAttacked", false);
//        float f4 = 16.0F;
//
//        Entity entityToAttack = entity.getWorld().getEntityByID(entity.getInteger("entityToAttack"));
//
//        if(entityToAttack == null) {
//            entityToAttack = this.findPlayerToAttack();
//
//            if(entityToAttack != null) {
//                this.pathToEntity = entity.getWorld().getPathEntityToEntity((Entity) entity, entityToAttack, f4, true, false, false, true);
//            }
//        } else if(entityToAttack.isEntityAlive()) {
//            float f = entityToAttack.getDistanceToEntity((Entity) entity);
//
//            if(this.canEntityBeSeen(entityToAttack)) {
//                entity.attackEntity(entityToAttack, f);
//            }
//        } else {
//            entityToAttack = null;
//        }
//
//        if(entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP) this.entityToAttack).theItemInWorldManager.isCreative()) {
//            entityToAttack = null;
//        }
//
//        entity.getWorld().theProfiler.endSection();
//
//        if(!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
//            this.pathToEntity = entity.getWorld().getPathEntityToEntity(this, entityToAttack, f4, true, false, false, true);
//        } else if(!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
//            this.updateWanderPath();
//        }
//
//        int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
//        boolean flag = this.isInWater();
//        boolean flag1 = UtilSoulEntity.handleLavaMovement(entity);
//        this.rotationPitch = 0.0F;
//
//        if(this.pathToEntity != null && entity.getRandom().nextInt(100) != 0) {
//            entity.getWorld().theProfiler.startSection("followpath");
//            Vec3 vec3 = this.pathToEntity.getPosition(this);
//            double d0 = (double) (this.width * 2.0F);
//
//            while(vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0) {
//                this.pathToEntity.incrementPathIndex();
//
//                if(this.pathToEntity.isFinished()) {
//                    vec3 = null;
//                    this.pathToEntity = null;
//                } else {
//                    vec3 = this.pathToEntity.getPosition(this);
//                }
//            }
//
//            this.isJumping = false;
//
//            if(vec3 != null) {
//                double d1 = vec3.xCoord - this.posX;
//                double d2 = vec3.zCoord - this.posZ;
//                double d3 = vec3.yCoord - (double) i;
//                float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
//                float f2 = MathHelper.wrapAngleTo180_float(f1 - this.rotationYaw);
//                this.moveForward = (float) entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
//
//                if(f2 > 30.0F) {
//                    f2 = 30.0F;
//                }
//
//                if(f2 < -30.0F) {
//                    f2 = -30.0F;
//                }
//
//                this.rotationYaw += f2;
//
//                if(this.hasAttacked && entityToAttack != null) {
//                    double d4 = entityToAttack.posX - this.posX;
//                    double d5 = entityToAttack.posZ - this.posZ;
//                    float f3 = this.rotationYaw;
//                    this.rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
//                    f2 = (f3 - this.rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
//                    this.moveStrafing = -MathHelper.sin(f2) * this.moveForward * 1.0F;
//                    this.moveForward = MathHelper.cos(f2) * this.moveForward * 1.0F;
//                }
//
//                if(d3 > 0.0D) {
//                    this.isJumping = true;
//                }
//            }
//
//            if(entityToAttack != null) {
//                UtilSoulEntity.faceEntity(entity, entityToAttack, 30.0F, 30.0F);
//            }
//
//            if(this.isCollidedHorizontally && !this.hasPath()) {
//                this.isJumping = true;
//            }
//
//            if(entity.getRandom().nextFloat() < 0.8F && (flag || flag1)) {
//                this.isJumping = true;
//            }
//
//            entity.getWorld().theProfiler.endSection();
//        } else {
//            updateEntityActionState(entity);
//            this.pathToEntity = null;
//        }
//    }

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
