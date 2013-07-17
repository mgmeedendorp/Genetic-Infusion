package Seremis.SoulCraft.api.magnet;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.core.proxy.ClientProxy;
import Seremis.core.geometry.Coordinate3D;
import Seremis.core.geometry.Line3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagnetLink {

    public IMagnetConnector connector1;
    public IMagnetConnector connector2;

    public Line3D line = new Line3D();

    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this.connector1 = connector1;
        this.connector2 = connector2;
        MagnetLinkHelper.instance.addLink(this);
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
    }

    public boolean isConnectionPossible() {
        if(connector1.canConnect() && connector2.canConnect() && !connector1.getTile().isInvalid() && !connector2.getTile().isInvalid()) {
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        Coordinate3D from = new Coordinate3D(connector1.getTile());
        Coordinate3D target = new Coordinate3D(connector2.getTile());
        Line3D templine = new Line3D(from, target);
        Coordinate3D finalFrom = connector1.applyBeamRenderOffset(from, templine.getSide(connector1.getTile()));
        Coordinate3D finalTarget = connector2.applyBeamRenderOffset(target, templine.getSide(connector2.getTile()));
        ClientProxy.proxy.renderBeam(connector1.getTile().worldObj, finalFrom, finalTarget, connector1.getHeat(), connector2.getHeat());
    }
}
