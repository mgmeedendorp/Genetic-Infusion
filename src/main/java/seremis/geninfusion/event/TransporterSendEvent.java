package seremis.geninfusion.event;

import seremis.geninfusion.entity.EntityTransporter;
import seremis.geninfusion.tileentity.TileStationController;

public class TransporterSendEvent extends TransporterEvent {

    public TileStationController to, from;

    public TransporterSendEvent(EntityTransporter transporter, TileStationController from, TileStationController to) {
        super(transporter);
        this.from = from;
        this.to = to;
    }
}
