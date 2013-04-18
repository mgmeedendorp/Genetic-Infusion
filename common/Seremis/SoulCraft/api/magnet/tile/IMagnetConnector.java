package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;


public interface IMagnetConnector {
    
    TileEntity getTile();
    
    double getMaxConnectionLength();
    int getMaxLinks();
    
    boolean connect(ForgeDirection direction);
    void disconnect();
    
    void addLink(MagnetLink link);
    void removeLink(MagnetLink link);
    List<MagnetLink> getLinks();
}
