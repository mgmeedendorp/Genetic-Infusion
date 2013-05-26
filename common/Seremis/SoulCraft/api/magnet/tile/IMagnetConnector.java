package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;


public interface IMagnetConnector {
    
    TileEntity getTile();
    
    double getRange();
    
    boolean connect(ForgeDirection direction);
    void invalidate();
    
    List<MagnetLink> getLinks();
}
