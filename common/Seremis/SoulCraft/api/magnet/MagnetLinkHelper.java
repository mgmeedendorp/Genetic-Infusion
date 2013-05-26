package Seremis.SoulCraft.api.magnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.core.geometry.Line3D;

public class MagnetLinkHelper {
    
    public static MagnetLinkHelper instance = new MagnetLinkHelper();
    
    public HashMap<IMagnetConnector, List<MagnetLink>> registeredMap = new HashMap<IMagnetConnector, List<MagnetLink>>();
    
    public void addLink(MagnetLink link) {
        addLink(link.connector1, link);
        addLink(link.connector2, link);
    }
    
    private void addLink(IMagnetConnector connector, MagnetLink link) {
        if(connector != null && link != null && registeredMap.containsKey(connector)) {
            if(!registeredMap.get(connector).contains(link)) {
                registeredMap.get(connector).add(link);
            }
        }
        if(connector != null && link != null && !registeredMap.containsKey(connector)) {
            List<MagnetLink> tempList = new ArrayList<MagnetLink>();
            tempList.add(link);
            registeredMap.put(connector, tempList);
        }
    }
    
    public void removeLink(MagnetLink link) {
        Set<IMagnetConnector> connectorList = registeredMap.keySet();
        Iterator<IMagnetConnector> it = connectorList.iterator();
        while(it.hasNext()) {
            IMagnetConnector connector = it.next();
            if(registeredMap.get(connector).contains(link)) {
                System.out.println(connector);
                it.remove();
            }
        }
    }
    
    public void removeAllLinksFrom(IMagnetConnector connector) {
        if(registeredMap.containsKey(connector)) {
            List<MagnetLink> links = connector.getLinks();
            List<MagnetLink> clone = new ArrayList<MagnetLink>();
            for(MagnetLink link : links) {
                clone.add(link);
            }
            Iterator<MagnetLink> it = clone.iterator();
            while(it.hasNext()) {
                removeLink(it.next());
            }
        }
    }
    
    public List<MagnetLink> getAllLinksFrom(IMagnetConnector connector) {
        return registeredMap.get(connector);
    }
    
    public List<MagnetLink> getAllLinks() {
        List<MagnetLink> tempList = new ArrayList<MagnetLink>();
        for(List<MagnetLink> link : registeredMap.values()) {
            tempList.addAll(link);
        }
        return tempList;
    }
    
    public boolean checkConditions(MagnetLink link) {
        return checkConditions(link.connector1, link.connector2);
    }
    
    public boolean checkConditions(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2) return false;
        Line3D line = new Line3D();
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        if(line.getLength() <= connector1.getRange() && line.getLength() <= connector2.getRange()) {
            if(!doesLinkExist(connector1, connector2)) {
                if(connector1.connect(line.getSide(connector1.getTile())) && connector2.connect(line.getSide(connector2.getTile()))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean doesLinkExist(MagnetLink link) {
        return doesLinkExist(link.connector1, link.connector2);
    }

    public boolean doesLinkExist(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2) return false;
        List<MagnetLink> links = getAllLinks();
        for(MagnetLink link : links) {
            if(link.connector1.equals(connector1) || link.connector2.equals(connector1)) {
                if(link.connector1.equals(connector2) || link.connector2.equals(connector2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
