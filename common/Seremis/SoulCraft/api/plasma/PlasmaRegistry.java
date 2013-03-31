package Seremis.SoulCraft.api.plasma;

import java.util.WeakHashMap;

public class PlasmaRegistry implements IPlasmaRegistry {

    public static PlasmaRegistry instance = new PlasmaRegistry();
    
    private WeakHashMap<Integer, IPlasmaNetwork> plasmaRegistry = new WeakHashMap<Integer, IPlasmaNetwork>();
    
    @Override
    public void registerPlasmaNetwork(IPlasmaNetwork net,  int id) {
        plasmaRegistry.put(id, net);
    }
    
    public WeakHashMap<Integer, IPlasmaNetwork> getRegisteredMap() {
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
