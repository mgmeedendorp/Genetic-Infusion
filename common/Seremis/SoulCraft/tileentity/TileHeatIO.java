package Seremis.SoulCraft.tileentity;

import net.minecraft.block.Block;
import Seremis.SoulCraft.api.magnet.tile.TileMagnetHeater;

public class TileHeatIO extends TileMagnetHeater {

    @Override
    public void heatUpdate() {
        super.heatUpdate();
        int id = worldObj.getBlockId(xCoord, yCoord - 1, zCoord);
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
        return 10;
    }

    @Override
    public int getMaxHeat() {
        return 5000;
    }
    
    @Override
    public int getHeatTransmissionSpeed() {
        return 50;
    }
}
