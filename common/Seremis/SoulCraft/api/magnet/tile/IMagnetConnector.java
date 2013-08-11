package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.core.geometry.Coordinate3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IMagnetConnector {

    TileEntity getTile();

    double getRange();

    /**
     * This gets checked every 20 ticks, so use it for dynamic checking like
     * inventories.
     * 
     * @return
     */
    boolean canConnect();

    /**
     * Use this to determine if the link can be made to this side of the
     * connector. This is only called once.
     * 
     * @param direction
     */
    boolean connectToSide(ForgeDirection direction);
    
    boolean connectToConnector(IMagnetConnector connector);

    void invalidate();

    List<MagnetLink> getLinks();

    @SideOnly(Side.CLIENT)
    Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side);
    
    int getHeat();
    /**
     * Heats up the connector
     * This shouln't be used by blocks that are not in the network. (Network-internal)
     * @param heat
     * @return the heat that couldn't be applied
     */
    int warm(int heat);
    /**
     * Cools down the connector
     * This shouln't be used by blocks that are not in the network. (Network-internal)
     * @param heat
     * @return the 'coolness' left.
     */
    int cool(int heat);
    
    int getHeatLossPerTick();
    
    int getMaxHeat();
}
