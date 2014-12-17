package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;

/**
 * @author Seremis
 */
public class TraitAI extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean useNewAI = false;
        //((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_NEW_AI)).value;
        boolean useOldAI = true;
        //((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_USE_OLD_AI)).value;

        entity.getWorld().theProfiler.startSection("ai");

        if(UtilSoulEntity.isMovementBlocked(entity)) {
            entity.setBoolean("isJumping", false);
            entity.setFloat("moveStrafing", 0.0F);
            entity.setFloat("moveForward", 0.0F);
            entity.setFloat("randomYawVelocity", 0.0F);
        } else if(!entity.getWorld().isRemote) {
            if(useNewAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else if(useOldAI) {
                entity.getWorld().theProfiler.startSection("oldAi");
                entity.updateEntityActionState();
                entity.getWorld().theProfiler.endSection();
                entity.setFloat("rotationYawHead", entity.getFloat("rotationYaw"));
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public void updateWanderPath(IEntitySoulCustom entity) {
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
            float f1 = entity.getBlockPathWeight(i1, j1, k1);

            if(f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if(flag) {
            entity.setObject("pathToEntity", entity.getWorld().getEntityPathToXYZ((Entity) entity, i, j, k, 10.0F, true, false, false, true));
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        //TODO this properly
        return 0.5F - entity.getWorld().getLightBrightness(x, y, z);
    }

    @Override
    public Entity findPlayerToAttack(IEntitySoulCustom entity) {
        return entity.getWorld().getClosestPlayerToEntity((Entity) entity, 50);
    }

    @Override
    public void updateEntityActionState(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
        entity.setFloat("moveForward", 0.0F);
        entity.setFloat("moveStrafing", 0.0F);
        UtilSoulEntity.despawnEntity(entity);
        float f = 8.0F;

        if(entity.getRandom().nextFloat() < 0.02F) {
            EntityPlayer entityplayer = entity.getWorld().getClosestPlayerToEntity((Entity) entity, (double) f);

            if(entityplayer != null) {
                entity.setObject("currentTarget", entityplayer);
                entity.setInteger("numTicksToChaseTarget", 10 + entity.getRandom().nextInt(20));
            } else {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }
        }

        EntityLiving currentTarget = (EntityLiving) entity.getObject("currentTarget");
        int numTicksToChaseTarget = entity.getInteger("numTicksToChaseTarget");

        if(currentTarget != null) {
            UtilSoulEntity.faceEntity(entity, currentTarget, 10.0F, (float) 40);

            if(numTicksToChaseTarget-- <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity((Entity) entity) > (double) (f * f)) {
                entity.setObject("currentTarget", null);
            }
        } else {
            if(entity.getRandom().nextFloat() < 0.05F) {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }

            entity.setFloat("rotationYaw", entity.getFloat("rotationYaw") + entity.getFloat("randomYawVelocity"));
            entity.setFloat("rotationPitch", entity.getFloat("defaultPitch"));
        }

        entity.setObject("currentTarget", currentTarget);
        entity.setInteger("numTicksToChaseTarget", numTicksToChaseTarget);

        boolean flag1 = entity.getBoolean("inWater");
        boolean flag = UtilSoulEntity.handleLavaMovement(entity);

        if(flag1 || flag) {
            entity.setBoolean("isJumping", entity.getRandom().nextFloat() < 0.8F);
        }
    }

    public void updateAITasks(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
        entity.getWorld().theProfiler.startSection("checkDespawn");
        UtilSoulEntity.despawnEntity(entity);
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("sensing");
        ((EntityLiving) entity).getEntitySenses().clearSensingCache();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("targetSelector");
        ((EntityAITasks) entity.getObject("targetTasks")).onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("goalSelector");
        ((EntityAITasks) entity.getObject("tasks")).onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("navigation");
        ((EntityLiving) entity).getNavigator().onUpdateNavigation();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("mob tick");
        entity.updateAITick();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("controls");
        entity.getWorld().theProfiler.startSection("move");
        ((EntityLiving) entity).getMoveHelper().onUpdateMoveHelper();
        entity.getWorld().theProfiler.endStartSection("look");
        ((EntityLiving) entity).getLookHelper().onUpdateLook();
        entity.getWorld().theProfiler.endStartSection("jump");
        ((EntityLiving) entity).getJumpHelper().doJump();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.endSection();
    }
}
