package Seremis.SoulCraft.api.magnet.tile;

import net.minecraftforge.common.ForgeDirection;

public abstract class TileMagnetHeater extends TileMagnetConnector implements IMagnetHeater {

    @Override
    public boolean connectToSide(ForgeDirection direction) {
        return false;
    }
    
    @Override
    public boolean connectToConnector(IMagnetConnector connector) {
        return !(connector instanceof IMagnetHeater);
    }

    @Override
    public int importHeat(int heat) {
        return warm(heat);
    }

    @Override
    public int exportHeat(int heat) {
        return cool(heat);
    }

}
