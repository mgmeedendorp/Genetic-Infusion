package Seremis.SoulCraft.api.magnet.tile;

public interface IMagnetHeater {
    /**
     * Import heat into the network
     * 
     * @param heat
     * @return the heat that couldn't be imported
     */
    public int importHeat(int heat);

    /**
     * Get heat out of the network
     * 
     * @param heat
     * @return the heat that couldn't be imported
     */
    public int exportHeat(int heat);
}
