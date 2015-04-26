package seremis.geninfusion.soul.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

import java.util.List;

public class EntityAIAvoidEntityCustom extends EntityAIBase {

    public final IEntitySelector entitySelector = new IEntitySelector() {
        public boolean isEntityApplicable(Entity entity)
        {
            return entity.isEntityAlive() && EntityAIAvoidEntityCustom.this.living.getEntitySenses().canSee(entity);
        }
    };

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public double farSpeed;
    public double nearSpeed;
    public Entity closestAvoidEntity;
    public float distanceFromEntity;

    public PathEntity avoidPath;
    public PathNavigate entityNavigator;

    private Class avoidEntityClass;

    public EntityAIAvoidEntityCustom(IEntitySoulCustom entity, Class avoidClass, float avoidDistance, double farSpeed, double nearSpeed) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
        this.avoidEntityClass = avoidClass;
        this.distanceFromEntity = avoidDistance;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.entityNavigator = living.getNavigator();
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.avoidEntityClass == EntityPlayer.class) {
            if (SoulHelper.geneRegistry().<Boolean>getValueFromAllele(entity, Genes.GENE_IS_TAMEABLE()) && entity.isTamed()) {
                return false;
            }

            closestAvoidEntity = entity.getWorld().getClosestPlayerToEntity(living, distanceFromEntity);

            if (closestAvoidEntity == null) {
                return false;
            }
        } else {
            List<Entity> entities = entity.getWorld().selectEntitiesWithinAABB(avoidEntityClass, living.boundingBox.expand(distanceFromEntity, 3.0D, distanceFromEntity), entitySelector);

            if (entities.isEmpty()) {
                return false;
            }

            this.closestAvoidEntity = entities.get(0);
        }

        Vec3 moveTarget = GIRandomPositionGenerator.findRandomTargetBlockAwayFrom(entity, 16, 7, Vec3.createVectorHelper(this.closestAvoidEntity.posX, this.closestAvoidEntity.posY, this.closestAvoidEntity.posZ));

        if (moveTarget == null) {
            return false;
        } else if (closestAvoidEntity.getDistanceSq(moveTarget.xCoord, moveTarget.yCoord, moveTarget.zCoord) < closestAvoidEntity.getDistanceSqToEntity(living)) {
            return false;
        } else {
            avoidPath = entityNavigator.getPathToXYZ(moveTarget.xCoord, moveTarget.yCoord, moveTarget.zCoord);
            return avoidPath != null && avoidPath.isDestinationSame(moveTarget);
        }
    }

    public boolean continueExecuting() {
        return !entityNavigator.noPath();
    }

    public void startExecuting() {
        entityNavigator.setPath(avoidPath, farSpeed);
    }

    public void resetTask() {
        closestAvoidEntity = null;
    }

    public void updateTask() {
        if (living.getDistanceSqToEntity(closestAvoidEntity) < 49.0D) {
            living.getNavigator().setSpeed(nearSpeed);
        } else {
            living.getNavigator().setSpeed(farSpeed);
        }
    }
}
