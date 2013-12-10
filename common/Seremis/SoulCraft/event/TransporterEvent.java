package Seremis.SoulCraft.event;

import net.minecraftforge.event.Event;
import Seremis.SoulCraft.entity.EntityTransporter;

public class TransporterEvent extends Event {

    public EntityTransporter transporter;

    public TransporterEvent(EntityTransporter transporter) {
        this.transporter = transporter;
    }
}
