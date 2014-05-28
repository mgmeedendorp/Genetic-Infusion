package seremis.soulcraft.gui.util;

import seremis.soulcraft.gui.GuiStationControllerSend;
import seremis.soulcraft.gui.GuiStationControllerTransporter;
import seremis.soulcraft.gui.SCGui;

public class GuiTabTop extends GuiTab {
    
    public GuiTabTop(int id, int x, int y, int w, int h, int u, int v, String name) {
        super(id, x, y, w, h, u, v, name);
    }

    @Override
    public void drawBackground(SCGui gui, int x, int y) {
        
    }

    @Override
    public void drawForeground(SCGui gui, int x, int y) {
        gui.drawTexturedModalRect(this.x, this.y, u, v, w, h);
    }
    
    @Override
    public void mouseClick(SCGui gui, int x, int y, int button) {
        if(gui instanceof GuiStationControllerTransporter)
            ((GuiStationControllerTransporter)gui).tile.sendTileDataToServer(1, new byte[] {0});
        if(gui instanceof GuiStationControllerSend)
            ((GuiStationControllerSend)gui).tile.sendTileDataToServer(1, new byte[] {1});
    }
}
