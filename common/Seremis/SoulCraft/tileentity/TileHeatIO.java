package Seremis.SoulCraft.tileentity;

import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.tile.TileMagnetHeater;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class TileHeatIO extends TileMagnetHeater {

    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 20;

    @Override
    public boolean connectToSide(ForgeDirection direction) {
        return true;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj))
            return;
        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            lastUpdateTick = currTime;
            heatUpdate();
        }
    }
    
    public void heatUpdate() {
        int id = worldObj.getBlockId(xCoord, yCoord-1, zCoord);
        if(id == Block.fire.blockID || id == Block.lavaStill.blockID) {
            importHeat(500);
        }
    }

    @Override
    public double getRange() {
        return 8;
    }

    @Override
    public int getHeatLossPerTick() {
        return 20;
    }

    @Override
    public int getMaxHeat() {
        return 5000;
    }
}
