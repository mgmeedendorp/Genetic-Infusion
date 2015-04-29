package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

import java.util.List;

public class EntityAIHurtByTargetCustom extends EntityAITargetCustom {

    public boolean entityCallsForHelp;
    public int lastRevengeTimer;

    public EntityAIHurtByTargetCustom(IEntitySoulCustom entity, boolean callForHelp) {
        super(entity, false);
        this.entityCallsForHelp = callForHelp;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        int revengeTimer = living.getRevengeTimer();
        return revengeTimer != lastRevengeTimer && isSuitableTarget(living.getAITarget(), false);
    }

    public void startExecuting() {
        living.setAttackTarget(living.getAITarget());
        lastRevengeTimer = living.getRevengeTimer();

        if(this.entityCallsForHelp) {
            double followRange = getFollowRange();
            List<EntityLiving> list = entity.getWorld().getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(living.posX, living.posY, living.posZ, living.posX + 1.0D, living.posY + 1.0D, living.posZ + 1.0D).expand(followRange, 10.0D, followRange));

            for(EntityLiving ent : list) {
                if(entity != ent && ent.getAttackTarget() == null && !ent.isOnSameTeam(living.getAITarget())) {
                    ent.setAttackTarget(living.getAITarget());
                }
            }
        }
        super.startExecuting();
    }

}
