package seremis.geninfusion.event;

import seremis.geninfusion.entity.EntityTransporter;


public class TransporterEvent extends cpw.mods.fml.common.eventhandler.Event {

    public final EntityTransporter transporter;

    public TransporterEvent(EntityTransporter transporter) {
        this.transporter = transporter;
    }
}
