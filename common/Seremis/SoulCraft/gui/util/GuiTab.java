package Seremis.SoulCraft.gui.util;

import Seremis.SoulCraft.gui.SCGui;

public abstract class GuiTab extends GuiRectangle {

    protected int id;
    
    protected boolean visible = true;
    
    protected String name;
    
    public GuiTab(int id, int x, int y, int w, int h, String name) {
        super(x, y, w, h);
        this.name = name;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public GuiTab setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
    
    public boolean visible() {
        return visible;
    }
    
    public void draw(SCGui gui, int srcX, int srcY) {
        if(visible)
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return "GuiTab[id: " + id + " x: " + x + ", y: " + y + ", width: " + w + ", heigth: " + h + ", name: '" + name + "']";
    }
    
    public abstract void drawBackground(SCGui gui, int x, int y);
    public abstract void drawForeground(SCGui gui, int x, int y);
    public void mouseClick(SCGui gui, int x, int y, int button) {}
    public void mouseMoveClick(SCGui gui, int x, int y, int button, long timeSinceClicked) {}
    public void mouseReleased(SCGui gui, int x, int y, int button) {}
}