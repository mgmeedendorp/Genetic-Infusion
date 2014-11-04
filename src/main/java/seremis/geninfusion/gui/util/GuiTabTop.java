package seremis.geninfusion.gui.util;

import seremis.geninfusion.gui.GIGui;
import seremis.geninfusion.gui.GuiStationControllerSend;
import seremis.geninfusion.gui.GuiStationControllerTransporter;

public class GuiTabTop extends GuiTab {

    public GuiTabTop(int id, int x, int y, int w, int h, int u, int v, String name) {
        super(id, x, y, w, h, u, v, name);
    }

    @Override
    public void drawBackground(GIGui gui, int x, int y) {

    }

    @Override
    public void drawForeground(GIGui gui, int x, int y) {
        gui.drawTexturedModalRect(this.x, this.y, u, v, w, h);
    }

    @Override
    public void mouseClick(GIGui gui, int x, int y, int button) {
        if(gui instanceof GuiStationControllerTransporter)
            ((GuiStationControllerTransporter) gui).tile.sendTileDataToServer(1, new byte[]{0});
        if(gui instanceof GuiStationControllerSend)
            ((GuiStationControllerSend) gui).tile.sendTileDataToServer(1, new byte[]{1});
    }
}
