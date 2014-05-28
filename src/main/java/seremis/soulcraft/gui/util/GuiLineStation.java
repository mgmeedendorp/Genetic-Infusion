package seremis.soulcraft.gui.util;

import seremis.soulcraft.tileentity.TileStationController;

public class GuiLineStation extends GuiLine {

    public TileStationController tile;

    public GuiLineStation(int x, int y, int w, int h, int u, int v) {
        super(x, y, w, h, u, v);
    }

    public GuiLineStation() {
        super();
    }
}
