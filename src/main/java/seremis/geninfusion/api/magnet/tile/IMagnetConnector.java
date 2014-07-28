package seremis.geninfusion.api.magnet.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import seremis.geninfusion.api.magnet.MagnetLink;
import seremis.geninfusion.api.util.Coordinate3D;

import java.util.List;

public interface IMagnetConnector {

    TileEntity getTile();

    double getRange();

    /**
     * This gets checked every tick, so use it for dynamic checking like
     * inventories.
     * 
     * @return
     */
    boolean canConnect(MagnetLink link);

    void invalidate();

    List<MagnetLink> getLinks();

    Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side);

    int getHeat();
    
    /**
     * Returns the speed this connector will transmit heat with.
     * @return heat per tick
     */
    int getHeatTransmissionSpeed();

    /**
     * Heats up the connector This shouln't be used by blocks that are not in
     * the network. (Network-internal)
     * 
     * @param heat
     * @return the heat that couldn't be applied
     */
    int warm(int heat);

    /**
     * Cools down the connector This shouln't be used by blocks that are not in
     * the network. (Network-internal)
     * 
     * @param heat
     * @return the 'coolness' left.
     */
    int cool(int heat);

    int getHeatLossPerTick();

    int getMaxHeat();
}
