package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

/**
 * @author Seremis
 */
public class TraitAttack extends Trait {

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        ((EntityLiving) entity).setLastAttacker(entityToAttack);
        return false;
    }
}
