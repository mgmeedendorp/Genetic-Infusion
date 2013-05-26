package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public abstract class TileMagnetConnector extends TileEntity implements IMagnetConnector {
    
    public double range;
    private long lastUpdateTick;
    public long ticksBeforeUpdate = 10;
    
    public TileMagnetConnector(double range) {
        this.range = range;
        lastUpdateTick = 0;
    }
    
    //TileEntity//
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj)) return;
        long currentTime = worldObj.getWorldTime();
        if (lastUpdateTick + ticksBeforeUpdate <= currentTime) {
            lastUpdateTick = worldObj.getWorldTime();
            linkUpdate();
        }
    }
    
    public void linkUpdate() {
        World world = worldObj;
        for(int x=(int) (-10); x<=range; x++) {
            for(int y=(int) (-1*range); y<=range; y++) {
                for(int z=(int) (-1*range); z<=range; z++) {
                    TileEntity tile = world.getBlockTileEntity(xCoord + x, yCoord + y, zCoord + z);
                    if(tile != null && tile instanceof IMagnetConnector && tile != this && !MagnetLinkHelper.instance.doesLinkExist(this, (IMagnetConnector)tile)) {
                        MagnetLink link = new MagnetLink(this, (IMagnetConnector)tile);
                        if(MagnetLinkHelper.instance.checkConditions(link)) {
                            MagnetLinkHelper.instance.addLink(link);
                        }
                        System.out.println(link.toString());
                    }
                }
            }
        }
    }
    
    //IMagnetConnector//
    
    @Override
    public TileEntity getTile() {
        return this;
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
    }
}
