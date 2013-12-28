package seremis.soulcraft.event;

import seremis.soulcraft.entity.EntityTransporter;
import seremis.soulcraft.tileentity.TileStationController;

public class TransporterArriveEvent extends TransporterEvent {

    public TileStationController to;

    public TransporterArriveEvent(EntityTransporter transporter, TileStationController to) {
        super(transporter);
        this.to = to;
    }

}
