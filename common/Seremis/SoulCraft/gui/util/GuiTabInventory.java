package Seremis.SoulCraft.gui.util;

import Seremis.SoulCraft.gui.SCGui;

public class GuiTabInventory extends GuiTab {

    public GuiTabInventory(int id, int x, int y, int w, int h, String name) {
        super(id, x, y, w, h, name);
    }

    @Override
    public void drawBackground(SCGui gui, int x, int y) {
        if(visible)
            gui.drawTexturedModalRect(gui.getLeft() + 62, gui.getTop() + 72, 7, 139, 54, 54);          
    }

    @Override
    public void drawForeground(SCGui gui, int x, int y) {
        
    }

}
