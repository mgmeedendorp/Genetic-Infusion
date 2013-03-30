package Seremis.SoulCraft.api.plasma.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.plasma.IPlasmaNetwork;


public interface IPlasmaConnector {
    
    public TileEntity getTile();
    
    public IPlasmaNetwork getNetwork();
    
    public boolean connect(ForgeDirection side);

    public void setNetwork(IPlasmaNetwork network);

}
