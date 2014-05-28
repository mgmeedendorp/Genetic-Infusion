package seremis.soulcraft.gui.util;

import java.util.Arrays;

import seremis.soulcraft.gui.SCGui;

public abstract class GuiTab extends GuiRectangle {

    protected int id;

    protected boolean visible = true;

    protected String name;

    public GuiTab(int id, int x, int y, int w, int h, int u, int v, String name) {
        super(x, y, w, h, u, v);
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

    @Override
    public void draw(SCGui gui) {
        if(visible) {
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, u, v, w, h);
        }
    }

    @Override
    public void drawString(SCGui gui, int mouseX, int mouseY, String str) {
        if(visible && inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GuiTab[id: " + id + " x: " + x + ", y: " + y + ", width: " + w + ", heigth: " + h + " u:" + u + " v: " + v + ", name: '" + name + "']";
    }

    public abstract void drawBackground(SCGui gui, int x, int y);

    public abstract void drawForeground(SCGui gui, int x, int y);

    public void mouseClick(SCGui gui, int x, int y, int button) {}

    public void mouseMoveClick(SCGui gui, int x, int y, int button, long timeSinceClicked) {}

    public void mouseReleased(SCGui gui, int x, int y, int button) {}

    public void initGui(SCGui gui) {}

    public void keyTyped(SCGui gui, char keyTyped, int keyCode) {}
}