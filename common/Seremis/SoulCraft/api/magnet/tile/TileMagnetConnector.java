package Seremis.SoulCraft.api.magnet.tile;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public abstract class TileMagnetConnector extends TileEntity implements IMagnetConnector {

    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 20;

    protected int heat = 0;

    // TileEntity//

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }
        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            lastUpdateTick = currTime;
            linkUpdate();
        }
        heatUpdate();
    }

    public void linkUpdate() {
        World world = worldObj;
        if(world.isRemote) {
            return;
        }
        for(int x = (int) (-1 * getRange()); x <= getRange(); x++) {
            for(int y = (int) (-1 * getRange()); y <= getRange(); y++) {
                for(int z = (int) (-1 * getRange()); z <= getRange(); z++) {
                    TileEntity tile = world.getBlockTileEntity(xCoord + x, yCoord + y, zCoord + z);
                    if(tile != null && tile instanceof IMagnetConnector && tile != this) {
                        MagnetLink link = new MagnetLink(this, (IMagnetConnector) tile);
                        MagnetLinkHelper.instance.addLink(link);
                    }
                }
            }
        }
    }

    public void heatUpdate() {
        if(heat > 0) {
            cool(getHeatLossPerTick());
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Heat", heat);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        heat = compound.getInteger("Heat");
    }

    // IMagnetConnector//

    @Override
    public TileEntity getTile() {
        return this;
    }

    @Override
    public boolean canConnect(MagnetLink link) {
        return true;
    }

    @Override
    public List<MagnetLink> getLinks() {
        return MagnetLinkHelper.instance.getLinksConnectedTo(this);
    }

    @Override
    public void invalidate() {
        MagnetLinkHelper.instance.removeAllLinksFrom(this);
        super.invalidate();
    }

    @Override
    public Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side) {
        Coordinate3D centerPosition = new Coordinate3D(position.x + 0.5D, position.y + 0.5D, position.z + 0.5D);
        return centerPosition;
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public int warm(int heat) {
        int remainingHeat = 0;
        this.heat += heat;
        if(this.heat > getMaxHeat()) {
            remainingHeat = this.heat - getMaxHeat();
            this.heat = getMaxHeat();
        }
        return remainingHeat;
    }

    @Override
    public int cool(int heat) {
        int remainingHeat = 0;
        this.heat -= heat;
        if(this.heat < 0) {
            remainingHeat = this.heat*-1;
            this.heat = 0;
        }
        return remainingHeat;
    }

    @Override
    public String toString() {
        return "TileMagnetConnector[name: " + getBlockType().getUnlocalizedName() + ", x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + ", heat: " + heat + "]";
    }
}
