package seremis.geninfusion.tileentity;

import seremis.geninfusion.api.magnet.tile.TileMagnetHeater;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class TileHeatIO extends TileMagnetHeater {

    @Override
    public void heatUpdate() {
        super.heatUpdate();
        Block id = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
        if(id == Blocks.fire || id == Blocks.lava) {
            importHeat(5);
        }
    }

    @Override
    public double getRange() {
        return 8;
    }

    @Override
    public int getHeatLossPerTick() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return 5000;
    }
    
    @Override
    public int getHeatTransmissionSpeed() {
        return 20;
    }
}
