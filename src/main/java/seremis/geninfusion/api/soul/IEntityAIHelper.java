package seremis.geninfusion.api.soul;

import net.minecraft.entity.ai.EntityAIBase;

public interface IEntityAIHelper {

    /**
     * This gets an EntityAIBase that works with IEntitySoulCustom from the EntityAIHelper class
     * @param aiClass The class of the EntityAIBase that the method has to return
     * @param args The arguments for the EntityAIBase you want to be returned. Use an IEntitySoulCustom instead of the EntityLiving, EntityCreature, EntityMob, etc.. as the first argument.
     * @return The EntityAIBase requested.
     */
    public EntityAIBase getEntityAIForEntitySoulCustom(Class<EntityAIBase> aiClass, Object... args);
}
