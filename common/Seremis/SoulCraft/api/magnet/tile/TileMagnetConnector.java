package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.core.geometry.Coordinate3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileMagnetConnector extends TileEntity implements IMagnetConnector {

    public double range;
    private long lastUpdateTick;
    public long ticksBeforeUpdate = 20;

    public TileMagnetConnector(double range) {
        this.range = range;
        lastUpdateTick = 0;
    }

    // TileEntity//

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj))
            return;
        long currentTime = worldObj.getWorldTime();
        if(lastUpdateTick + ticksBeforeUpdate <= currentTime) {
            lastUpdateTick = worldObj.getWorldTime();
            linkUpdate();
        }
    }

    public void linkUpdate() {
        World world = worldObj;
        if(canConnect()) {
            for(int x = (int) (-1 * range); x <= range; x++) {
                for(int y = (int) (-1 * range); y <= range; y++) {
                    for(int z = (int) (-1 * range); z <= range; z++) {
                        TileEntity tile = world.getBlockTileEntity(xCoord + x, yCoord + y, zCoord + z);
                        if(tile != null && tile instanceof IMagnetConnector && tile != this) {
                            MagnetLink link = new MagnetLink(this, (IMagnetConnector) tile);
                            if(MagnetLinkHelper.instance.checkConditions(link)) {
                                MagnetLinkHelper.instance.addLink(link);
                            }
                        }
                    }
                }
            }
        } else {
            MagnetLinkHelper.instance.removeAllLinksFrom(this);
            return;
        }
        List<MagnetLink> links = this.getLinks();
        if(links != null) {
            for(MagnetLink link : links) {
                if(!link.isConnectionPossible()) {
                    MagnetLinkHelper.instance.removeLink(link);
                }
            }
        }
    }

    // IMagnetConnector//

    @Override
    public TileEntity getTile() {
        return this;
    }

    @Override
    public boolean canConnect() {
        return true;
    }

    @Override
    public double getRange() {
        return range;
    }

    @Override
    public List<MagnetLink> getLinks() {
        return MagnetLinkHelper.instance.registeredMap.get(this);
    }

    @Override
    public void invalidate() {
        MagnetLinkHelper.instance.removeAllLinksFrom(this);
        MagnetLinkHelper.instance.registeredMap.remove(this);
        super.invalidate();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side) {
        Coordinate3D centerPosition = new Coordinate3D(position.x + 0.5D, position.y + 0.5D, position.z + 0.5D);
        return centerPosition;
    }
    
    @Override
    public int getHeat() {
        return 2000;
    }
}
