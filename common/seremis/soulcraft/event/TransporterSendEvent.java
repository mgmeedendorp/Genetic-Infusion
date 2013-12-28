package seremis.soulcraft.event;

import seremis.soulcraft.entity.EntityTransporter;
import seremis.soulcraft.tileentity.TileStationController;

public class TransporterSendEvent extends TransporterEvent {

    public TileStationController to, from;

    public TransporterSendEvent(EntityTransporter transporter, TileStationController from, TileStationController to) {
        super(transporter);
        this.from = from;
        this.to = to;
    }
}
