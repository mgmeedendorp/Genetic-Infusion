package Seremis.SoulCraft.api.plasma;

public interface IPlasmaRegistry {
    
    public void registerPlasmaNetwork(IPlasmaNetwork net, int id);

    public IPlasmaNetwork getNetworkFromID(int id);

    public int getNextID();
}
