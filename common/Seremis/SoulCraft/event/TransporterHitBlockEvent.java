package Seremis.SoulCraft.event;

import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.entity.EntityTransporter;

public class TransporterHitBlockEvent extends TransporterEvent {

    public Coordinate3D blockPos;

    public TransporterHitBlockEvent(EntityTransporter transporter, Coordinate3D blockPos) {
        super(transporter);
        this.blockPos = blockPos;
    }
}
