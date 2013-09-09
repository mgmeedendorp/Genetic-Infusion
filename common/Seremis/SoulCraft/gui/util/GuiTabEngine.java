package Seremis.SoulCraft.gui.util;

import Seremis.SoulCraft.gui.GuiStationControllerTransporter;
import Seremis.SoulCraft.gui.SCGui;

public class GuiTabEngine extends GuiTab {

    private GuiStationControllerTransporter gui;
    
    public GuiTabEngine(int id, int x, int y, int w, int h, String name, GuiStationControllerTransporter gui) {
        super(id, x, y, w, h, name);
        this.gui = gui;
    }

    @Override
    public void drawBackground(SCGui gui, int x, int y) {
        
    }

    @Override
    public void drawForeground(SCGui gui, int x, int y) {
        if(visible)
            gui.getFontRenderer().drawSplitString("Transporter Speed: " + (this.gui.tile.getTransporterSpeed()), 50, 80, 90, 0x404040);
    }

}
