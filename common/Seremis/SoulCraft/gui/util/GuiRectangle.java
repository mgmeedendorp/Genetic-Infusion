package Seremis.SoulCraft.gui.util;

import java.util.Arrays;

import Seremis.SoulCraft.gui.SCGui;

public class GuiRectangle {

    protected int x;
    protected int y;
    protected int w;
    protected int h;
    
    public GuiRectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public boolean inRect(SCGui gui, int mouseX, int mouseY) {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();
        
        return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    
    public void draw(SCGui gui, int srcX, int srcY) {
        gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
    }
    
    public void drawString(SCGui gui, int mouseX, int mouseY, String str) {
        if (inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }
    
}
