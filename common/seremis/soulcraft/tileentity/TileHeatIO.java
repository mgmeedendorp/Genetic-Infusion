package seremis.soulcraft.tileentity;

import net.minecraft.block.Block;
import seremis.soulcraft.api.magnet.tile.TileMagnetHeater;

public class TileHeatIO extends TileMagnetHeater {

    @Override
    public void heatUpdate() {
        super.heatUpdate();
        int id = worldObj.getBlockId(xCoord, yCoord - 1, zCoord);
        if(id == Block.fire.blockID || id == Block.lavaStill.blockID) {
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
