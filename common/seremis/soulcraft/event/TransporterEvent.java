package seremis.soulcraft.event;

import net.minecraftforge.event.Event;
import seremis.soulcraft.entity.EntityTransporter;

public class TransporterEvent extends Event {

    public final EntityTransporter transporter;

    public TransporterEvent(EntityTransporter transporter) {
        this.transporter = transporter;
    }
}
