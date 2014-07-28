package seremis.geninfusion.gui.util;

import seremis.geninfusion.gui.GIGui;

import java.util.Arrays;

public class GuiRectangle {

    public int x;
    public int y;
    public int w;
    public int h;
    public int u;
    public int v;

    public GuiRectangle(int x, int y, int w, int h, int u, int v) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.u = u;
        this.v = v;
    }

    public boolean inRect(GIGui gui, int mouseX, int mouseY) {
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

    public void draw(GIGui gui) {
        gui.drawTexturedModalRect(x, y, u, v, w, h);
    }

    public void drawString(GIGui gui, int mouseX, int mouseY, String str) {
        if(inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }

    @Override
    public String toString() {
        return "GuiRectangle[x: " + x + ", y: " + y + ", width: " + w + ", height : " + h + "]";
    }
}
