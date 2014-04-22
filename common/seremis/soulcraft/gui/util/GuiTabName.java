package seremis.soulcraft.gui.util;

import seremis.soulcraft.gui.GuiStationControllerTransporter;
import seremis.soulcraft.gui.SCGui;
import net.minecraft.client.gui.GuiTextField;

public class GuiTabName extends GuiTab {

    private GuiStationControllerTransporter gui;
    private GuiTextField text;

    public GuiTabName(int id, int x, int y, int w, int h, int u, int v, String name, GuiStationControllerTransporter gui) {
        super(id, x, y, w, h, u, v, name);
        this.gui = gui;
    }

    @Override
    public void initGui(SCGui gui) {
        text = new GuiTextField(gui.getFontRenderer(), 50, 105, 78, 14);
        this.text.setVisible(true);
        this.text.setCanLoseFocus(true);
        this.text.setFocused(false);
        this.text.setMaxStringLength(30);
    }

    @Override
    public void drawBackground(SCGui gui, int x, int y) {

    }

    @Override
    public void drawForeground(SCGui gui, int x, int y) {
        if(visible) {
            gui.getFontRenderer().drawSplitString("Name: \n" + this.gui.tile.name, 45, 75, 90, 0x404040);
            text.drawTextBox();
        }
    }

    @Override
    public void mouseClick(SCGui gui, int x, int y, int button) {
        text.mouseClicked(x - gui.getLeft(), y - gui.getTop(), button);
    }

    @Override
    public void keyTyped(SCGui gui, char keyTyped, int keyCode) {
        if(visible && text.isFocused()) {
            text.textboxKeyTyped(keyTyped, keyCode);
            this.gui.tile.name = text.getText();
            this.gui.tile.sendTileDataToServer(2, this.gui.tile.name.getBytes());
        }
    }
}
