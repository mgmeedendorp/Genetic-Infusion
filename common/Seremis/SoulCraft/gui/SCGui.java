package Seremis.SoulCraft.gui;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class SCGui extends GuiContainer {

    public SCGui(Container container) {
        super(container);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

    }

    public int getLeft() {
        return guiLeft;
    }

    public int getTop() {
        return guiTop;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public void drawHoveringString(List<String> list, int x, int y) {
        drawHoveringText(list, x, y, fontRenderer);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {}

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

    }
}
