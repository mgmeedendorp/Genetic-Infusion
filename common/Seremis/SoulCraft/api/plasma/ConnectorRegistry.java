package Seremis.SoulCraft.api.plasma;

import java.util.WeakHashMap;

import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;

public class ConnectorRegistry implements IConnectorRegistry {

    public static ConnectorRegistry instance;
    
    private WeakHashMap<Integer, IPlasmaConnector> registeredConnectors = new WeakHashMap<Integer, IPlasmaConnector>();
    
    @Override
    public void registerPlasmaConnector(IPlasmaConnector connector, int id) {
        if(registeredConnectors.containsKey(id)) {
            return;
        }
        registeredConnectors.put(id, connector);
    }
    
    @Override
    public boolean isRegisteredConnector(IPlasmaConnector connector) {
        if(connector != null && connector instanceof IPlasmaConnector && registeredConnectors.containsValue(connector)) {
            return true;
        }
        return false;
    }
    
    @Override
    public IPlasmaConnector getConnectorFromID(int id) {
        return registeredConnectors.get(id);
    }
    
    @Override
    public int getNextID() {
        return registeredConnectors.size();
    }

}
