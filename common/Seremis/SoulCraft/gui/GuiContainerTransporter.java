package Seremis.SoulCraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerTransporter;

public class GuiContainerTransporter extends SCGui {

    public GuiContainerTransporter(InventoryPlayer playerInv, IInventory tile) {
        super(new ContainerTransporter(playerInv.player, tile));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        fontRenderer.drawString("Plasmatic Transporter", this.xSize / 2 - 50, 5, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_TRANSPORTER);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

}
