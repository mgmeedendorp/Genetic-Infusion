package seremis.soulcraft.event;

import seremis.soulcraft.api.util.Coordinate3D;
import seremis.soulcraft.entity.EntityTransporter;

public class TransporterHitBlockEvent extends TransporterEvent {

    public Coordinate3D blockPos;

    public TransporterHitBlockEvent(EntityTransporter transporter, Coordinate3D blockPos) {
        super(transporter);
        this.blockPos = blockPos;
    }
}
