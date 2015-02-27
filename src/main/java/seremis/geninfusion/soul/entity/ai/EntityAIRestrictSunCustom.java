package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

public class EntityAIRestrictSunCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public EntityAIRestrictSunCustom(IEntitySoulCustom entity) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
    }

    public boolean shouldExecute() {
        return this.entity.getWorld().isDaytime();
    }

    public void startExecuting() {
        this.living.getNavigator().setAvoidSun(true);
    }

    public void resetTask() {
        this.living.getNavigator().setAvoidSun(false);
    }
}
