package seremis.geninfusion.event;

import seremis.geninfusion.entity.EntityTransporter;
import seremis.geninfusion.tileentity.TileStationController;

public class TransporterArriveEvent extends TransporterEvent {

    public TileStationController to;

    public TransporterArriveEvent(EntityTransporter transporter, TileStationController to) {
        super(transporter);
        this.to = to;
    }

}
