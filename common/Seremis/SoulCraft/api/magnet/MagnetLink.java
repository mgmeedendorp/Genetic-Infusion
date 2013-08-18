package Seremis.SoulCraft.api.magnet;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.core.proxy.ClientProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagnetLink {

    public IMagnetConnector connector1;
    public IMagnetConnector connector2;

    public Line3D line = new Line3D();

    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this.connector1 = connector1;
        this.connector2 = connector2;
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
    
    public void divideHeat() {
        int method = getHeatDivideMethod();
        
        int heat1 = connector1.getHeat();
        int heat2 = connector2.getHeat();
        
        if(heat1 != heat2 && method == 0) {
            int averageHeat = heat1-heat2/2;
            int changeHeat = Math.abs(heat1-averageHeat)/100;
            if(heat1<heat2) {
                int con1rem = connector1.warm(changeHeat);
                int con2rem = connector2.cool(changeHeat);
                connector1.warm(con2rem);
                connector2.warm(con1rem);
            } else if(heat1>heat2) {
                int con1rem = connector1.cool(changeHeat);
                int con2rem = connector2.warm(changeHeat);
                connector1.warm(con2rem);
                connector2.warm(con1rem);
            }
        }
    }
    
    private int getHeatDivideMethod() {
        int heat1 = connector1.getHeat();
        int heat2 = connector2.getHeat();
        
        if(heat1 == connector1.getMaxHeat() && heat2 > heat1 || heat2 == connector2.getMaxHeat() && heat1 > heat2) {
            return -1;
        }
        if(heat1 == connector1.getMaxHeat() && !(heat2 > heat1)) {
            return 0;
        }
        if(heat1 == connector2.getMaxHeat() && !(heat1 > heat2)) {
            return 0;
        }
        if(heat1 == connector1.getMaxHeat() && heat2 == connector2.getMaxHeat()) {
            return -1;
        }
        return 0;
    }
}
