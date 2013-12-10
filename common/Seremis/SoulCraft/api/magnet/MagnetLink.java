package Seremis.SoulCraft.api.magnet;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConsumer;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketHeatMagnetLink;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagnetLink {

    public IMagnetConnector connector1;
    public IMagnetConnector connector2;

    public int dimensionID;

    public Line3D line = new Line3D();

    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2, int dimensionID) {
        this.connector1 = connector1;
        this.connector2 = connector2;
        Line3D line2 = new Line3D().setLineFromTile(connector1.getTile(), connector2.getTile());
        line2.head.move(0.5, 0.5, 0.5);
        line2.tail.move(0.5, 0.5, 0.5);
        line.head = connector1.applyBeamRenderOffset(new Coordinate3D(connector1.getTile()), line2.getSide(new Coordinate3D(connector1.getTile())));
        line.tail = connector2.applyBeamRenderOffset(new Coordinate3D(connector2.getTile()), line2.getSide(new Coordinate3D(connector2.getTile())));
        this.dimensionID = dimensionID;
    }

    public MagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this.connector1 = connector1;
        this.connector2 = connector2;
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        this.dimensionID = connector1.getTile().worldObj.provider.dimensionId;
    }

    public boolean isConnectionPossible() {
        if(connector1.getTile() != null && connector2.getTile() != null && connector1.canConnect(this) && connector2.canConnect(this) && !connector1.getTile().isInvalid() && !connector2.getTile().isInvalid()) {
            return true;
        }
        return false;
    }

    public IMagnetConnector getOther(IMagnetConnector connector) {
        return connector == connector1 ? connector2 : connector == connector2 ? connector1 : null;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        Coordinate3D from = new Coordinate3D(connector1.getTile());
        Coordinate3D target = new Coordinate3D(connector2.getTile());
        
        Line3D templine = new Line3D(from, target);
        
        Coordinate3D finalFrom = connector1.applyBeamRenderOffset(from, templine.getSide(connector1.getTile()));
        Coordinate3D finalTarget = connector2.applyBeamRenderOffset(target, templine.getSide(connector2.getTile()));
        
        int r1 = connector1.getHeat()/4;
        int r2 = connector2.getHeat()/4;
        int g1 = 20 + 10/(connector1.getHeat()+1);
        int g2 = 20 + 10/(connector2.getHeat()+1);
        
        CommonProxy.proxy.renderBeam(connector1.getTile().worldObj, finalFrom, finalTarget, r1, g1, 0, r2, g2, 0);
    }

    public void divideHeat() {
        int method = getHeatDivideMethod();

        int heat1 = connector1.getHeat();
        int heat2 = connector2.getHeat();

        if(method == 0) {
            int changeHeat = Math.min(connector1.getHeatTransmissionSpeed(), connector2.getHeatTransmissionSpeed());
            if(changeHeat > Math.sqrt(heat1*heat1-heat2*heat2)) {
                changeHeat = (int) Math.sqrt(heat1*heat1-heat2*heat2);
            }
            if(heat1 < heat2) {
                int con1rem = connector1.warm(changeHeat);
                int con2rem = connector2.cool(changeHeat);
                connector1.warm(con2rem);
                connector2.warm(con1rem);
                PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketHeatMagnetLink(this)));
            } else if(heat1 > heat2) {
                int con1rem = connector1.cool(changeHeat);
                int con2rem = connector2.warm(changeHeat);
                connector1.warm(con2rem);
                connector2.warm(con1rem);
                PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketHeatMagnetLink(this)));
            }
        }
        if(method == 1) {
            if(connector1 instanceof IMagnetConsumer) {
                if(connector1.getHeat() != connector1.getMaxHeat() && connector2.getHeat() != 0) {
                    int changeHeat = Math.min(connector1.getHeatTransmissionSpeed(), connector2.getHeatTransmissionSpeed());
                   
                    if(changeHeat > connector1.getMaxHeat() - connector1.getHeat()) {
                        changeHeat = connector1.getMaxHeat() - connector1.getHeat();
                    }
                    
                    connector1.warm(changeHeat);
                    connector2.cool(changeHeat);
                    
                    PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketHeatMagnetLink(this)));
                }
            } else if(connector2 instanceof IMagnetConsumer) {
                if(connector2.getHeat() != connector2.getMaxHeat() && connector1.getHeat() != 0) {
                    int changeHeat = Math.min(connector1.getHeatTransmissionSpeed(), connector2.getHeatTransmissionSpeed());
                   
                    if(changeHeat > connector2.getMaxHeat() - connector2.getHeat()) {
                        changeHeat = connector2.getMaxHeat() - connector2.getHeat();
                    }
                    
                    connector1.cool(changeHeat);
                    connector2.warm(changeHeat);
                    
                    PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketHeatMagnetLink(this)));
                }
            }
        }
    }

    private int getHeatDivideMethod() {
        int heat1 = connector1.getHeat();
        int heat2 = connector2.getHeat();

        if(connector1 instanceof IMagnetConsumer || connector2 instanceof IMagnetConsumer) {
            return 1;
        }
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

    public boolean equals(MagnetLink link) {
        return connector1 == link.connector1 && connector2 == link.connector2 || connector2 == link.connector1 && connector1 == link.connector2;
    }

    @Override
    public String toString() {
        return "MagnetLink[" + "dimensionID: " + dimensionID + ", " + connector1.toString() + " " + connector2.toString() + "]";
    }
}