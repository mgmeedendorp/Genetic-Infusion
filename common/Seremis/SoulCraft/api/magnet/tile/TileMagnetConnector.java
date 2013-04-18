package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;

public abstract class TileMagnetConnector extends TileEntity implements IMagnetConnector {

    public int maxConnectionLength;
    public int maxLinks;
    
    @Override
    public TileEntity getTile() {
        return this;
    }

    @Override
    public double getMaxConnectionLength() {
        return maxConnectionLength;
    }
    
    public int getMaxLinks() {
        return maxLinks;
    }

    @Override
    public abstract boolean connect(ForgeDirection direction);

    @Override
    public void addLink(MagnetLink link) {
        MagnetLinkHelper.instance.addLink(this, link);
    }
    
    @Override
    public void removeLink(MagnetLink link) {
        MagnetLinkHelper.instance.removeLink(this, link);
    }
    
    @Override
    public List<MagnetLink> getLinks() {
        return MagnetLinkHelper.instance.registeredMap.get(this);
    }
    
    @Override
    public void disconnect() {
        MagnetLinkHelper.instance.removeAllLinksFrom(this);
        MagnetLinkHelper.instance.registeredMap.remove(this);
    }
}
