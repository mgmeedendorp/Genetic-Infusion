package Seremis.SoulCraft.api.plasma;

import java.util.HashMap;

import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;

public interface IPlasmaNetwork {

    public void addConnectorToNetwork(IPlasmaConnector connector, PlasmaPacket pack);
    
    public void invalidate(IPlasmaConnector connector);
    
    public void dividePlasma();
    
    public PlasmaPacket getTotalPlasmaInNetwork();
    
    public void addPlasmaToConnector(IPlasmaConnector connector, PlasmaPacket pack);
    
    public void merge(IPlasmaNetwork network);
    
    public HashMap<IPlasmaConnector, PlasmaPacket> getConnectors();
}
