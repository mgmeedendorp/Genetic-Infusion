package seremis.geninfusion.api.magnet.tile;

import seremis.geninfusion.api.magnet.MagnetLink;

public abstract class TileMagnetHeater extends TileMagnetConnector implements IMagnetHeater {

    @Override
    public boolean canConnect(MagnetLink link) {
        return !(link.getOther(this) instanceof IMagnetHeater);
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
