package seremis.geninfusion.event;

import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.entity.EntityTransporter;

public class TransporterHitBlockEvent extends TransporterEvent {

    public Coordinate3D blockPos;

    public TransporterHitBlockEvent(EntityTransporter transporter, Coordinate3D blockPos) {
        super(transporter);
        this.blockPos = blockPos;
    }
}
