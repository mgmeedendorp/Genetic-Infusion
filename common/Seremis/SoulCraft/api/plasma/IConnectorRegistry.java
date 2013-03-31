package Seremis.SoulCraft.api.plasma;

import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;

public interface IConnectorRegistry {
    
    public void registerPlasmaConnector(IPlasmaConnector connector, int id);

    public boolean isRegisteredConnector(IPlasmaConnector connector);

    public IPlasmaConnector getConnectorFromID(int id);
    
    public int getNextID();
}
