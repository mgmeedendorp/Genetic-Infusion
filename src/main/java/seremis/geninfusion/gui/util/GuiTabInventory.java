package seremis.geninfusion.gui.util;

import seremis.geninfusion.gui.GIGui;

public class GuiTabInventory extends GuiTab {

    public GuiTabInventory(int id, int x, int y, int w, int h, int u, int v, String name) {
        super(id, x, y, w, h, u, v, name);
    }

    @Override
    public void drawBackground(GIGui gui, int x, int y) {
        if(visible) {
            gui.drawTexturedModalRect(gui.getLeft() + 61, gui.getTop() + 72, 7, 139, 54, 54);
        }
    }

    @Override
    public void drawForeground(GIGui gui, int x, int y) {

    }

}
