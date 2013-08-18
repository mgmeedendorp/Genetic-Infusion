package Seremis.SoulCraft.api.magnet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Line3D;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagnetLinkHelper {

    public static MagnetLinkHelper instance = new MagnetLinkHelper();
    
    private List<MagnetLink> registeredMap = new ArrayList<MagnetLink>();

    public boolean modification = false;
    
    public void addLink(MagnetLink link) {
        if(link != null && link.connector1 != null && link.connector2 != null && link.connector1 != link.connector2 && !doesLinkExist(link)) {
            if(checkConditions(link)) {
                modification = true;
                registeredMap.add(link);
                modification = false;
            }
        }
    }

    public void removeLink(MagnetLink link) {
        modification = true;
        registeredMap.remove(link);
        modification = false;
    }

    public void removeAllLinksFrom(IMagnetConnector connector) {
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector || link.connector2 == connector) {
                modification = true;
                it.remove();
                modification = false;
            }
        }
    }

    public List<MagnetLink> getLinksConnectedTo(IMagnetConnector connector) {
        List<MagnetLink> tempList = new ArrayList<MagnetLink>();
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector || link.connector2 == connector) {
                tempList.add(link);
            }
        }
        return tempList;
    }
    
    public List<IMagnetConnector> getConnectorsConnectedTo(IMagnetConnector connector) {
        List<IMagnetConnector> connList = new ArrayList<IMagnetConnector>();
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector || link.connector2 == connector) {
                if(link.connector1 == connector) {
                    connList.add(link.connector2);
                } else {
                    connList.add(link.connector1);
                }
            }
        }
        return connList;
    }

    public List<MagnetLink> getAllLinks() {
        return registeredMap;
    }

    public boolean checkConditions(MagnetLink link) {
        return checkConditions(link.connector1, link.connector2);
    }

    public boolean checkConditions(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2 || !(connector1 instanceof IMagnetConnector) || !(connector2 instanceof IMagnetConnector))
            return false;
        Line3D line = new Line3D();
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        if(connector1.canConnect() && connector2.canConnect()) {
            if(line.getLength() <= connector1.getRange() && line.getLength() <= connector2.getRange()) {
                if(!doesLinkExist(connector1, connector2)) {
                    if(connector1.connectToSide(line.getSide(connector1.getTile())) && connector2.connectToSide(line.getSide(connector2.getTile()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean doesLinkExist(MagnetLink link) {
        return doesLinkExist(link.connector1, link.connector2);
    }

    public boolean doesLinkExist(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2)
            return false;
        for(MagnetLink link : getAllLinks()) {
            if(link.connector1.equals(connector1) || link.connector2.equals(connector1)) {
                if(link.connector1.equals(connector2) || link.connector2.equals(connector2)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void renderLinks() {
        if(FMLClientHandler.instance().getClient().inGameHasFocus) {
            List<MagnetLink> links = getAllLinks();
            if(links != null && !modification) {
                for(MagnetLink link : links) {
                    link.render();
                }
            }
        }
    }
}
