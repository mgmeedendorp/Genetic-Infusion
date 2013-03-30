package Seremis.SoulCraft.api.plasma;

import java.util.HashMap;

public class PlasmaRegistry implements IPlasmaRegistry {

    public static PlasmaRegistry instance = new PlasmaRegistry();
    
    private HashMap<Integer, IPlasmaNetwork> plasmaRegistry = new HashMap<Integer, IPlasmaNetwork>();
    
    @Override
    public void registerPlasmaNetwork(IPlasmaNetwork net,  int id) {
        plasmaRegistry.put(id, net);
    }
    
    public HashMap<Integer, IPlasmaNetwork> getRegisteredMap() {
        return plasmaRegistry;
    }
    
    @Override
    public IPlasmaNetwork getNetworkFromID(int id) {
        return plasmaRegistry.get(id);
    }
    
    @Override
    public int getNextID() {
        return plasmaRegistry.size();
    }
}
