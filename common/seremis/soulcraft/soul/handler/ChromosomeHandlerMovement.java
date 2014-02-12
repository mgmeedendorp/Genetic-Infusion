package seremis.soulcraft.soul.handler;

import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeHandlerMovement extends EntityEventHandler {

    public void onEntityUpdate(IEntitySoulCustom entity) {
        
        
        entity.collideWithNearbyEntities();
    }
}
