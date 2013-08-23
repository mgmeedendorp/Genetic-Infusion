package Seremis.SoulCraft.gui;

import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class GuiContainerStation extends SCGui {

    public GuiContainerStation(Container container) {
        super(container);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        fontRenderer.drawString("Magnetic Station", this.xSize / 2 - 50, 5, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION);
        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);
    }

}
