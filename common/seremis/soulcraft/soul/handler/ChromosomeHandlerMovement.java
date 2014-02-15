package seremis.soulcraft.soul.handler;

import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeHandlerMovement extends EntityEventHandler {

    public void onEntityUpdate(IEntitySoulCustom entity) {
        entity.collideWithNearbyEntities();
       // if(CommonProxy.proxy.isServerWorld(entity.getWorld())) {
            entity.setMotion(entity.getMotionX()*0.98, entity.getMotionY()*0.98, entity.getMotionZ()*0.98);
            entity.addVelocity(0, -1, 0);
            entity.moveEntity(entity.getMotionX(), entity.getMotionY(), entity.getMotionZ());
       // }
    }
}
