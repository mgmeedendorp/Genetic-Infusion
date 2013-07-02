package Seremis.SoulCraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.inventory.container.ContainerTransporter;

public class GuiContainerTransporter extends SCGui {
    
    public GuiContainerTransporter(InventoryPlayer playerInv, IInventory tile) {
        super(new ContainerTransporter(playerInv.player, tile));
    }
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        fontRenderer.drawString("Plasmatic Transporter", 58, 5, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_TRANSPORTER);
        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);
    }

}
