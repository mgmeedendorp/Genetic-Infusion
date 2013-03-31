package Seremis.SoulCraft.api.plasma;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;

public class PlasmaNetwork implements IPlasmaNetwork {
    
    private int networkID;
    
    private HashMap<IPlasmaConnector, PlasmaPacket> connected = new HashMap<IPlasmaConnector, PlasmaPacket>();
    
    public PlasmaNetwork(IPlasmaConnector... connected) {
        networkID = PlasmaRegistry.instance.getNextID();
        for(IPlasmaConnector tmp: connected) {
            addConnectorToNetwork(tmp, null);
        }
    }

    @Override
    public void addConnectorToNetwork(IPlasmaConnector connector, PlasmaPacket pack) {
        connector.setNetwork(this);
        this.connected.put(connector, pack);
    }
    
    @Override
    public void invalidate(IPlasmaConnector connector) {
        if(this.connected.get(connector) != null) {
            this.connected.remove(connector);
        }
    }

    @Override
    public PlasmaPacket getTotalPlasmaInNetwork() {
        PlasmaPacket totalPlasma = new PlasmaPacket();
        
        Iterator it = this.connected.entrySet().iterator();
        
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if(pairs != null) {
                IPlasmaConnector connector = (IPlasmaConnector)pairs.getKey();
                
                if(connector == null) {
                    it.remove();
                    continue;
                }
                if(connector.getTile().isInvalid()) {
                    it.remove();
                    continue;
                }
                PlasmaPacket pack = (PlasmaPacket)pairs.getValue();
                if(pairs.getKey() != null && pack != null && pack.getAmount() != 0) {
                    totalPlasma.mergePackets(pack);
                }
            }
        }
        return totalPlasma;
    }
    
    @Override
    public void dividePlasma() {
        PlasmaPacket totalPlasma = getTotalPlasmaInNetwork();
        if(totalPlasma == null || totalPlasma.getAmount() == 0) {
            return;
        }
          
        Iterator it = this.connected.entrySet().iterator();
        
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            
            if(pairs != null) {
                IPlasmaConnector connector = (IPlasmaConnector)pairs.getKey();
                PlasmaPacket pack = (PlasmaPacket)pairs.getValue();
                int plasmaPerConnector = totalPlasma.getAmount()/this.connected.size();
                addPlasmaToConnector(connector, pack.mergePackets(new PlasmaPacket(plasmaPerConnector)));
            }
        }    
    }

    @Override
    public void addPlasmaToConnector(IPlasmaConnector connector, PlasmaPacket pack) {
        if(connected.containsKey(connector) && pack != null) {
            connected.get(connector).mergePackets(pack);
        }
        
    }
    
    @Override
    public HashMap<IPlasmaConnector, PlasmaPacket> getConnectors() {
        return this.connected;
    }

    @Override
    public void merge(IPlasmaNetwork network) {
        this.connected.putAll(network.getConnectors());
    }
    
    @Override
    public String toString() {
        return "PlasmaNetwork[id: " + networkID + ", conductors: " + connected + "]";
    }

    @Override
    public int getNetworkId() {
        return networkID;
    }
}
