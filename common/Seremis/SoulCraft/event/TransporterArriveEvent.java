package Seremis.SoulCraft.event;

import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.tileentity.TileStationController;

public class TransporterArriveEvent extends TransporterEvent {

    public TileStationController to;

    public TransporterArriveEvent(EntityTransporter transporter, TileStationController to) {
        super(transporter);
        this.to = to;
    }

}
