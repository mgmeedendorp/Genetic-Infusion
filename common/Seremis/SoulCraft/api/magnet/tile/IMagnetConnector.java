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
    
    boolean connect(ForgeDirection direction);
    void invalidate();
    
    List<MagnetLink> getLinks();
    
    @SideOnly(Side.CLIENT)
    Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side);
}
