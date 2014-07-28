package seremis.geninfusion.api.magnet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.Level;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.magnet.tile.IMagnetConnector;
import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.api.util.Line3D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MagnetLinkHelper {

    public static MagnetLinkHelper instance = new MagnetLinkHelper();

    private List<MagnetLink> registeredMap = new CopyOnWriteArrayList<MagnetLink>();

    public void addLink(MagnetLink link) {
        if(link.connector1.getTile().getWorldObj().isRemote) {
            addClientLink(link);
            //System.out.println(link);
            return;
        }
        if(link != null && link.connector1 != null && link.connector2 != null && link.connector1 != link.connector2 && !doesLinkExist(link)) {
            if(checkConditions(link)) {
                registeredMap.add(link);
              //  PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketAddMagnetLink(link)));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void addClientLink(MagnetLink link) {
        if(link != null && link.connector1 != null && link.connector2 != null && link.connector1 != link.connector2 && !doesLinkExist(link)) {
            registeredMap.add(link);
            return;
        }
    }

    public void removeLink(MagnetLink link) {
        registeredMap.remove(link);
      //  PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLink(link)));
    }

    public void removeAllLinksFrom(IMagnetConnector connector) {
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector || link.connector2 == connector) {
                registeredMap.remove(link);
            }
        }
       // PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLinkConnector(connector)));
    }

    public List<MagnetLink> getLinksConnectedTo(IMagnetConnector connector) {
        List<MagnetLink> links = new ArrayList<MagnetLink>();

        Iterator<MagnetLink> it = getAllLinks().iterator();
        
        while(it.hasNext()) {
            MagnetLink link = it.next();
            
            if(new Coordinate3D(connector.getTile()).equals(new Coordinate3D(link.connector1.getTile()))) {
                links.add(link);
            } else if(new Coordinate3D(connector.getTile()).equals(new Coordinate3D(link.connector2.getTile()))) {
                links.add(link);
            } 
        }

        return links;
    }

    public List<IMagnetConnector> getConnectorsConnectedTo(IMagnetConnector connector) {
        List<IMagnetConnector> connList = new ArrayList<IMagnetConnector>();

        Iterator<MagnetLink> it = getAllLinks().iterator();

        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(new Coordinate3D(connector.getTile()).equals(new Coordinate3D(link.connector1.getTile()))) {
                 connList.add(link.connector2);
             } else if(new Coordinate3D(connector.getTile()).equals(new Coordinate3D(link.connector2.getTile()))) {
                 connList.add(link.connector1);
             } 
        }
        return connList;
    }

    public List<MagnetLink> getAllLinks() {
        return new ArrayList<MagnetLink>(registeredMap);
    }
    
    public List<MagnetLink> getAllLinksInRange(double x, double y, double z, double range) {
        List<MagnetLink> linksInRange = new ArrayList<MagnetLink>();
        for(MagnetLink link : getAllLinks()) {
            double dx = link.line.head.x - x;
            double dy = link.line.head.y - y;
            double dz = link.line.head.z - z;
            
            if(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) <= range) {
                linksInRange.add(link);
            }
        }
        return linksInRange;
    }

    public boolean checkConditions(MagnetLink link) {
        return checkConditions(link.connector1, link.connector2);
    }

    public boolean checkConditions(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2 || !(connector1 instanceof IMagnetConnector) || !(connector2 instanceof IMagnetConnector)) {
            return false;
        }
        Line3D line = new Line3D();
        line.setLineFromTile(connector1.getTile(), connector2.getTile());

        MagnetLink link = new MagnetLink(connector1, connector2);
        if(connector1.canConnect(link) && connector2.canConnect(link)) {
            if(line.getLength() <= connector1.getRange() && line.getLength() <= connector2.getRange()) {
                if(!doesLinkExist(connector1, connector2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doesLinkExist(MagnetLink link) {
        return doesLinkExist(link.connector1, link.connector2);
    }

    public boolean doesLinkExist(IMagnetConnector conn1, IMagnetConnector conn2) {
        if(conn1 == null || conn2 == null || conn1 == conn2) {
            return true;
        }

        Iterator<MagnetLink> it = getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(!areLinksEqual(link, new MagnetLink(conn1, conn2))) {
                return true;
            }
            if(link.connector1 == conn2 && link.connector2 == conn1) {
                return true;
            }
        }
        return false;
    }
    
    public boolean areLinksEqual(MagnetLink link1, MagnetLink link2) {
        if(link1.dimensionID != link2.dimensionID) {
            if(!Coordinate3D.equals(link1.connector1.getTile(), link2.connector1.getTile()) && !Coordinate3D.equals(link1.connector2.getTile(), link2.connector2.getTile())) {
                if(!Coordinate3D.equals(link1.connector1.getTile(), link2.connector2.getTile()) && !Coordinate3D.equals(link1.connector2.getTile(), link2.connector1.getTile())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public List<IMagnetConnector> getAllConnectors() {
        List<IMagnetConnector> connectors = new ArrayList<IMagnetConnector>();
        Iterator<MagnetLink> it = getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(!connectors.contains(link.connector1)) {
                connectors.add(link.connector1);
            }
            if(!connectors.contains(link.connector2)) {
                connectors.add(link.connector2);
            }
        }
        return connectors;
    }

    public void tick() {
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            link.divideHeat();
            if(!link.isConnectionPossible()) {
                registeredMap.remove(link);
              //  PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLink(link)));
            }
        }
    }

    public void updatePlayerWithNetworks(EntityPlayer player) {
      //  PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketResetMagnetLinks()), (Player) player);

        for(MagnetLink link : getAllLinks()) {
      //      PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketAddMagnetLink(link)), (Player) player);
        }        
        GeneticInfusion.logger.log(Level.INFO, "Sent list of links to player: " + player.getDisplayName());
    }

    public MagnetNetwork getNetworkFrom(MagnetLink link) {
        return getNetworkFrom(link.connector1);
    }

    public void reset() {
        instance = new MagnetLinkHelper();
        registeredMap.clear();
    }

    public MagnetNetwork getNetworkFrom(IMagnetConnector connector) {
        MagnetNetwork network = new MagnetNetwork();
        List<IMagnetConnector> connectors = new ArrayList<IMagnetConnector>();
        List<MagnetLink> links = new ArrayList<MagnetLink>();
        connectors.add(connector);
        
        int index = 0;
        while(index < connectors.size()) {
            List<IMagnetConnector> copy = new ArrayList<IMagnetConnector>(connectors);

            for(MagnetLink link : getLinksConnectedTo(copy)) {
                if(!connectors.contains(link.connector1)) {
                    connectors.add(link.connector1);
                }
                if(!connectors.contains(link.connector2)) {
                    connectors.add(link.connector2);
                }
                if(!links.contains(link)) {
                    links.add(link);
                }
            }
            index++;
        }

        for(MagnetLink link : links) {
            network.addLink(link);
        }

        return network;
    }

    private static List<MagnetLink> getLinksConnectedTo(List<IMagnetConnector> conns) {
        List<MagnetLink> links = new ArrayList<MagnetLink>();

        for(IMagnetConnector conn : conns) {
            for(MagnetLink link : MagnetLinkHelper.instance.getLinksConnectedTo(conn)) {
                if(!links.contains(link)) {
                    links.add(link);
                }
            }
        }
        return links;
    }
}