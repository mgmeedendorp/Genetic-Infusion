package Seremis.SoulCraft.event;

import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.tileentity.TileStationController;

public class TransporterSendEvent extends TransporterEvent {

    public TileStationController to, from;

    public TransporterSendEvent(EntityTransporter transporter, TileStationController from, TileStationController to) {
        super(transporter);
        this.from = from;
        this.to = to;
    }
}
