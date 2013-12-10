package Seremis.SoulCraft.gui.util;

import java.util.Arrays;

import Seremis.SoulCraft.gui.SCGui;

public class GuiRectangle {

    public int x;
    public int y;
    public int w;
    public int h;

    public GuiRectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean inRect(SCGui gui, int mouseX, int mouseY) {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();

        return isInBetween(x, x + w, mouseX) && isInBetween(y, y + h, mouseY);
    }

    public boolean isInBetween(int x, int y, int i) {
        return i >= Math.min(x, y) && i <= Math.max(x, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public void draw(SCGui gui, int srcX, int srcY) {
        gui.drawTexturedModalRect(x, y, srcX, srcY, w, h);
    }

    public void drawString(SCGui gui, int mouseX, int mouseY, String str) {
        if(inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }

    @Override
    public String toString() {
        return "GuiRectangle[x: " + x + ", y: " + y + ", width: " + w + ", height : " + h + "]";
    }
}
