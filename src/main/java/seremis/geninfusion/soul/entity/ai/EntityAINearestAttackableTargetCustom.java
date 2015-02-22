package seremis.geninfusion.soul.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

import java.util.Collections;
import java.util.List;

public class EntityAINearestAttackableTargetCustom extends EntityAITargetCustom {

    private final Class targetClass;
    private final int targetChance;
    private final EntityAINearestAttackableTarget.Sorter sorter;
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;

    public EntityAINearestAttackableTargetCustom(IEntitySoulCustom entity, Class targetClass, int targetChance, boolean targetVisible) {
        this(entity, targetClass, targetChance, targetVisible, false);
    }

    public EntityAINearestAttackableTargetCustom(IEntitySoulCustom entity, Class targetClass, int targetChance, boolean targetVisible, boolean nearbyOnly) {
        this(entity, targetClass, targetChance, targetVisible, nearbyOnly, null);
    }

    public EntityAINearestAttackableTargetCustom(IEntitySoulCustom entity, Class targetClass, int targetChance, boolean targetVisible, boolean nearbyOnly, final IEntitySelector entitySelector) {
        super(entity, targetVisible, nearbyOnly);
        this.targetClass = targetClass;
        this.targetChance = targetChance;
        this.sorter = new EntityAINearestAttackableTarget.Sorter(living);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector() {
            public boolean isEntityApplicable(Entity entity) {
                return entity instanceof EntityLivingBase && !(entitySelector != null && !entitySelector.isEntityApplicable(entity)) && EntityAINearestAttackableTargetCustom.this.isSuitableTarget((EntityLivingBase) entity, false);
            }
        };
    }

    public boolean shouldExecute() {
        if(targetChance <= 0 || living.getRNG().nextInt(targetChance) == 0) {
            double followRange = getFollowRange();

            List list = living.worldObj.selectEntitiesWithinAABB(this.targetClass, this.living.boundingBox.expand(followRange, 4.0D, followRange), this.targetEntitySelector);
            Collections.sort(list, sorter);

            if(!list.isEmpty()) {
                targetEntity = (EntityLivingBase) list.get(0);
                return true;
            }
        }
        return false;
    }

    public void startExecuting() {
        living.setAttackTarget(targetEntity);
        super.startExecuting();
    }
}
