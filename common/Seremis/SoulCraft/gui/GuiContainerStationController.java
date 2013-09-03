package Seremis.SoulCraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerStationController;
import Seremis.SoulCraft.tileentity.TileStationController;

public class GuiContainerStationController extends SCGui {

    private TileStationController tile;
    private int page;
    
    public GuiContainerStationController(EntityPlayer player, TileStationController tile) {
        super(new ContainerStationController(player, (IInventory)tile));
        super.xSize = 176;
        super.ySize = 222;
        this.tile = tile;
        page = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        
        if(page == 0) {
            
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        if(page == 0) {
            SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Magnet Station Controller", this.xSize / 2 -60, 5, 0x404040);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        tile.sendTileData(0, button.id);
    }
}
