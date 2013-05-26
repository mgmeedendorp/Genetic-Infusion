package Seremis.SoulCraft.api.magnet;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.core.geometry.Line3D;

public class MagnetLink {
    
    public IMagnetConnector connector1;
    public IMagnetConnector connector2;
    
    public Line3D line = new Line3D();
    public boolean shouldRender;
    
    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this.connector1 = connector1;
        this.connector2 = connector2;
        MagnetLinkHelper.instance.addLink(this);
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        System.out.println(connector1 + " " + connector2 + " " + line.toString());
    }
}
