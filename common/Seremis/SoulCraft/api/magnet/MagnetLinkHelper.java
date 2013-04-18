package Seremis.SoulCraft.api.magnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.core.geometry.Line3D;

public class MagnetLinkHelper {
    
    public static MagnetLinkHelper instance = new MagnetLinkHelper();
    
    public HashMap<IMagnetConnector, List<MagnetLink>> registeredMap = new HashMap<IMagnetConnector, List<MagnetLink>>();
    
    public void addLink(IMagnetConnector connector, MagnetLink link) {
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
    
    public void removeLink(IMagnetConnector connector, MagnetLink link) {
        if(connector != null && link != null) {
            if(registeredMap.containsKey(connector) && registeredMap.get(connector).contains(link)) {
                registeredMap.get(connector).remove(link);
            }
        }
    }
    
    public void removeAllLinksFrom(IMagnetConnector connector) {
        for(MagnetLink link : registeredMap.get(connector)) {
            for(List<MagnetLink> tempList : registeredMap.values()) {
                tempList.remove(link);
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
    
    public boolean checkConditions(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null) return false;
        Line3D line = new Line3D();
        line.setLineFromTile(connector1.getTile(), connector2.getTile());
        if(line.getLength() <= connector1.getMaxConnectionLength() && line.getLength() <= connector2.getMaxConnectionLength()) {
            if(registeredMap.containsKey(connector1) && registeredMap.get(connector1).size() < connector1.getMaxLinks()) {
                if(registeredMap.containsKey(connector2) && registeredMap.get(connector2).size() < connector2.getMaxLinks()) {
                    if(connector1.connect(line.getSide(connector1.getTile())) && connector2.connect(line.getSide(connector2.getTile()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
